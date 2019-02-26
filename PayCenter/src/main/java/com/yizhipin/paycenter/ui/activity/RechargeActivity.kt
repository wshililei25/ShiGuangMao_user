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
import com.alibaba.android.arouter.facade.annotation.Route
import com.alipay.sdk.app.PayTask
import com.google.gson.Gson
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.AliPayResult
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.payresult.PayResult
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.ui.activity.PaySuccessActivity
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.base.widgets.PayRadioGroup
import com.yizhipin.base.widgets.PayRadioPurified
import com.yizhipin.paycenter.R
import com.yizhipin.provider.router.RouterPath
import com.yizhipin.shop.injection.component.DaggerPayComponent
import com.yizhipin.shop.injection.module.PayModule
import com.yizhipin.shop.presenter.PayPresenter
import com.yizhipin.shop.presenter.view.PayView
import kotlinx.android.synthetic.main.activity_recharge.*
import org.jetbrains.anko.startActivity

/**
 * Created by ${XiLei} on 2018/9/24.
 *
 * 充值
 */
@Route(path = RouterPath.PayCenter.PATH_PAY_RECHARGE)
class RechargeActivity : BaseMvpActivity<PayPresenter>(), PayView, View.OnClickListener {

    private var mType = "Alipay" //支付方式
    private val SDK_PAY_FLAG = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recharge)

        initView()
    }

    override fun injectComponent() {
        DaggerPayComponent.builder().activityComponent(mActivityComponent).payModule(PayModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initView() {
        mMinusIv.onClick(this)
        mPlusIv.onClick(this)
        mConfirmBtn.onClick(this)

        mPayRadioGroup.setOnCheckedChangeListener(object : PayRadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: PayRadioGroup, checkedId: Int) {
                for (i in 0 until group.getChildCount()) {
                    (group.getChildAt(i) as PayRadioPurified).setChangeImg(checkedId)
                }
                if (mAliRadio.isChecked) {
                    mType = "Alipay"
                }
                if (mWechatRadio.isChecked) {
                    mType = "Weixin"
                }
            }
        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mMinusIv -> {
                if (mAmountEt.text.toString().toInt() > 100) {
                    var amount = mAmountEt.text.toString().toInt() - 100
                    mAmountEt.setText(amount.toString())
                }
            }
            R.id.mPlusIv -> {
                var amount = mAmountEt.text.toString().toInt() + 100
                mAmountEt.setText(amount.toString())
            }
            R.id.mConfirmBtn -> {

                var map = mutableMapOf<String, String>()
                map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN))
//                map.put("amount", mAmountEt.text.toString()) //暂时注释
                map.put("amount", "0.01")
                map.put("payType", mType)
                mBasePresenter.recharge(map)
            }
        }

    }

    override fun onRechargeSuccess(result: String) {
        when (mType) {
            "Alipay" -> {
                val payRunnable = Runnable {
                    val alipay = PayTask(this@RechargeActivity)
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
                    val resultInfo = payResult.getResult()// 同步返回需要验证的信息
                    val resultStatus = payResult.getResultStatus()
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Log.d("XiLei", "payResult=" + payResult);
//                        showAlert(this@RechargeActivity, getString(R.string.pay_success) + payResult)
                        var gson = Gson().fromJson<AliPayResult>(payResult.result, AliPayResult::class.java)
                        startActivity<PaySuccessActivity>(BaseConstant.KEY_PAY_CONTENT to "成功充值" + gson.alipay_trade_app_pay_response.total_amount + "元")
                        finish()
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(this@RechargeActivity, getString(R.string.pay_failed) + payResult)
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