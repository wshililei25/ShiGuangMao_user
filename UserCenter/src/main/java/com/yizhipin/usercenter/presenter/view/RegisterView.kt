package com.yizhipin.usercenter.presenter.view

import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.presenter.view.BaseView

/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface RegisterView : BaseView {
    fun onGetCodeSuccess(result: Boolean)
    fun onRegisterSuccess(result: UserInfo)
}