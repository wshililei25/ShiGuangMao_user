package com.yizhipin.usercenter.presenter.view

import com.yizhipin.base.data.response.FeeRecord
import com.yizhipin.base.data.response.Store
import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.presenter.view.BaseView

/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface UserInfoView : BaseView {
    fun getUserResult(result: UserInfo)
    fun getFeeRecordListSuccess(result: MutableList<FeeRecord>)
    fun getUnReadNewCount(result: Int)
    fun onEditUserResult(result: UserInfo)
    fun onGetDefaultStoreSuccess(result: Store)
    fun onGetOssSignSuccess(result: String)
}