package com.yizhipin.goods.presenter.view

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.TeacherWorks
import com.yizhipin.base.presenter.view.BaseView

/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface TeacherWorksView : BaseView {
    fun onGetTeacherWorksSuccess(result: BasePagingResp<MutableList<TeacherWorks>>)
}