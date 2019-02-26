package com.yizhipin.shop.presenter

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.ScenicSpot
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.shop.presenter.view.SceincView
import com.yizhipin.shop.service.impl.ShopServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class SceincPresenter @Inject constructor() : BasePresenter<SceincView>() {

    @Inject
    lateinit var mServiceImpl: ShopServiceImpl

    fun getSceincList(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getSceincList(map).execute(object : BaseSubscriber<BasePagingResp<MutableList<ScenicSpot>>>(mView) {
            override fun onNext(t: BasePagingResp<MutableList<ScenicSpot>>) {
                mView.onGetScenicListSuccess(t)
            }
        }, mLifecycleProvider)
    }
}

