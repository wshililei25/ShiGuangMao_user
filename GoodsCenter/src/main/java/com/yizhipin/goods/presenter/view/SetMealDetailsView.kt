package com.yizhipin.goods.presenter.view

import com.yizhipin.base.data.response.BasicServices
import com.yizhipin.base.data.response.Evaluate
import com.yizhipin.base.data.response.OrderDetails
import com.yizhipin.base.data.response.SetMealDetails
import com.yizhipin.base.presenter.view.BaseView

/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface SetMealDetailsView : BaseView {
    fun onGetMealDetailsSuccess(result: SetMealDetails)
    fun onOrderSuccess(result: OrderDetails)
    fun onOrderDetailsSuccess(result: OrderDetails)
    fun onFollowSuccess(result: Boolean)
    fun onGetEvaluateSuccess(result: MutableList<Evaluate>)
    fun onGetBasicServicesSuccess(result: MutableList<BasicServices>)
}