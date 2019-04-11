package com.yizhipin.shop.presenter

import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.shop.presenter.view.WithdrawView
import com.yizhipin.shop.service.impl.PayServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class WithdrawPresenter @Inject constructor() : BasePresenter<WithdrawView>() {

    @Inject
    lateinit var mServiceImpl: PayServiceImpl

    fun getUserInfo(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getUserInfo(map).execute(object : BaseSubscriber<UserInfo>(mView) {
            override fun onNext(t: UserInfo) {
                mView.getUserResult(t)
            }
        }, mLifecycleProvider)
    }

    fun applyWithdraw(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.applyWithdraw(map).execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.onApplyWithdrawSuccess(t)
            }
        }, mLifecycleProvider)
    }


}

