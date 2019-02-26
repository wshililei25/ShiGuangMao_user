package com.yizhipin.goods.presenter

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Meal
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.goods.presenter.view.MealView
import com.yizhipin.goods.service.impl.GoodsServiceImpl
import javax.inject.Inject

class MealPresenter @Inject constructor() : BasePresenter<MealView>() {

    @Inject
    lateinit var mGoodsServiceImpl: GoodsServiceImpl

    fun getMealList(map: MutableMap<String, String>) {
//        mView.showLoading()
        mGoodsServiceImpl.getMealList(map).execute(object : BaseSubscriber<BasePagingResp<MutableList<Meal>>>(mView) {
            override fun onNext(t: BasePagingResp<MutableList<Meal>>) {
                mView.onGetMealListSuccess(t)
            }
        }, mLifecycleProvider)
    }
}
