package com.yizhipin.paycenter.ui.activity

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.CashPledge
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.paycenter.R
import com.yizhipin.provider.router.RouterPath
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

    private lateinit var mCashPledge: CashPledge

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
            R.id.mRebackBtn -> ARouter.getInstance().build(RouterPath.PayCenter.PATH_PAY_WITHDRAW)
                    .withBoolean(BaseConstant.KEY_IS_CASH, true).navigation()
        }
    }

    override fun onRechargeSuccess(result: String) {
    }

}