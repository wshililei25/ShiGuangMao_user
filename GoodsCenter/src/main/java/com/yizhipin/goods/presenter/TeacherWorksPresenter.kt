package com.yizhipin.goods.presenter

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.TeacherWorks
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.goods.presenter.view.TeacherWorksView
import com.yizhipin.goods.service.impl.GoodsServiceImpl
import javax.inject.Inject

class TeacherWorksPresenter @Inject constructor() : BasePresenter<TeacherWorksView>() {

    @Inject
    lateinit var mServiceImpl: GoodsServiceImpl

    fun getTeacherWorks(map: MutableMap<String, String>) {

        mServiceImpl.getTeacherWorks(map).execute(object : BaseSubscriber<BasePagingResp<MutableList<TeacherWorks>>>(mView) {
            override fun onNext(t: BasePagingResp<MutableList<TeacherWorks>>) {
                mView.onGetTeacherWorksSuccess(t)
            }
        }, mLifecycleProvider)

    }

}
