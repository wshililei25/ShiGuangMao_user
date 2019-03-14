package com.yizhipin.goods.presenter.view

import com.yizhipin.base.data.response.DressDetails
import com.yizhipin.base.data.response.OrderDetails
import com.yizhipin.base.presenter.view.BaseView

interface DressDetailView : BaseView {

    fun onGetGoodsDetailSuccess(result: DressDetails)
    fun onFollowDressSuccess(result: DressDetails)
    fun onOrderSuccess(result: OrderDetails)
}
