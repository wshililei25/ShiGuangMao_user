package com.yizhipin.ordercender.presenter.view

import com.yizhipin.base.data.response.ShipAddress
import com.yizhipin.base.presenter.view.BaseView

/*
    编辑收货人信息 视图回调
 */
interface EditShipAddressView : BaseView {
    //添加收货人回调
    fun onAddShipAddressResult(result: ShipAddress)
    //修改收货人回调
    fun onEditShipAddressResult(result: ShipAddress)
}
