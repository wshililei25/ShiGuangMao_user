package com.yizhipin.usercenter.presenter

import com.yizhipin.base.data.response.FeeRecord
import com.yizhipin.base.data.response.OssAddress
import com.yizhipin.base.data.response.Store
import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.usercenter.presenter.view.UserInfoView
import com.yizhipin.usercenter.service.impl.UserServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class UserInfoPresenter @Inject constructor() : BasePresenter<UserInfoView>() {

    @Inject
    lateinit var mServiceImpl: UserServiceImpl

    fun getUserInfo(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getUserInfo(map).execute(object : BaseSubscriber<UserInfo>(mView) {
            override fun onNext(t: UserInfo) {
                mView.getUserResult(t)
            }
        }, mLifecycleProvider)
    }

    fun loadFeeRecordList(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.loadFeeRecordList(map).execute(object : BaseSubscriber<MutableList<FeeRecord>>(mView) {
            override fun onNext(t: MutableList<FeeRecord>) {
                mView.getFeeRecordListSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun getUnreadNewCount(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.getUnreadNewCount(map).execute(object : BaseSubscriber<Int>(mView) {
            override fun onNext(t: Int) {
                mView.getUnReadNewCount(t)
            }
        }, mLifecycleProvider)
    }

    /**
     * 编辑用户资料
     */
    fun editUserInfo(map: MutableMap<String, String>) {

        mView.showLoading()
        mServiceImpl.editUserInfo(map).execute(object : BaseSubscriber<UserInfo>(mView) {
            override fun onNext(t: UserInfo) {
                mView.onEditUserResult(t)
            }
        }, mLifecycleProvider)
    }

    fun getDefaultStore(map: MutableMap<String, String>) {
//        mView.showLoading()
        mServiceImpl.getDefaultStore(map)
                .execute(object : BaseSubscriber<Store>(mView) {
                    override fun onNext(t: Store) {
                        mView.onGetDefaultStoreSuccess(t)
                    }
                }, mLifecycleProvider)

    }

    fun getOssSign(map: MutableMap<String, String>) {

        mServiceImpl.getOssSign(map).execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.onGetOssSignSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun getOssSignFile(map: MutableMap<String, String>) {

        mServiceImpl.getOssSignFile(map).execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.onGetOssSignFileSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun getOssAddress() {
        mServiceImpl.getOssAddress().execute(object : BaseSubscriber<OssAddress>(mView) {
            override fun onNext(t: OssAddress) {
                mView.onGetOssAddressSuccess(t)
            }
        }, mLifecycleProvider)

    }
}

