package com.yizhipin.usercenter.presenter

import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.usercenter.service.impl.UserServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class InvitationPresenter @Inject constructor() : BasePresenter<InvitationView>() {

    @Inject
    lateinit var mUserServiceImpl: UserServiceImpl

    fun getInvitationList(map: MutableMap<String, String>) {
        mView.showLoading()
        mUserServiceImpl.getInvitationList(map).execute(object : BaseSubscriber<MutableList<UserInfo>>(mView) {
            override fun onNext(t: MutableList<UserInfo>) {
                mView.getIntegralListSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun addInvitation(map: MutableMap<String, String>) {
        mView.showLoading()
        mUserServiceImpl.addInvitation(map).execute(object : BaseSubscriber<UserInfo>(mView) {
            override fun onNext(t: UserInfo) {
                mView.addIntegralSuccess(t)
            }
        }, mLifecycleProvider)
    }

}

