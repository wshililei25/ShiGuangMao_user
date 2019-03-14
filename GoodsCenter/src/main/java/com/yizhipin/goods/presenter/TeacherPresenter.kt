package com.yizhipin.goods.presenter

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.AddCameraman
import com.yizhipin.base.data.response.Cameraman
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.goods.presenter.view.TeacherView
import com.yizhipin.goods.service.impl.GoodsServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class TeacherPresenter @Inject constructor() : BasePresenter<TeacherView>() {

    @Inject
    lateinit var mServiceImpl: GoodsServiceImpl

    fun getCameramanList(map: MutableMap<String, String>) {
        mServiceImpl.getCameramanList(map).execute(object : BaseSubscriber<BasePagingResp<MutableList<Cameraman>>>(mView) {
            override fun onNext(t: BasePagingResp<MutableList<Cameraman>>) {
                mView.onGetCameramanListSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun addCameraman(map: MutableMap<String, String>) {
        mView.showLoading()
        mServiceImpl.addCameraman(map).execute(object : BaseSubscriber<AddCameraman>(mView) {
            override fun onNext(t: AddCameraman) {
                mView.onAddCameramanSuccess(t)
            }
        }, mLifecycleProvider)
    }
}