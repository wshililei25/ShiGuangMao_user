package com.yizhipin.goods.presenter

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.BasicServices
import com.yizhipin.base.data.response.Evaluate
import com.yizhipin.base.data.response.OrderDetails
import com.yizhipin.base.data.response.SetMealDetails
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.goods.presenter.view.SetMealDetailsView
import com.yizhipin.goods.service.impl.GoodsServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class SetMealDetailsPresenter @Inject constructor() : BasePresenter<SetMealDetailsView>() {

    @Inject
    lateinit var mServiceImpl: GoodsServiceImpl

    fun getMealDetails(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getMealDetails(map).execute(object : BaseSubscriber<SetMealDetails>(mView) {
            override fun onNext(t: SetMealDetails) {
                mView.onGetMealDetailsSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun order(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.order(map).execute(object : BaseSubscriber<OrderDetails>(mView) {
            override fun onNext(t: OrderDetails) {
                mView.onOrderSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun getOrderDetails(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getOrderDetails(map).execute(object : BaseSubscriber<OrderDetails>(mView) {
            override fun onNext(t: OrderDetails) {
                mView.onOrderDetailsSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun getEvaluateList(map: MutableMap<String, String>) {
        mServiceImpl.getEvaluateList(map).execute(object : BaseSubscriber<BasePagingResp<MutableList<Evaluate>>>(mView) {
            override fun onNext(t: BasePagingResp<MutableList<Evaluate>>) {
                mView.onGetEvaluateListSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun getBasicServicesData(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getBasicServicesData(map).execute(object : BaseSubscriber<MutableList<BasicServices>>(mView) {
            override fun onNext(t: MutableList<BasicServices>) {
                mView.onGetBasicServicesSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun getFollow(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getFollow(map).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onFollowSuccess(t)
            }
        }, mLifecycleProvider)
    }
}

