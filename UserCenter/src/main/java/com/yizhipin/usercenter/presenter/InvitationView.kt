package com.yizhipin.usercenter.presenter

import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.presenter.view.BaseView

/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface InvitationView : BaseView {
    fun getIntegralListSuccess(result: MutableList<UserInfo>)
    fun addIntegralSuccess(result: UserInfo)
}