package com.yizhipin.goods.presenter

import com.yizhipin.base.data.response.Star
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.goods.presenter.view.StarView
import com.yizhipin.goods.service.impl.GoodsServiceImpl
import javax.inject.Inject

class StarPresenter @Inject constructor() : BasePresenter<StarView>() {

    @Inject
    lateinit var mGoodsServiceImpl: GoodsServiceImpl

    fun getStar() {
        mView.showLoading()
        mGoodsServiceImpl.getStar().execute(object : BaseSubscriber<Star>(mView) {
            override fun onNext(t: Star) {
                mView.onGetStarSuccess(t)
            }
        }, mLifecycleProvider)

    }
}
