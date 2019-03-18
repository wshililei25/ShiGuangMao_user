package com.yizhipin.presenter

import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.presenter.view.MainView
import com.yizhipin.usercenter.service.impl.MainServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class MainPresenter @Inject constructor() : BasePresenter<MainView>() {

    @Inject
    lateinit var mServiceImpl: MainServiceImpl

    fun getIsShowRedPackage(map: MutableMap<String, String>) {
        mServiceImpl.getIsShowRedPackage(map).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onIsShowRedPackageSuccess(t)
            }
        }, mLifecycleProvider)
    }
}

