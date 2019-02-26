package com.yizhipin.shop.presenter.view

import com.yizhipin.base.data.response.Meal
import com.yizhipin.base.data.response.ScenicSpot
import com.yizhipin.base.presenter.view.BaseView

/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface ScenicDetailsView : BaseView {
    fun onGetScenicDetailsSuccess(result: ScenicSpot)
    fun onFollowSuccess(result: Boolean)
    fun onGetMealSuccess(result: MutableList<Meal>)
}