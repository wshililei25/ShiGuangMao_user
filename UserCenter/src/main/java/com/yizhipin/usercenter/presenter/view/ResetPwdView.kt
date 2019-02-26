package com.yizhipin.usercenter.presenter.view

import com.yizhipin.base.presenter.view.BaseView

/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface ResetPwdView : BaseView {
    fun onGetCodeSuccess(result: Boolean)
    fun onResetPwdSuccess(result: Boolean)
}