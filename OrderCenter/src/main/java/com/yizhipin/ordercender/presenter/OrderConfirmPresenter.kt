package com.yizhipin.ordercender.presenter

import com.yizhipin.base.data.response.DressDetails
import com.yizhipin.base.data.response.ShipAddress
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.ordercender.presenter.view.OrderConfirmView
import com.yizhipin.ordercender.serivice.impl.OrderServiceImpl
import javax.inject.Inject

/*
    订单确认页 Presenter
 */
class OrderConfirmPresenter @Inject constructor() : BasePresenter<OrderConfirmView>() {

    @Inject
    lateinit var mOrderServiceImpl: OrderServiceImpl

    fun getGoodsDetail(map: MutableMap<String, String>) {
        mView.showLoading()
        mOrderServiceImpl.getGoodsDetail(map).execute(object : BaseSubscriber<DressDetails>(mView) {
            override fun onNext(t: DressDetails) {
                mView.onGetGoodsDetailSuccess(t)
            }
        }, mLifecycleProvider)

    }

    /**
     * 获取默认地址
     */
    fun getDefaultAddress(map: MutableMap<String, String>) {

        mOrderServiceImpl.getDefaultAddress(map).execute(object : BaseSubscriber<ShipAddress>(mView) {
            override fun onNext(t: ShipAddress) {
                mView.onGetDefaultAddressSuccess(t)
            }
        }, mLifecycleProvider)

    }



}
