package com.yizhipin.ordercender.presenter

import com.yizhipin.base.presenter.view.BaseView

interface PayConfirmView : BaseView {

    fun onSubmitOrderSuccess(result:String)
    fun onMealFrontMoneySuccess(result:String?)
}
