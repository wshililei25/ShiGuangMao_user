package com.yizhipin.shop.presenter

import com.yizhipin.base.data.response.CashPledge
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.shop.presenter.view.CashPledgeView
import com.yizhipin.shop.service.impl.PayServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class CashPledgePresenter @Inject constructor() : BasePresenter<CashPledgeView>() {

    @Inject
    lateinit var mServiceImpl: PayServiceImpl

    fun getCashPledge(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getCashPledge(map).execute(object : BaseSubscriber<CashPledge>(mView) {
            override fun onNext(t: CashPledge) {
                mView.onGetCashPledgeSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun recharge(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.recharge(map).execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.onRechargeSuccess(t)
            }
        }, mLifecycleProvider)
    }
}

