package com.yizhipin.goods.presenter

import com.yizhipin.base.data.response.BasicServices
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.goods.presenter.view.BasicServicesView
import com.yizhipin.goods.service.impl.GoodsServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class BasicServicesPresenter @Inject constructor() : BasePresenter<BasicServicesView>() {

    @Inject
    lateinit var mServiceImpl: GoodsServiceImpl

    fun getBasicServicesData(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getBasicServicesData(map).execute(object : BaseSubscriber<MutableList<BasicServices>>(mView) {
            override fun onNext(t: MutableList<BasicServices>) {
                mView.onGetBasicServicesSuccess(t)
            }
        }, mLifecycleProvider)
    }


}

