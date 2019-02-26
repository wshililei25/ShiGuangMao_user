package com.yizhipin.shop.presenter.view

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.ScenicSpot
import com.yizhipin.base.presenter.view.BaseView

/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface SceincView : BaseView {
    fun onGetScenicListSuccess(result: BasePagingResp<MutableList<ScenicSpot>>)
}