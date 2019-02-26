package com.yizhipin.goods.presenter.view

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Dress
import com.yizhipin.base.data.response.DressCategory
import com.yizhipin.base.presenter.view.BaseView

interface DressView : BaseView {

    fun onGetDressCategorySuccess(result: MutableList<DressCategory>)
    fun onGetDressListSuccess(result: BasePagingResp<MutableList<Dress>>)
}
