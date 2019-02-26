package com.yizhipin.goods.presenter.view

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Integral
import com.yizhipin.base.presenter.view.BaseView

interface IntegralView : BaseView {

    fun onGetIntegralListSuccess(result: BasePagingResp<MutableList<Integral>>)
    fun onGetIntegralDetailSuccess(result: Integral)
//    fun onFollowSuccess(result: Boolean)
}
