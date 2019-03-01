package com.yizhipin.ordercender.data.repository


import com.yizhipin.base.data.net.RetrofitFactory
import com.yizhipin.base.data.net.RetrofitFactoryDelete
import com.yizhipin.base.data.net.RetrofitFactoryGet
import com.yizhipin.base.data.net.RetrofitFactoryPost
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.protocol.BaseResp
import com.yizhipin.base.data.response.BuyResult
import com.yizhipin.base.data.response.DressDetails
import com.yizhipin.base.data.response.RedPacket
import com.yizhipin.ordercender.data.api.OrderApi
import com.yizhipin.ordercender.data.protocol.ConfirmOrderReq
import com.yizhipin.ordercender.data.response.Coupon
import com.yizhipin.ordercender.data.response.Order
import com.yizhipin.ordercender.data.response.ShipAddress
import io.reactivex.Observable
import javax.inject.Inject

/*
   订单数据层
 */
class OrderRepository @Inject constructor() {

    /*
        取消订单
     */
    fun cancelOrder(map: MutableMap<String, String>): Observable<BaseResp<String>> {
        return RetrofitFactoryDelete(map).create(OrderApi::class.java).cancelOrder(map["id"]!!)
    }

    /*
        确认订单
     */
    fun confirmOrder(orderId: Int): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(OrderApi::class.java).confirmOrder(ConfirmOrderReq(orderId))
    }

    fun getGoodsDetail(map: MutableMap<String, String>): Observable<BaseResp<DressDetails>> {
        return RetrofitFactoryGet().create(OrderApi::class.java).getGoodsDetail(map["id"]!!, map["loginUid"]!!)
    }

    fun getDefaultAddress(map: MutableMap<String, String>): Observable<BaseResp<ShipAddress>> {
        return RetrofitFactoryGet().create(OrderApi::class.java).getDefaultAddress(map["uid"]!!)
    }

    fun getOrderById(map: MutableMap<String, String>): Observable<BaseResp<Order>> {
        return RetrofitFactoryGet().create(OrderApi::class.java).getOrderById(map["id"]!!)
    }

    fun getOrderList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Order>>> {
        return RetrofitFactoryGet().create(OrderApi::class.java).getOrderList(map["currentPage"]!!, map["uid"]!!, map["status"]!!)
    }

    fun submitOrder(map: MutableMap<String, String>): Observable<BaseResp<String>> {
        return RetrofitFactoryPost(map).create(OrderApi::class.java).submitOrder()
    }

    fun mealFrontMoney(map: MutableMap<String, String>): Observable<BaseResp<String>> {
        return RetrofitFactoryPost(map).create(OrderApi::class.java).mealFrontMoney()
    }

    fun dressBuy(map: MutableMap<String, String>): Observable<BaseResp<BuyResult>> {
        return RetrofitFactoryPost(map).create(OrderApi::class.java).dressBuy()
    }

    fun dressHire(map: MutableMap<String, String>): Observable<BaseResp<BuyResult>> {
        return RetrofitFactoryPost(map).create(OrderApi::class.java).dressHire()
    }

    fun submitOrderReside(map: MutableMap<String, String>): Observable<BaseResp<String>> {
        return RetrofitFactoryPost(map).create(OrderApi::class.java).submitOrderReside()
    }

    fun getCouponList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Coupon>>> {
        return RetrofitFactoryGet().create(OrderApi::class.java).getCouponList(map["currentPage"]!!, map["uid"]!!)
    }

    fun getRedPacketList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<RedPacket>>> {
        return RetrofitFactoryGet().create(OrderApi::class.java).getRedPacketList(map["currentPage"]!!, map["uid"]!!)
    }

    fun getRedBalance(map: MutableMap<String, String>): Observable<BaseResp<String>> {
        return RetrofitFactoryGet().create(OrderApi::class.java).getRedBalance(map["uid"]!!)
    }

}
