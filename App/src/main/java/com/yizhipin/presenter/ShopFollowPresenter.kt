package com.yizhipin.presenter

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.StoreFollow
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.presenter.view.ShopFollowView
import com.yizhipin.usercenter.service.impl.MainServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class ShopFollowPresenter @Inject constructor() : BasePresenter<ShopFollowView>() {

    @Inject
    lateinit var mServiceImpl: MainServiceImpl

    fun getShopFollowList(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getShopFollowList(map).execute(object : BaseSubscriber<BasePagingResp<MutableList<StoreFollow>>>(mView) {
            override fun onNext(t: BasePagingResp<MutableList<StoreFollow>>) {
                mView.onGetShopListSuccess(t)
            }
        }, mLifecycleProvider)
    }
}

