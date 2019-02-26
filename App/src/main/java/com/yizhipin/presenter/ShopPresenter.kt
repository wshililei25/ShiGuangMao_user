package com.yizhipin.presenter

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Store
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.presenter.view.ShopView
import com.yizhipin.usercenter.service.impl.MainServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class ShopPresenter @Inject constructor() : BasePresenter<ShopView>() {

    @Inject
    lateinit var mServiceImpl: MainServiceImpl

    fun getShopList(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getShopList(map).execute(object : BaseSubscriber<BasePagingResp<MutableList<Store>>>(mView) {
            override fun onNext(t: BasePagingResp<MutableList<Store>>) {
                mView.onGetShopListSuccess(t)
            }
        }, mLifecycleProvider)
    }
}

