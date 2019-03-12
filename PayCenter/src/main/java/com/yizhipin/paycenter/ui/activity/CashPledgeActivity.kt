package com.yizhipin.paycenter.ui.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.alipay.sdk.app.PayTask
import com.google.gson.Gson
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.AliPayResult
import com.yizhipin.base.data.response.CashPledge
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.payresult.PayResult
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.ui.activity.PaySuccessActivity
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.paycenter.R
import com.yizhipin.shop.injection.component.DaggerPayComponent
import com.yizhipin.shop.injection.module.PayModule
import com.yizhipin.shop.presenter.CashPledgePresenter
import com.yizhipin.shop.presenter.view.CashPledgeView
import kotlinx.android.synthetic.main.activity_cash_pledge.*
import org.jetbrains.anko.startActivity

/**
 * Created by ${XiLei} on 2018/9/24.
 *
 * 押金
 */
class CashPledgeActivity : BaseMvpActivity<CashPledgePresenter>(), CashPledgeView, View.OnClickListener {

    private var mType = "Alipay" //支付方式
    private val SDK_PAY_FLAG = 1
    private lateinit var mCashPledge:CashPledge

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_pledge)

        initView()
    }

    override fun injectComponent() {
        DaggerPayComponent.builder().activityComponent(mActivityComponent).payModule(PayModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initView() {
        mConfirmBtn.onClick(this)
        mRebackBtn.onClick(this)
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        var map = mutableMapOf<String, String>()
        map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mBasePresenter.getCashPledge(map)
    }

    override fun onGetCashPledgeSuccess(result: CashPledge) {
        mCashPledge = result
        mAmountTv.text = "¥ ${result.total}"
        mRebackAmountTv.text = "(¥ ${result.available}可退)"
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mConfirmBtn -> startActivity<RechargeActivity>(BaseConstant.KEY_IS_CASHPLEDGE to true)
            R.id.mRebackBtn -> {

                var map = mutableMapOf<String, String>()
                map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
                map.put("amount", mCashPledge.available)
                map.put("payType", mType)
                mBasePresenter.recharge(map)
            }
        }

    }

    override fun onRechargeSuccess(result: String) {
        when (mType) {
            "Alipay" -> {
                val payRunnable = Runnable {
                    val alipay = PayTask(this@CashPledgeActivity)
                    val result = alipay.payV2(result, true)
                    Log.i("XiLei", "aliPay=" + result.toString())
                    val msg = Message()
                    msg.what = SDK_PAY_FLAG
                    msg.obj = result
                    mHandler.sendMessage(msg)
                }
                // 必须异步调用
                val payThread = Thread(payRunnable)
                payThread.start()
            }
            "Weixin" -> {

            }
        }
    }

    //支付宝支付 begin
    @SuppressLint("HandlerLeak")
    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                SDK_PAY_FLAG -> {
                    val payResult = PayResult(msg.obj as Map<String, String>)
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    val resultInfo = payResult.result// 同步返回需要验证的信息
                    val resultStatus = payResult.resultStatus
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Log.d("XiLei", "payResult=" + payResult);
//                        showAlert(this@RechargeActivity, getString(R.string.pay_success) + payResult)
                        var gson = Gson().fromJson<AliPayResult>(resultInfo, AliPayResult::class.java)
                        startActivity<PaySuccessActivity>(BaseConstant.KEY_PAY_CONTENT to "成功充值" + gson.alipay_trade_app_pay_response.total_amount + "元")
                        finish()
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知
                        if (TextUtils.equals(resultStatus, "6001")) {
                            showAlert(this@CashPledgeActivity, getString(R.string.pay_cancel))
                            return
                        }
                        showAlert(this@CashPledgeActivity, getString(R.string.pay_failed))
                    }
                }
            }
        }
    }

    private fun showAlert(ctx: Context, info: String) {
        showAlert(ctx, info, null)
    }

    private fun showAlert(ctx: Context, info: String, onDismiss: DialogInterface.OnDismissListener?) {
        AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton(R.string.confirm, null)
                .setOnDismissListener(onDismiss)
                .show()
    }
    //支付宝支付 end

}