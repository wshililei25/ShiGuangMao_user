package com.yizhipin.ordercender.presenter.view

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Coupon
import com.yizhipin.base.data.response.RedPacket
import com.yizhipin.base.presenter.view.BaseView

interface CouponView : BaseView {

    fun onCouponListSuccess(result: BasePagingResp<MutableList<Coupon>>)
    fun onRedPacketListSuccess(result: BasePagingResp<MutableList<RedPacket>>)
    fun onRedBalanceSuccess(result: String)
}
