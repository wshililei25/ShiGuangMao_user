package com.yizhipin.usercenter.presenter

import com.yizhipin.base.data.response.Complain
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.usercenter.presenter.view.ResetPwdView
import com.yizhipin.usercenter.service.impl.UserServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class ComplainPresenter @Inject constructor() : BasePresenter<ComplainView>() {

    @Inject
    lateinit var mUserServiceImpl: UserServiceImpl

    fun complain(map: MutableMap<String, String>) {

        mView.showLoading()
        mUserServiceImpl.complain(map)
                .execute(object : BaseSubscriber<Complain>(mView) {
                    override fun onNext(t: Complain) {
                        mView.onComplainViewSuccess(t)
                    }
                }, mLifecycleProvider)

    }

}