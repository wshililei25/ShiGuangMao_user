package com.yizhipin.goods.presenter.view

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Evaluate
import com.yizhipin.base.presenter.view.BaseView

/*
    商品详情 视图回调
 */
interface ReportView : BaseView {

    //评价列表
    fun onGetEvaluateListSuccess(result: BasePagingResp<MutableList<Evaluate>>)

}
