package com.yizhipin.shop.presenter

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.ShopDetails
import com.yizhipin.base.data.response.Store
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.shop.presenter.view.PayView
import com.yizhipin.shop.service.impl.PayServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class PayPresenter @Inject constructor() : BasePresenter<PayView>() {

    @Inject
    lateinit var mServiceImpl: PayServiceImpl

    fun recharge(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.recharge(map).execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.onRechargeSuccess(t)
            }
        }, mLifecycleProvider)
    }
}

