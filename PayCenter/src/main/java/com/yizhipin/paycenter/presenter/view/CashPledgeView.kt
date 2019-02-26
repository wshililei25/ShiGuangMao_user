package com.yizhipin.shop.presenter.view

import com.yizhipin.base.data.response.CashPledge
import com.yizhipin.base.presenter.view.BaseView

/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface CashPledgeView : BaseView {
    fun onGetCashPledgeSuccess(result: CashPledge)
    fun onRechargeSuccess(result: String)
}