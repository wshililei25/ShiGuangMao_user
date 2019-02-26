package com.yizhipin.goods.presenter.view

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.DressCategory
import com.yizhipin.base.data.response.TimeSuperMarket
import com.yizhipin.base.presenter.view.BaseView

interface TimeSuperMarketView : BaseView {

    fun onGetTimeSuperMarketCategorySuccess(result: MutableList<DressCategory>)
    fun onGetTimeSuperMarketListSuccess(result: BasePagingResp<MutableList<TimeSuperMarket>>)
    fun onGetTimeSuperMarketDetailSuccess(result: TimeSuperMarket)
    fun onFollowSuccess(result: Boolean)
}
