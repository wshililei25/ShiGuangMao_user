package com.yizhipin.shop.data.api

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.protocol.BaseResp
import com.yizhipin.base.data.response.*
import com.yizhipin.usercenter.data.api.Api
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by ${XiLei} on 2018/7/27.
 */
interface ShopApi {

    @GET(Api.SHOP_LIST)
    fun getShopList(@Query("currentPage") currentPage: String): Observable<BasePagingResp<MutableList<Store>>>

    @GET(Api.SCENIC_LIST)
    fun getSceincList(@Query("currentPage") currentPage: String, @Query("hot") hot: String): Observable<BasePagingResp<MutableList<ScenicSpot>>>

    @GET("${Api.SHOP_DETAILS}/{id}")
    fun getShopDetails(@Path("id") id: String, @Query("loginUid") loginUid: String): Observable<BaseResp<ShopDetails>>

    @GET("${Api.SCENIC_DETAILS}/{id}")
    fun getScenicDetails(@Path("id") id: String, @Query("loginUid") loginUid: String): Observable<BaseResp<ScenicSpot>>

    @GET(Api.SHOP_BANNER)
    fun getBanner(@Query("storeId") storeId: String): Observable<BaseResp<MutableList<Banner>>>

    @GET(Api.HOT_MEAL)
    fun getHotMealData(@Query("storeId") storeId: String): Observable<BaseResp<MutableList<Meal>>>

    @GET("${Api.MEAL_SENIC}/{id}")
    fun getMealData(@Path("id") storeId: String): Observable<BaseResp<MutableList<Meal>>>

    @POST(Api.ORDER_SCENIC)
    fun orderScenic(): Observable<BaseResp<OrderDetails>>

    @GET(Api.TIME_TEACHER)
    fun getTimeTeacherData(@Query("storeId") storeId: String): Observable<BaseResp<MutableList<Teacher>>>

    @GET(Api.EVALUATE)
    fun getEvaluateData(@Query("storeId") storeId: String): Observable<BaseResp<MutableList<Evaluate>>>

    @GET(Api.SHOP_FOLLOW)
    fun getFollow(): Observable<BaseResp<Boolean>>

    @GET(Api.SCENIC_FOLLOW)
    fun getFollowScenic(): Observable<BaseResp<Boolean>>
}