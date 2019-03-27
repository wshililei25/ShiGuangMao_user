package com.yizhipin.presenter.view

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.TeacherFollow
import com.yizhipin.base.presenter.view.BaseView

/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface TeacherFollowView : BaseView {
    fun onGetCameramanListSuccess(result: BasePagingResp<MutableList<TeacherFollow>>)
}