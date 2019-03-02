package com.yizhipin.ordercender.presenter.view

import com.yizhipin.base.data.response.DressDetails
import com.yizhipin.base.data.response.ShipAddress
import com.yizhipin.base.presenter.view.BaseView

/*
    订单确认页 视图回调
 */
interface OrderConfirmView : BaseView {
    fun onGetGoodsDetailSuccess(result: DressDetails)
    fun onGetDefaultAddressSuccess(result: ShipAddress)
}
