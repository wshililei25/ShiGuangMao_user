package com.yizhipin.goods.presenter

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Evaluate
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.goods.presenter.view.ReportView
import com.yizhipin.goods.service.impl.GoodsServiceImpl
import javax.inject.Inject

/*
    评价 Presenter
 */
class EvaluatePresenter @Inject constructor() : BasePresenter<ReportView>() {

    @Inject
    lateinit var mGoodsServiceImpl: GoodsServiceImpl

    fun getEvaluateList(map: MutableMap<String, String>) {

        mGoodsServiceImpl.getEvaluateList(map).execute(object : BaseSubscriber<BasePagingResp<MutableList<Evaluate>>>(mView) {
            override fun onNext(t: BasePagingResp<MutableList<Evaluate>>) {
                mView.onGetEvaluateListSuccess(t)
            }
        }, mLifecycleProvider)
    }
    fun getEvaluateTeacherList(map: MutableMap<String, String>) {

        mGoodsServiceImpl.getEvaluateTeacherList(map).execute(object : BaseSubscriber<BasePagingResp<MutableList<Evaluate>>>(mView) {
            override fun onNext(t: BasePagingResp<MutableList<Evaluate>>) {
                mView.onGetEvaluateListSuccess(t)
            }
        }, mLifecycleProvider)
    }

}
