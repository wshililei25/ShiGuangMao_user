package com.yizhipin.ordercender.data.api

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.protocol.BaseResp
import com.yizhipin.base.data.response.*
import com.yizhipin.ordercender.data.protocol.ConfirmOrderReq
import com.yizhipin.usercenter.data.api.Api
import io.reactivex.Observable
import retrofit2.http.*


/*
    订单 接口
 */
interface OrderApi {

    /*
        取消订单
     */
    @DELETE("${Api.ORDER_CANCEL}${"/{id}"}")
    fun cancelOrder(@Path("id") id: String): Observable<BaseResp<String>>

    /*
        确认订单
     */
    @POST("order/confirm")
    fun confirmOrder(@Body req: ConfirmOrderReq): Observable<BaseResp<String>>

    @GET("${Api.GOODS_DETAIL}${"/{id}"}")
    fun getGoodsDetail(@Path("id") id: String, @Query("loginUid") loginUid: String): Observable<BaseResp<DressDetails>>

    /**
     * 获取默认地址
     */
    @GET(Api.DEFAULT_ADDRESS)
    fun getDefaultAddress(@Query("uid") uid: String): Observable<BaseResp<ShipAddress>>

    /**
     * 订单详情
     */
    @GET("${Api.ORDER_CANCEL}${"/{id}"}")
    fun getOrderById(@Path("id") id: String): Observable<BaseResp<Order>>

    @GET(Api.ORDER_LIST)
    fun getOrderList(@Query("currentPage") currentPage: String, @Query("uid") uid: String
                     , @Query("status") status: String): Observable<BasePagingResp<MutableList<Order>>>

    /**
     * 提交订单
     */
    @POST(Api.SUBMIT_ORDER)
    fun submitOrder(): Observable<BaseResp<String>>

    @POST(Api.MEAL_FRONT_MONEY)
    fun mealFrontMoney(): Observable<BaseResp<String>>

    @POST(Api.MEAL_BALANCE_PAYMENT)
    fun mealBalancePayment(): Observable<BaseResp<String>>

    @POST(Api.DRESS_BUY)
    fun dressBuy(): Observable<BaseResp<BuyResult>>

    @POST(Api.DRESS_HIRE)
    fun dressHire(): Observable<BaseResp<BuyResult>>

    /**
     * 提交订单(一品小住)
     */
    @POST(Api.SUBMIT_ORDER_RESIDE)
    fun submitOrderReside(): Observable<BaseResp<String>>

    /**
     * 优惠券列表
     */
    @GET(Api.COUPON_LIST)
    fun getCouponList(@Query("currentPage") currentPage: String, @Query("uid") uid: String): Observable<BasePagingResp<MutableList<Coupon>>>

    /**
     * 红包记录
     */
    @GET(Api.RED_PACKET_LIST)
    fun getRedPacketList(@Query("currentPage") currentPage: String, @Query("uid") uid: String): Observable<BasePagingResp<MutableList<RedPacket>>>

    /**
     * 红包总额
     */
    @GET("${Api.RED_BALANCE}${"/{uid}"}")
    fun getRedBalance(@Path("uid") uid: String): Observable<BaseResp<String>>

    @GET("${Api.MEAL_ORDER}${"/{id}"}")
    fun getOrderDetails(@Path("id") id: String): Observable<BaseResp<OrderDetails>>
}
