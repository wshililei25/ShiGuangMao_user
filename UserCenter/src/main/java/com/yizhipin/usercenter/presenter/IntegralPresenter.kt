package com.yizhipin.usercenter.presenter

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.FeeRecord
import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.usercenter.presenter.view.IntegralView
import com.yizhipin.usercenter.service.impl.UserServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class IntegralPresenter @Inject constructor() : BasePresenter<IntegralView>() {

    @Inject
    lateinit var mUserServiceImpl: UserServiceImpl

    fun getUserInfo(map: MutableMap<String, String>) {
        mView.showLoading()
        mUserServiceImpl.getUserInfo(map).execute(object : BaseSubscriber<UserInfo>(mView) {
            override fun onNext(t: UserInfo) {
                mView.getUserResult(t)
            }
        }, mLifecycleProvider)
    }

    fun getIntegralList(map: MutableMap<String, String>) {
        mView.showLoading()
        mUserServiceImpl.getIntegralList(map).execute(object : BaseSubscriber<BasePagingResp<MutableList<FeeRecord>>>(mView) {
            override fun onNext(t: BasePagingResp<MutableList<FeeRecord>>) {
                mView.getIntegralListSuccess(t)
            }
        }, mLifecycleProvider)
    }

}

