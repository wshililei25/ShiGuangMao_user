package com.yizhipin.shop.presenter.view

import com.yizhipin.base.data.response.ShopDetails
import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.presenter.view.BaseView

/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface WithdrawView : BaseView {
    fun getUserResult(result: UserInfo)
    fun onApplyWithdrawSuccess(result: String)
}