package com.yizhipin.ordercender.presenter.view

import com.yizhipin.base.data.response.ShipAddress
import com.yizhipin.base.presenter.view.BaseView

/*
    收货人列表 视图回调
 */
interface ShipAddressView : BaseView {

    //获取收货人列表回调
    fun onGetShipAddressResult(result: MutableList<ShipAddress>?)

    //设置默认收货人回调
    fun onSetDefaultResult(result: Boolean)

    //删除收货人回调
    fun onDeleteResult(result: Boolean)

}
