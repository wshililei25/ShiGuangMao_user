package com.yizhipin.presenter

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Cameraman
import com.yizhipin.base.data.response.TeacherFollow
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.goods.presenter.view.TeacherView
import com.yizhipin.presenter.view.TeacherFollowView
import com.yizhipin.usercenter.service.impl.MainServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class TeacherFollowPresenter @Inject constructor() : BasePresenter<TeacherFollowView>() {

    @Inject
    lateinit var mServiceImpl: MainServiceImpl

    fun getCameramanList(map: MutableMap<String, String>) {
        mServiceImpl.getCameramanList(map).execute(object : BaseSubscriber<BasePagingResp<MutableList<TeacherFollow>>>(mView) {
            override fun onNext(t: BasePagingResp<MutableList<TeacherFollow>>) {
                mView.onGetCameramanListSuccess(t)
            }
        }, mLifecycleProvider)
    }

}