package com.yizhipin.ordercender.serivice

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Coupon
import com.yizhipin.base.data.response.RedPacket
import io.reactivex.Observable

interface CouponService {

    fun getCouponList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Coupon>>>
    fun getRedPacketList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<RedPacket>>>
    fun getRedBalance(map: MutableMap<String, String>): Observable<String>
}
