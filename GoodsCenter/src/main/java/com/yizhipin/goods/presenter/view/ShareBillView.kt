package com.yizhipin.goods.presenter.view

import com.yizhipin.base.data.response.ShareBill
import com.yizhipin.base.presenter.view.BaseView

/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface ShareBillView : BaseView {
    fun onGetShareBillListSuccess(result: MutableList<ShareBill>)
}