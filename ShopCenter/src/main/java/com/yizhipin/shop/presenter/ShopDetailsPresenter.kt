package com.yizhipin.shop.presenter

import com.yizhipin.base.data.response.*
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.shop.presenter.view.ShopDetailsView
import com.yizhipin.shop.service.impl.ShopServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class ShopDetailsPresenter @Inject constructor() : BasePresenter<ShopDetailsView>() {

    @Inject
    lateinit var mServiceImpl: ShopServiceImpl

    fun getShopDetails(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getShopDetails(map).execute(object : BaseSubscriber<ShopDetails>(mView) {
            override fun onNext(t: ShopDetails) {
                mView.onGetShopDetailsSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun getBanner(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getBanner(map).execute(object : BaseSubscriber<MutableList<Banner>>(mView) {
            override fun onNext(t: MutableList<Banner>) {
                mView.onGetBannerSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun getHotMealData(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getHotMealData(map).execute(object : BaseSubscriber<MutableList<Meal>>(mView) {
            override fun onNext(t: MutableList<Meal>) {
                mView.onGetHotMealSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun getTimeTeacherData(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getTimeTeacherData(map).execute(object : BaseSubscriber<MutableList<Teacher>>(mView) {
            override fun onNext(t: MutableList<Teacher>) {
                mView.onGetTimeTeacherSuccess(t)
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

    fun getFollow(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getFollow(map).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onFollowSuccess(t)
            }
        }, mLifecycleProvider)
    }
}

