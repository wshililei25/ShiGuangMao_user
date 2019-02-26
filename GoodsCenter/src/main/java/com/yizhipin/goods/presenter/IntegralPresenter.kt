package com.yizhipin.goods.presenter

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Integral
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.goods.presenter.view.IntegralView
import com.yizhipin.goods.service.impl.GoodsServiceImpl
import javax.inject.Inject

class IntegralPresenter @Inject constructor() : BasePresenter<IntegralView>() {

    @Inject
    lateinit var mServiceImpl: GoodsServiceImpl

    fun getIntegralList(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getIntegralList(map).execute(object : BaseSubscriber<BasePagingResp<MutableList<Integral>>>(mView) {
            override fun onNext(t: BasePagingResp<MutableList<Integral>>) {
                mView.onGetIntegralListSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun getIntegralDetail(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getIntegralDetail(map).execute(object : BaseSubscriber<Integral>(mView) {
            override fun onNext(t: Integral) {
                mView.onGetIntegralDetailSuccess(t)
            }
        }, mLifecycleProvider)

    }

    /* fun getFollowMarket(map: MutableMap<String, String>) {
       mView.showLoading()
       mServiceImpl.getFollowMarket(map).execute(object : BaseSubscriber<Boolean>(mView) {
           override fun onNext(t: Boolean) {
               mView.onFollowSuccess(t)
           }
       }, mLifecycleProvider)
   }*/
}
