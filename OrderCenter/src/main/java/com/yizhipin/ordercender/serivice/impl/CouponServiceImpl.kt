package com.yizhipin.ordercender.serivice.impl

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Coupon
import com.yizhipin.base.data.response.RedPacket
import com.yizhipin.base.ext.convert
import com.yizhipin.base.ext.convertPaging
import com.yizhipin.ordercender.data.repository.OrderRepository
import com.yizhipin.ordercender.serivice.CouponService
import io.reactivex.Observable
import javax.inject.Inject

class CouponServiceImpl @Inject constructor() : CouponService {

    @Inject
    lateinit var repository: OrderRepository

    override fun getCouponList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Coupon>>> {
        return repository.getCouponList(map).convertPaging()

    }
    override fun getRedPacketList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<RedPacket>>> {
        return repository.getRedPacketList(map).convertPaging()

    }
    override fun getRedBalance(map: MutableMap<String, String>): Observable<String> {
        return repository.getRedBalance(map).convert()

    }
}
