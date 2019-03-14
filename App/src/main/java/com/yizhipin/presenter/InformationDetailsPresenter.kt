package com.yizhipin.presenter

import com.yizhipin.base.data.response.News
import com.yizhipin.base.data.response.Store
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.presenter.view.HomeView
import com.yizhipin.presenter.view.InformationDetailsView
import com.yizhipin.usercenter.service.impl.MainServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class InformationDetailsPresenter @Inject constructor() : BasePresenter<InformationDetailsView>() {

    @Inject
    lateinit var mServiceImpl: MainServiceImpl

    fun getInformationDetails(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getInformationDetails(map)
                .execute(object : BaseSubscriber<News>(mView) {
                    override fun onNext(t: News) {
                        mView.onGetInformationDetailsSuccess(t)
                    }
                }, mLifecycleProvider)

    }

}

