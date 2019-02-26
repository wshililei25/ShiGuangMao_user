package com.yizhipin.generalizecenter.presenter.view

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Comment
import com.yizhipin.base.data.response.Interaction
import com.yizhipin.base.data.response.InteractionDetails
import com.yizhipin.base.presenter.view.BaseView

interface ReportView : BaseView {

    fun onGetInteractionListSuccess(result: BasePagingResp<MutableList<Interaction>>)
    fun onGetInteractionDetailsSuccess(result: InteractionDetails)
    fun onCommentSuccess(result: Comment)

}
