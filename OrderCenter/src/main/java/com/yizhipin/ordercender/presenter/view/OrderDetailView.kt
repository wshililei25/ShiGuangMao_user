package com.yizhipin.ordercender.presenter.view

import com.yizhipin.base.data.response.OrderDetails
import com.yizhipin.base.presenter.view.BaseView

/*
    订单详情页 视图回调
 */
interface OrderDetailView : BaseView {
    fun onOrderDetailsSuccess(result: OrderDetails)
}
