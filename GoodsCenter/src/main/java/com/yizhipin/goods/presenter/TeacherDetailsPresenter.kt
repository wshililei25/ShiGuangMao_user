package com.yizhipin.goods.presenter

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Cameraman
import com.yizhipin.base.data.response.CameranmanWorks
import com.yizhipin.base.data.response.Evaluate
import com.yizhipin.base.data.response.OrderDetails
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.goods.presenter.view.TeacherDetailsView
import com.yizhipin.goods.service.impl.GoodsServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class TeacherDetailsPresenter @Inject constructor() : BasePresenter<TeacherDetailsView>() {

    @Inject
    lateinit var mServiceImpl: GoodsServiceImpl

    fun getCameramanDetails(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getCameramanDetails(map).execute(object : BaseSubscriber<Cameraman>(mView) {
            override fun onNext(t: Cameraman) {
                mView.onGetCameramanDetailsSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun getEvaluateData(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getEvaluateData(map).execute(object : BaseSubscriber<MutableList<Evaluate>>(mView) {
            override fun onNext(t: MutableList<Evaluate>) {
                mView.onGetEvaluateSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun getTeacherWorks(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getTeacherWorks(map).execute(object : BaseSubscriber<BasePagingResp<MutableList<CameranmanWorks>>>(mView) {
            override fun onNext(t: BasePagingResp<MutableList<CameranmanWorks>>) {
                mView.onGetTeacherWorksSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun getCameramanFollow(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getCameramanFollow(map).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onFollowSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun orderTeacher(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.orderTeacher(map).execute(object : BaseSubscriber<OrderDetails>(mView) {
            override fun onNext(t: OrderDetails) {
                mView.onOrderSuccess(t)
            }
        }, mLifecycleProvider)
    }
}

