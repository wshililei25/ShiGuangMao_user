package com.yizhipin.generalizecenter.presenter.view

import com.yizhipin.base.data.response.GeneralizeInvest
import com.yizhipin.base.data.response.GeneralizeInvestAmount
import com.yizhipin.base.data.response.InvestDetails
import com.yizhipin.base.data.response.InvestList
import com.yizhipin.base.presenter.view.BaseView

/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface GeneralizeInvestView : BaseView {
    fun onGetInvestAmountSuccess(result: GeneralizeInvestAmount)
    fun onGetInvestListSuccess(result: MutableList<GeneralizeInvest>)
    fun onGetInvestDetailsListSuccess(result: MutableList<InvestList>)
    fun onGetInvestDetailsSuccess(result: InvestDetails)
}