package com.yizhipin.ordercender.ui.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.alipay.sdk.app.PayTask
import com.google.gson.Gson
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.common.WechatAppID
import com.yizhipin.base.data.response.AliPayResult
import com.yizhipin.base.data.response.BuyResult
import com.yizhipin.base.data.response.Coupon
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.payresult.PayResult
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.ui.activity.PaySuccessActivity
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.base.widgets.PayPasswordDialog
import com.yizhipin.base.widgets.PayRadioGroup
import com.yizhipin.base.widgets.PayRadioPurified
import com.yizhipin.ordercender.R
import com.yizhipin.ordercender.common.OrderConstant
import com.yizhipin.ordercender.injection.component.DaggerOrderComponent
import com.yizhipin.ordercender.injection.module.OrderModule
import com.yizhipin.ordercender.presenter.PayConfirmPresenter
import com.yizhipin.ordercender.presenter.PayConfirmView
import com.yizhipin.provider.common.ProvideReqCode
import com.yizhipin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_pay_confirm.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject

/**
 * Created by ${XiLei} on 2018/9/24.
 * 支付确认
 */
@Route(path = RouterPath.OrderCenter.PATH_ORDER_PAY)
class PayConfirmActivity : BaseMvpActivity<PayConfirmPresenter>(), PayConfirmView, View.OnClickListener {

    @Autowired(name = BaseConstant.KEY_PAY_FROM)
    @JvmField
    var mPayFrom: String = ""  //支付来源

    @Autowired(name = BaseConstant.KEY_DRESS_ID)
    @JvmField
    var mDressId: String = ""  //服装id

    @Autowired(name = BaseConstant.KEY_PAY_NUM)
    @JvmField
    var mPayNum: String = ""  //购买数量

    @Autowired(name = BaseConstant.KEY_PAY_AMOUNT)
    @JvmField
    var mPayAmount: String = ""  //实际支付金额

    @Autowired(name = BaseConstant.KEY_ADDRESS_ID)
    @JvmField
    var mAddressId: String = "" //收货地址id

    @Autowired(name = BaseConstant.KEY_MEAL_ORDER_ID)
    @JvmField
    var mOrderId: String = "" //订单id

