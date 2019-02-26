package com.yizhipin.goods.presenter

import com.yizhipin.base.data.response.BrideInfo
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.goods.presenter.view.BrideInfoView
import com.yizhipin.goods.service.impl.GoodsServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class BrideInfoPresenter @Inject constructor() : BasePresenter<BrideInfoView>() {

    @Inject
    lateinit var mServiceImpl: GoodsServiceImpl

    fun addBrideInfo(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.addBrideInfo(map).execute(object : BaseSubscriber<BrideInfo>(mView) {
            override fun onNext(t: BrideInfo) {
                mView.onAddBrideInfoSuccess(t)
            }
        }, mLifecycleProvider)
    }

}

