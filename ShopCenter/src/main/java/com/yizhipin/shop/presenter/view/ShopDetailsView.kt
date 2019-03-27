package com.yizhipin.shop.presenter.view

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.*
import com.yizhipin.base.presenter.view.BaseView

/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface ShopDetailsView : BaseView {
    fun onGetShopDetailsSuccess(result: ShopDetails)
    fun onGetBannerSuccess(result: MutableList<Banner>)
    fun onFollowSuccess(result: Boolean)
    fun onGetHotMealSuccess(result: MutableList<Meal>)
    fun onGetTimeTeacherSuccess(result: MutableList<Teacher>)
    fun onGetEvaluateListSuccess(result: BasePagingResp<MutableList<Evaluate>>)
}