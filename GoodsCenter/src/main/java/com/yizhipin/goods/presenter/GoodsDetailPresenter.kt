package com.yizhipin.goods.presenter

import com.yizhipin.base.data.response.Evaluate
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.goods.data.response.Report
import com.yizhipin.goods.presenter.view.GoodsDetailView
import com.yizhipin.goods.service.impl.GoodsServiceImpl
import javax.inject.Inject

/*
    商品详情 Presenter
 */
class GoodsDetailPresenter @Inject constructor() : BasePresenter<GoodsDetailView>() {

    @Inject
    lateinit var mGoodsServiceImpl: GoodsServiceImpl


    /**
     * 最新评价
     */
    fun getEvaluateNew(map: MutableMap<String, String>) {

        mView.showLoading()
        mGoodsServiceImpl.getEvaluateNew(map).execute(object : BaseSubscriber<Evaluate>(mView) {
            override fun onNext(t: Evaluate) {
                mView.onGetEvaluateNewSuccess(t)
            }
        }, mLifecycleProvider)

    }

    /**
     * 最新体验报告
     */
    fun getReportNew(map: MutableMap<String, String>) {
        mView.showLoading()
        mGoodsServiceImpl.getReportNew(map).execute(object : BaseSubscriber<Report>(mView) {
            override fun onNext(t: Report) {
                mView.onGetReportNewSuccess(t)
            }
        }, mLifecycleProvider)

    }

    fun getCartCountData(map: MutableMap<String, String>) {
//        mView.showLoading()
        mGoodsServiceImpl.getCartCountData(map).execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.onGetCartCountSuccess(t)
            }
        }, mLifecycleProvider)

    }
}
