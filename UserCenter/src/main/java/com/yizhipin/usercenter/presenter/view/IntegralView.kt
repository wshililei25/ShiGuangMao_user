package com.yizhipin.usercenter.presenter.view

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.FeeRecord
import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.presenter.view.BaseView

/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface IntegralView : BaseView {
    fun getUserResult(result: UserInfo)
    fun getIntegralListSuccess(result: BasePagingResp<MutableList<FeeRecord>>)
}