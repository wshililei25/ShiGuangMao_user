package com.yizhipin.usercenter.presenter

import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.usercenter.presenter.view.RegisterView
import com.yizhipin.usercenter.service.impl.UserServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class RegisterPresenter @Inject constructor() : BasePresenter<RegisterView>() {

    @Inject
    lateinit var mUserServiceImpl: UserServiceImpl

    fun getCode(map: MutableMap<String, String>) {

        mView.showLoading()
        mUserServiceImpl.getCode(map)
                .execute(object : BaseSubscriber<Boolean>(mView) {
                    override fun onNext(t: Boolean) {
                        mView.onGetCodeSuccess(t)
                    }
                }, mLifecycleProvider)

    }
    fun register(map: MutableMap<String, String>) {

        mView.showLoading()
        mUserServiceImpl.register(map)
                .execute(object : BaseSubscriber<UserInfo>(mView) {
                    override fun onNext(t: UserInfo) {
                        mView.onRegisterSuccess(t)
                    }
                }, mLifecycleProvider)

    }
}