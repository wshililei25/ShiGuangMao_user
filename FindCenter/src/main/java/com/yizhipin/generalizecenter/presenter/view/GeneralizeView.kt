package com.yizhipin.generalizecenter.presenter.view

import com.yizhipin.base.data.response.GeneralizeGroupDetails
import com.yizhipin.base.presenter.view.BaseView

/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface GeneralizeView : BaseView {
    fun onGetGroupDetailsSuccess(result: GeneralizeGroupDetails)
    fun onPayPersonageSuccess(result: String)
    fun onGetEndTimeSuccess(result: String)
}