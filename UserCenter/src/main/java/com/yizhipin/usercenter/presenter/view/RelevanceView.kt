package com.yizhipin.usercenter.presenter.view

import com.yizhipin.base.data.response.RelevanceUser
import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.presenter.view.BaseView

/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface RelevanceView : BaseView {
    fun getUserSuccess(result: UserInfo)
    fun getRelevanceUserSuccess(result: MutableList<RelevanceUser>)
    fun addRelevanceUserSuccess(result: RelevanceUser)
    fun deleteRelevanceUserSuccess(result: Boolean)
}