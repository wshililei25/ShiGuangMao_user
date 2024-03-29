package com.yizhipin.presenter

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.*
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.presenter.view.HomeView
import com.yizhipin.presenter.view.HelpView
import com.yizhipin.usercenter.service.impl.MainServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class HelpPresenter @Inject constructor() : BasePresenter<HelpView>() {

    @Inject
    lateinit var mServiceImpl: MainServiceImpl

    fun getHelpList(map: MutableMap<String, String>) {
        mServiceImpl.getHelpList(map)
                .execute(object : BaseSubscriber<BasePagingResp<MutableList<Help>>>(mView) {
                    override fun onNext(t: BasePagingResp<MutableList<Help>>) {
                        mView.onGetHelpSuccess(t)
                    }
                }, mLifecycleProvider)

    }
}

