package com.yizhipin.presenter

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Meal
import com.yizhipin.base.data.response.MealFollow
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.goods.presenter.view.MealView
import com.yizhipin.goods.service.impl.GoodsServiceImpl
import com.yizhipin.presenter.view.MealFollowView
import com.yizhipin.usercenter.service.impl.MainServiceImpl
import javax.inject.Inject

class MealFollowPresenter @Inject constructor() : BasePresenter<MealFollowView>() {

    @Inject
    lateinit var mServiceImpl: MainServiceImpl

    fun getMealList(map: MutableMap<String, String>) {
        mServiceImpl.getMealList(map).execute(object : BaseSubscriber<BasePagingResp<MutableList<MealFollow>>>(mView) {
            override fun onNext(t: BasePagingResp<MutableList<MealFollow>>) {
                mView.onGetMealListSuccess(t)
            }
        }, mLifecycleProvider)
    }
}
