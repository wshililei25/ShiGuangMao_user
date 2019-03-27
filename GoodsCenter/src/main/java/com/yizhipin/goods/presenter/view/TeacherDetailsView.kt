package com.yizhipin.goods.presenter.view

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Teacher
import com.yizhipin.base.data.response.TeacherWorks
import com.yizhipin.base.data.response.Evaluate
import com.yizhipin.base.data.response.OrderDetails
import com.yizhipin.base.presenter.view.BaseView

/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface TeacherDetailsView : BaseView {
    fun onGetCameramanDetailsSuccess(result: Teacher)
    fun onFollowSuccess(result: Boolean)
    fun onGetTeacherWorksSuccess(result: BasePagingResp<MutableList<TeacherWorks>>)
    fun onOrderSuccess(result: OrderDetails)
    fun onGetEvaluateListSuccess(result: BasePagingResp<MutableList<Evaluate>>)
}