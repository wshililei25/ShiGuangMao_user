package com.yizhipin.paycenter.ui.activity

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.ext.enable
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.paycenter.R
import com.yizhipin.provider.router.RouterPath
import com.yizhipin.shop.injection.component.DaggerPayComponent
import com.yizhipin.shop.injection.module.PayModule
import com.yizhipin.shop.presenter.WithdrawPresenter
import com.yizhipin.shop.presenter.view.WithdrawView
import kotlinx.android.synthetic.main.activity_withdraw.*

/**
 * Created by ${XiLei} on 2018/9/24.
 *
 * 提现、退押金
 */
@Route(path = RouterPath.PayCenter.PATH_PAY_WITHDRAW)
class WithdrawActivity : BaseMvpActivity<WithdrawPresenter>(), WithdrawView, View.OnClickListener {

    @Autowired(name = BaseConstant.KEY_IS_CASH)
    @JvmField
    var mIsCash: Boolean = false //是提现还是退押金

    @Autowired(name = BaseConstant.KEY_DEPOSIT)
    @JvmField
    var mDeposit: String = "" //可退还押金

    private lateinit var mUserInfo: UserInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdraw)

        initView()
        initData()
    }

    override fun injectComponent() {
        DaggerPayComponent.builder().activityComponent(mActivityComponent).payModule(PayModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initView() {

        mAllTv.onClick(this)
        mConfirmBtn.onClick(this)
        mCustomBtn.onClick(this)
        mConfirmBtn.enable(mAmountEt, { isBtnEnable() })
        mConfirmBtn.enable(mAliPayEt, { isBtnEnable() })
        if (mIsCash) {
            mHeaderBar.getTiTleTv().text = getString(R.string.reback_recharge)
            mNameTv.text = getString(R.string.reback_amount)
            mAllTv.text = getString(R.string.reback_all)
            mChannelTv.text = getString(R.string.reback_channel)
            mAmountEt.setHint("可退 ¥$mDeposit")
        }
    }

    private fun initData() {
        var map = mutableMapOf<String, String>()
        map.put("id", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mBasePresenter.getUserInfo(map)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mCustomBtn -> custom()
            R.id.mAllTv -> {
                if (mIsCash) {
                    mAmountEt.setText(mDeposit)
                } else {
                    mUserInfo?.let {
                        mAmountEt.setText(mUserInfo.amount.toString())
                    }
                }
                mAmountEt.setSelection(mAmountEt.text.length)
            }
            R.id.mConfirmBtn -> {
                var map = mutableMapOf<String, String>()
                map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
                map.put("amount", mAmountEt.text.toString())
                map.put("payType", "Alipay")
                mBasePresenter.applyWithdraw(map)
            }
        }
    }

    private fun isBtnEnable(): Boolean {
        return mAmountEt.text.isNullOrEmpty().not() &&
                mAliPayEt.text.isNullOrEmpty().not()
    }

    override fun getUserResult(result: UserInfo) {
        mUserInfo = result
        if (!mIsCash) {
            mAmountEt.hint = "可提 ¥${result.amount}"
        }
    }

    override fun onApplyWithdrawSuccess(result: String) {
        finish()
    }

}