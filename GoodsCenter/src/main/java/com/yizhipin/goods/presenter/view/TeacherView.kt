package com.yizhipin.goods.presenter.view

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.AddCameraman
import com.yizhipin.base.data.response.Cameraman
import com.yizhipin.base.presenter.view.BaseView

/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface TeacherView : BaseView {
    fun onGetCameramanListSuccess(result: BasePagingResp<MutableList<Cameraman>>)
    fun onAddCameramanSuccess(result: AddCameraman)
}