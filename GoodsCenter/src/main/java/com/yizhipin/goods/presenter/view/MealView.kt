package com.yizhipin.goods.presenter.view

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Meal
import com.yizhipin.base.presenter.view.BaseView

interface MealView : BaseView {

    fun onGetMealListSuccess(result: BasePagingResp<MutableList<Meal>>)
}
