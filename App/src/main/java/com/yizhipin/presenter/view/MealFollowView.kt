package com.yizhipin.presenter.view

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.MealFollow
import com.yizhipin.base.presenter.view.BaseView

interface MealFollowView : BaseView {

    fun onGetMealListSuccess(result: BasePagingResp<MutableList<MealFollow>>)
}
