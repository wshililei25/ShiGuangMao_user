package com.yizhipin.ordercender.serivice

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.DressDetails
import com.yizhipin.ordercender.data.response.Order
import com.yizhipin.ordercender.data.response.ShipAddress
import io.reactivex.Observable

/*
    订单业务 接口
 */
interface OrderService {
    fun getGoodsDetail(map: MutableMap<String, String>): Observable<DressDetails>
    fun getOrderById(map: MutableMap<String, String>): Observable<Order>
    fun submitOrder(map: MutableMap<String, String>): Observable<String>
    fun submitOrderReside(map: MutableMap<String, String>): Observable<String>
    fun getOrderList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Order>>>
    fun cancelOrder(map: MutableMap<String, String>): Observable<Boolean>
    fun confirmOrder(orderId: Int): Observable<Boolean>
    fun getDefaultAddress(map: MutableMap<String, String>): Observable<ShipAddress>
    fun mealFrontMoney(map: MutableMap<String, String>): Observable<String?>
}