    private lateinit var mIWXAPI: IWXAPI
    private var mGoodsId = "" //商品id
    private var mProductCounts = "" //商品数量
    private var mConponId = "" //优惠券id
    private var mPayType = "Alipay" //支付方式
    private lateinit var mPayPasswordDialog: PayPasswordDialog
    private val SDK_PAY_FLAG = 1
    private var mAmount = 0.00

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_confirm)

        initView()
        initWechat()
    }

    override fun injectComponent() {
        DaggerOrderComponent.builder().activityComponent(mActivityComponent).orderModule(OrderModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initView() {
        mPostageTv.text = getString(R.string.rmb).plus(mPayAmount)
        mRealityPriceTv.text = mPayAmount
        if (mPayFrom.isNotEmpty()) {
            mTypeTv.text = mPayFrom
        }

        mPayRadioGroup.setOnCheckedChangeListener(object : PayRadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: PayRadioGroup, checkedId: Int) {
                for (i in 0 until group.getChildCount()) {
                    (group.getChildAt(i) as PayRadioPurified).setChangeImg(checkedId)
                }
                if (mBalanceRadio.isChecked) {
                    mPayType = "yue"
                }
                if (mAliRadio.isChecked) {
                    mPayType = "Alipay"
                }
                if (mWechatRadio.isChecked) {
                    mPayType = "Weixin"
                }
            }
        })

        mPayBtn.onClick(this)
        mCouponView.onClick(this)
    }

    /**
     * WeChat Pay注册
     */
    private fun initWechat() {
        mIWXAPI = WXAPIFactory.createWXAPI(this, null)
        when (AppPrefsUtils.getString(BaseConstant.KEY_SHOP_NAME)) {
            "临汾店" -> mIWXAPI.registerApp(WechatAppID.LINFEN)
            "三亚店" -> mIWXAPI.registerApp(WechatAppID.SANYA)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mPayBtn -> {

                when (mPayFrom) {
                    getString(R.string.meal_destine) -> { //套餐预定
                        var map = mutableMapOf<String, String>()
                        map.put("orderId", mOrderId)
                        map.put("payType", mPayType)
                        if (mPayAmount == "1000") { //支付订金
                            mBasePresenter.mealFrontMoney(map)
                        } else { //支付尾款
                            mBasePresenter.mealBalancePayment(map)
                        }

                    }
                    getString(R.string.dress_buy) -> { //服装购买
                        var map = mutableMapOf<String, String>()
                        map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
                        map.put("clothId", mDressId)
                        map.put("orderCount", mPayNum)
                        map.put("payType", mPayType)
                        map.put("addressId", mAddressId)
                        mBasePresenter.dressBuy(map)
                    }
                    getString(R.string.dress_hire) -> { //服装租借
                        var map = mutableMapOf<String, String>()
                        map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
                        map.put("clothId", mDressId)
                        map.put("orderCount", mPayNum)
                        map.put("payType", mPayType)
                        map.put("addressId", mAddressId)
                        mBasePresenter.dressHire(map)
                    }
                }
            }
            R.id.mCouponView -> ARouter.getInstance().build(RouterPath.OrderCenter.PATH_ORDER_COUPON)
                    .withBoolean(OrderConstant.KEY_IS_PAY, true)
                    .navigation(this, ProvideReqCode.CODE_REQ_COUPON)
        }
    }

    /**
     * 套餐预定
     */
    override fun onMealFrontMoneySuccess(result: String) {
        pay(result)
    }

    /**
     * 服装购买、租借
     */
    override fun onDressBuySuccess(result: BuyResult) {
        pay(result.payInfo)
    }

    private fun pay(result: String) {
        when (mPayType) {

            "yue" -> {
                startActivity<PaySuccessActivity>(BaseConstant.KEY_PAY_CONTENT to getString(R.string.pay_success))
                finish()
            }
            "Alipay" -> {
                val payRunnable = Runnable {
                    val alipay = PayTask(this@PayConfirmActivity)
                    val result = alipay.payV2(result.toString(), true)
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
                val json = JSONObject(result.toString())
                val req = PayReq()
                req.appId = json.getString("appid")
                req.partnerId = json.getString("partnerid")
                req.prepayId = json.getString("prepayid")
                req.packageValue = json.getString("package")
                req.nonceStr = json.getString("noncestr")
                req.timeStamp = json.getString("timestamp")
                req.sign = json.getString("sign")
                toast("正常调起微信支付")
                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                mIWXAPI.sendReq(req)
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
                        startActivity<PaySuccessActivity>(BaseConstant.KEY_PAY_CONTENT to getString(R.string.pay_success))
                        finish()
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知
                        if (TextUtils.equals(resultStatus, "6001")) {
                            showAlert(this@PayConfirmActivity, getString(R.string.pay_cancel))
                            return
                        }
                        showAlert(this@PayConfirmActivity, getString(R.string.pay_failed))
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

    /**
     * 下单成功
     */
    override fun onSubmitOrderSuccess(result: String) {
        setResult(ProvideReqCode.CODE_RESULT_PAY)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            ProvideReqCode.CODE_RESULT_COUPON -> {
                var coupon = data!!.getParcelableExtra<Coupon>(OrderConstant.KEY_COUPON_ITEM)

                if (mAmount >= coupon.minAmount.toDouble()) {
//                    mAmountTv.text = "已自动抵扣${coupon.amount}元"
                    mConponId = coupon.id.toString()
                } else {
//                    mAmountTv.text = "不可用"
                    mConponId = ""
                }
            }
        }
    }
}