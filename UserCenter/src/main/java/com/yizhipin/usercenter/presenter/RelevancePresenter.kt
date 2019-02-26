package com.yizhipin.usercenter.presenter

import com.yizhipin.base.data.response.RelevanceUser
import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.usercenter.presenter.view.RelevanceView
import com.yizhipin.usercenter.service.impl.UserServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class RelevancePresenter @Inject constructor() : BasePresenter<RelevanceView>() {

    @Inject
    lateinit var mUserServiceImpl: UserServiceImpl

    fun getUserInfo(map: MutableMap<String, String>) {
        mView.showLoading()
        mUserServiceImpl.getUserInfo(map).execute(object : BaseSubscriber<UserInfo>(mView) {
            override fun onNext(t: UserInfo) {
                mView.getUserSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun getRelevanceUser(map: MutableMap<String, String>) {
        mView.showLoading()
        mUserServiceImpl.getRelevanceUser(map).execute(object : BaseSubscriber<MutableList<RelevanceUser>>(mView) {
            override fun onNext(t: MutableList<RelevanceUser>) {
                mView.getRelevanceUserSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun addRelevanceUser(map: MutableMap<String, String>) {
        mView.showLoading()
        mUserServiceImpl.addRelevanceUser(map).execute(object : BaseSubscriber<RelevanceUser>(mView) {
            override fun onNext(t: RelevanceUser) {
                mView.addRelevanceUserSuccess(t)
            }
        }, mLifecycleProvider)
    }
    fun updateRelevanceUser(map: MutableMap<String, String>) {
        mView.showLoading()
        mUserServiceImpl.updateRelevanceUser(map).execute(object : BaseSubscriber<RelevanceUser>(mView) {
            override fun onNext(t: RelevanceUser) {
                mView.addRelevanceUserSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun deleteRelevanceUser(map: MutableMap<String, String>) {
        mView.showLoading()
        mUserServiceImpl.deleteRelevanceUser(map).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.deleteRelevanceUserSuccess(t)
            }
        }, mLifecycleProvider)
    }
}

