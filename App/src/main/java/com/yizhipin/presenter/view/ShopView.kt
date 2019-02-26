package com.yizhipin.presenter.view

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Store
import com.yizhipin.base.presenter.view.BaseView

/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface ShopView : BaseView {
    fun onGetShopListSuccess(result: BasePagingResp<MutableList<Store>>)
}