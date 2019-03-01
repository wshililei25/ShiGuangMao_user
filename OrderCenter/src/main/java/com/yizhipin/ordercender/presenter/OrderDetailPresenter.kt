package com.yizhipin.ordercender.presenter

import com.yizhipin.base.data.response.OrderDetails
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.ordercender.presenter.view.OrderDetailView
import com.yizhipin.ordercender.serivice.impl.OrderServiceImpl
import javax.inject.Inject

class OrderDetailPresenter @Inject constructor() : BasePresenter<OrderDetailView>() {

    @Inject
    lateinit var mServiceImpl: OrderServiceImpl

    fun getOrderDetails(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getOrderDetails(map).execute(object : BaseSubscriber<OrderDetails>(mView) {
            override fun onNext(t: OrderDetails) {
                mView.onOrderDetailsSuccess(t)
            }
        }, mLifecycleProvider)
    }

}
