package com.yizhipin.generalizecenter.presenter

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Comment
import com.yizhipin.base.data.response.Interaction
import com.yizhipin.base.data.response.InteractionDetails
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.generalizecenter.presenter.view.ReportView
import com.yizhipin.generalizecenter.service.impl.GeneralizeServiceImpl
import javax.inject.Inject

class InteractionPresenter @Inject constructor() : BasePresenter<ReportView>() {

    @Inject
    lateinit var mGoodsServiceImpl: GeneralizeServiceImpl

    fun getInteractionList(map: MutableMap<String, String>) {

        mView.showLoading()
        mGoodsServiceImpl.getInteractionList(map).execute(object : BaseSubscriber<BasePagingResp<MutableList<Interaction>>>(mView) {
            override fun onNext(t: BasePagingResp<MutableList<Interaction>>) {
                mView.onGetInteractionListSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun getInteractionDetails(map: MutableMap<String, String>) {

        mView.showLoading()
        mGoodsServiceImpl.getInteractionDetails(map).execute(object : BaseSubscriber<InteractionDetails>(mView) {
            override fun onNext(t: InteractionDetails) {
                mView.onGetInteractionDetailsSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun comment(map: MutableMap<String, String>) {

        mView.showLoading()
        mGoodsServiceImpl.comment(map).execute(object : BaseSubscriber<Comment>(mView) {
            override fun onNext(t: Comment) {
                mView.onCommentSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun giveLike(map: MutableMap<String, String>) {

        mGoodsServiceImpl.giveLike(map).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {

            }
        }, mLifecycleProvider)
    }

}
