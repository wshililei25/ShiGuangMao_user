package com.yizhipin.shop.presenter

import com.yizhipin.base.data.response.Meal
import com.yizhipin.base.data.response.ScenicSpot
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.shop.presenter.view.ScenicDetailsView
import com.yizhipin.shop.service.impl.ShopServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class ScenicDetailsPresenter @Inject constructor() : BasePresenter<ScenicDetailsView>() {

    @Inject
    lateinit var mServiceImpl: ShopServiceImpl

    fun getScenicDetails(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getScenicDetails(map).execute(object : BaseSubscriber<ScenicSpot>(mView) {
            override fun onNext(t: ScenicSpot) {
                mView.onGetScenicDetailsSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun getMealData(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getMealData(map).execute(object : BaseSubscriber<MutableList<Meal>>(mView) {
            override fun onNext(t: MutableList<Meal>) {
                mView.onGetMealSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun getFollowScenic(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getFollowScenic(map).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onFollowSuccess(t)
            }
        }, mLifecycleProvider)
    }
}

