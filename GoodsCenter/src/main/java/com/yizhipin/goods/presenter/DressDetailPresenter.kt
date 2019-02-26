package com.yizhipin.goods.presenter

import com.yizhipin.base.data.response.DressDetails
import com.yizhipin.base.data.response.DressNorm
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.goods.presenter.view.DressDetailView
import com.yizhipin.goods.service.impl.GoodsServiceImpl
import javax.inject.Inject

class DressDetailPresenter @Inject constructor() : BasePresenter<DressDetailView>() {

    @Inject
    lateinit var mGoodsServiceImpl: GoodsServiceImpl

    fun getGoodsDetail(map: MutableMap<String, String>) {
        mView.showLoading()
        mGoodsServiceImpl.getGoodsDetail(map).execute(object : BaseSubscriber<DressDetails>(mView) {
            override fun onNext(t: DressDetails) {
                mView.onGetGoodsDetailSuccess(t)
            }
        }, mLifecycleProvider)

    }

    fun getGoodNorm(map: MutableMap<String, String>) {
        mView.showLoading()
        mGoodsServiceImpl.getGoodNorm(map).execute(object : BaseSubscriber<MutableList<DressNorm>>(mView) {
            override fun onNext(t: MutableList<DressNorm>) {
//                mView.onGetGoodsNormSuccess(t)
            }
        }, mLifecycleProvider)

    }

    fun followDress(map: MutableMap<String, String>) {
        mView.showLoading()
        mGoodsServiceImpl.followDress(map).execute(object : BaseSubscriber<DressDetails>(mView) {
            override fun onNext(t: DressDetails) {
                mView.onFollowDressSuccess(t)
            }
        }, mLifecycleProvider)

    }

}
