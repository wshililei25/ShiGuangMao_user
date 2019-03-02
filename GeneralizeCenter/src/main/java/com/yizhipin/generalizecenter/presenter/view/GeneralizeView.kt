package com.yizhipin.generalizecenter.presenter.view

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.GeneralizeCollect
import com.yizhipin.base.data.response.GeneralizeGroupDetails
import com.yizhipin.base.presenter.view.BaseView

/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface GeneralizeView : BaseView {
    fun onGetGoodsListSuccess(result: BasePagingResp<MutableList<GeneralizeCollect>>)
    fun onGetGoodsDetailsSuccess(result: GeneralizeCollect)
    fun onGetGroupDetailsSuccess(result: GeneralizeGroupDetails)
    fun onPayPersonageSuccess(result: String)
    fun onGetEndTimeSuccess(result: String)
}