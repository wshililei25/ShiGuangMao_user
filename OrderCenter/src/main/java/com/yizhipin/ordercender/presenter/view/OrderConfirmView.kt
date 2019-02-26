package com.yizhipin.ordercender.presenter.view

import com.yizhipin.base.data.response.DressDetails
import com.yizhipin.base.presenter.view.BaseView
import com.yizhipin.ordercender.data.response.ShipAddress

/*
    订单确认页 视图回调
 */
interface OrderConfirmView : BaseView {
    fun onGetGoodsDetailSuccess(result: DressDetails)
    fun onGetDefaultAddressSuccess(result:ShipAddress)
}
