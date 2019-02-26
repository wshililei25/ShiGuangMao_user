package com.yizhipin.goods.presenter.view

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Cameraman
import com.yizhipin.base.data.response.CameranmanWorks
import com.yizhipin.base.data.response.Evaluate
import com.yizhipin.base.presenter.view.BaseView

/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface CameramanDetailsView : BaseView {
    fun onGetCameramanDetailsSuccess(result: Cameraman)
    fun onFollowSuccess(result: Boolean)
    fun onGetEvaluateSuccess(result: MutableList<Evaluate>)
    fun onGetTeacherWorksSuccess(result: BasePagingResp<MutableList<CameranmanWorks>>)
}