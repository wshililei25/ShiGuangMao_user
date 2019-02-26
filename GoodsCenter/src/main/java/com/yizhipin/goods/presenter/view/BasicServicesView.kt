package com.yizhipin.goods.presenter.view

import com.yizhipin.base.data.response.BasicServices
import com.yizhipin.base.presenter.view.BaseView

/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface BasicServicesView : BaseView {
    fun onGetBasicServicesSuccess(result: MutableList<BasicServices>)
}