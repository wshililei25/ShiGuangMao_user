package com.yizhipin.goods.presenter

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.DressCategory
import com.yizhipin.base.data.response.TimeSuperMarket
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.goods.presenter.view.TimeSuperMarketView
import com.yizhipin.goods.service.impl.GoodsServiceImpl
import javax.inject.Inject

class TimeSuperMarketPresenter @Inject constructor() : BasePresenter<TimeSuperMarketView>() {

    @Inject
    lateinit var mServiceImpl: GoodsServiceImpl

    fun getTimeSuperMarketCategory() {
        mView.showLoading()
        mServiceImpl.getTimeSuperMarketCategory().execute(object : BaseSubscriber<MutableList<DressCategory>>(mView) {
            override fun onNext(t: MutableList<DressCategory>) {
                mView.onGetTimeSuperMarketCategorySuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun getTimeSuperMarketList(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getTimeSuperMarketList(map).execute(object : BaseSubscriber<BasePagingResp<MutableList<TimeSuperMarket>>>(mView) {
            override fun onNext(t: BasePagingResp<MutableList<TimeSuperMarket>>) {
                mView.onGetTimeSuperMarketListSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun getTimeSuperMarketDetail(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getTimeSuperMarketDetail(map).execute(object : BaseSubscriber<TimeSuperMarket>(mView) {
            override fun onNext(t: TimeSuperMarket) {
                mView.onGetTimeSuperMarketDetailSuccess(t)
            }
        }, mLifecycleProvider)

    }

    fun getFollowMarket(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getFollowMarket(map).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onFollowSuccess(t)
            }
        }, mLifecycleProvider)
    }
}
