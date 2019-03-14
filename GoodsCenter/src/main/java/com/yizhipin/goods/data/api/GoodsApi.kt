package com.yizhipin.goods.data.api

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.protocol.BaseResp
import com.yizhipin.base.data.response.*
import com.yizhipin.usercenter.data.api.Api
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/*
    商品接口
 */
interface GoodsApi {

    @GET("${Api.GOODS_DETAIL}${"/{id}"}")
    fun getGoodsDetail(@Path("id") id: String, @Query("loginUid") loginUid: String): Observable<BaseResp<DressDetails>>

    @GET(Api.STAR)
    fun getStar(): Observable<BaseResp<Star>>

    @GET("${Api.DRESS_TIME_SUPERMARKET_DETAILS}${"/{id}"}")
    fun getTimeSuperMarketDetail(@Path("id") id: String, @Query("loginUid") loginUid: String): Observable<BaseResp<TimeSuperMarket>>

    @GET("${Api.INTEGRAL_MALL_DETAILS}${"/{id}"}")
    fun getIntegralDetail(@Path("id") id: String): Observable<BaseResp<Integral>>

    @GET(Api.GOODS_NORM)
    fun getGoodNorm(@Query("clothId") clothId: String): Observable<BaseResp<MutableList<DressNorm>>>

    @POST(Api.FOLLOW_DRESS)
    fun followDress(): Observable<BaseResp<DressDetails>>

    @GET(Api.DRESS_CATEGORY)
    fun getDressCategory(@Query("sex") sex: String, @Query("type") type: String): Observable<BaseResp<MutableList<DressCategory>>>

    @GET(Api.DRESS_TIME_SUPERMARKET)
    fun getTimeSuperMarketCategory(): Observable<BaseResp<MutableList<DressCategory>>>

    @GET(Api.DRESS_LIST)
    fun getDressList(@Query("currentPage") currentPage: String, @Query("sex") sex: String,
                     @Query("catagory") catagory: String, @Query("sellType") sellType: String): Observable<BasePagingResp<MutableList<Dress>>>

    @GET(Api.CAMERAMAN_LIST)
    fun getCameramanList(@Query("currentPage") currentPage: String, @Query("storeId") storeId: String, @Query("teacherType") teacherType: String): Observable<BasePagingResp<MutableList<Cameraman>>>

    @POST(Api.ADD_CAMERAMAN)
    fun addCameraman(): Observable<BaseResp<AddCameraman>>

    @GET(Api.DRESS_TIME_SUPERMARKET_LIST)
    fun getTimeSuperMarketList(@Query("currentPage") currentPage: String, @Query("catagory") catagory: String): Observable<BasePagingResp<MutableList<TimeSuperMarket>>>

    @GET(Api.INTEGRAL_MALL_LIST)
    fun getIntegralList(@Query("currentPage") currentPage: String, @Query("uid") uid: String): Observable<BasePagingResp<MutableList<Integral>>>

    @GET(Api.MEAL_LIST)
    fun getMealList(@Query("currentPage") currentPage: String, @Query("storeId") sex: String,
                    @Query("type") catagory: String, @Query("packageType") sellType: String): Observable<BasePagingResp<MutableList<Meal>>>

    /**
     * 最新评价
     */
    @GET("${Api.EVALUATE_NEW}")
    fun getEvaluateNew(@Query("pid") pid: String): Observable<BaseResp<Evaluate>>

    /**
     * 最新体验报告
     */
    @GET("${Api.REPORT_NEW}")
    fun getReportNew(@Query("pid") pid: String): Observable<BaseResp<Report>>

    /**
     * 购物车数量
     */
    @GET("${Api.CART_COUNT}")
    fun getCartCountData(@Query("uid") uid: String): Observable<BaseResp<String>>

    /**
     * 评价列表(未登陆时)
     */
    @GET("${Api.EVALUATE_LIST}")
    fun getEvaluateListNotLogin(@Query("currentPage") currentPage: String, @Query("pid") pid: String, @Query("shopId") shopId: String): Observable<BasePagingResp<MutableList<Evaluate>>>

    /**
     * 评价列表
     */
    @GET("${Api.EVALUATE_LIST}")
    fun getEvaluateList(@Query("currentPage") currentPage: String, @Query("pid") pid: String, @Query("shopId") shopId: String, @Query("loginUid") loginUid: String): Observable<BasePagingResp<MutableList<Evaluate>>>

    /**
     * 体验报告列表(未登陆时)
     */
    @GET("${Api.REPORT_LIST}")
    fun getReportListNotLogin(@Query("currentPage") currentPage: String, @Query("pid") pid: String, @Query("shopId") shopId: String, @Query("uid") uid: String): Observable<BasePagingResp<MutableList<Evaluate>>>

    /**
     * 体验报告列表
     */
    @GET("${Api.REPORT_LIST}")
    fun getReportList(@Query("currentPage") currentPage: String, @Query("pid") pid: String, @Query("shopId") shopId: String, @Query("uid") uid: String, @Query("loginUid") loginUid: String): Observable<BasePagingResp<MutableList<Evaluate>>>

    /**
     * 点赞评价
     */
    @POST("${Api.GIVE_LIKE}")
    fun giveLike(): Observable<BaseResp<Boolean>>

    /**
     * 点赞体验报告
     */
    @POST("${Api.GIVE_LIKE_REPORT}")
    fun giveLikeReport(): Observable<BaseResp<Boolean>>

    @GET("${Api.CAMERAMAN_DETAILS}${"/{id}"}")
    fun getCameramanDetails(@Path("id") id: String, @Query("loginUid") loginUid: String): Observable<BaseResp<Cameraman>>

    @GET("${Api.MEAL_DETAILS}${"/{id}"}")
    fun getMealDetails(@Path("id") id: String, @Query("loginUid") loginUid: String): Observable<BaseResp<SetMealDetails>>

    @POST(Api.ADD_BRIDE_INFO)
    fun addBrideInfo(): Observable<BaseResp<BrideInfo>>

    @POST(Api.MEAL_ORDER)
    fun order(): Observable<BaseResp<OrderDetails>>

    @POST(Api.TEACHER_ORDER)
    fun orderTeacher(): Observable<BaseResp<OrderDetails>>

    @GET("${Api.MEAL_ORDER}${"/{id}"}")
    fun getOrderDetails(@Path("id") id: String): Observable<BaseResp<OrderDetails>>

    /**
     * 用户详情
     */
    @GET("${Api.EDIT_USER_INFO}${"/{id}"}")
    fun getUserDetails(@Path("id") id: String): Observable<BaseResp<UserInfo>>

    /**
     * 拼单列表
     */
    @GET(Api.CROWDORDER_LIST)
    fun getCrowdorderList(@Query("uid") uid: String): Observable<BaseResp<MutableList<UserInfo>>>

    /**
     * 举报投诉
     */
    @POST(Api.COMPLAIN_SHOP)
    fun getComplainShop(): Observable<BaseResp<Complain>>

    /**
     * 附近品团列表
     */
    @GET(Api.SHARE_BILL_LIST)
    fun getShareBillList(@Query("lng") lng: String, @Query("lat") lat: String, @Query("pid") pid: String): Observable<BaseResp<MutableList<ShareBill>>>

    @GET(Api.MEAL_FOLLOW)
    fun getFollow(): Observable<BaseResp<Boolean>>

    @POST(Api.CAMERAMAN_FOLLOW)
    fun getCameramanFollow(): Observable<BaseResp<Boolean>>

    @GET(Api.MARKET_FOLLOW)
    fun getFollowMarket(): Observable<BaseResp<Boolean>>

    @GET(Api.EVALUATE)
    fun getEvaluateData(@Query("packageId") storeId: String): Observable<BaseResp<MutableList<Evaluate>>>

    @GET(Api.BASIC_SERVICES)
    fun getBasicServicesData(@Query("storeId") storeId: String): Observable<BaseResp<MutableList<BasicServices>>>

    @GET(Api.CLOUDLISK_LIST)
    fun getCloudDiskList(@Query("currentPage") currentPage: String, @Query("uid") uid: String, @Query("type") type: String): Observable<BasePagingResp<MutableList<CloudDisk>>>

    @GET(Api.CLOUDLISK_IMAGE_LIST)
    fun getCloudDiskImageList(@Query("currentPage") currentPage: String, @Query("folderId") folderId: String): Observable<BasePagingResp<MutableList<CloudDiskImage>>>

    @GET(Api.CAMERAMAN_WORKS)
    fun getTeacherWorks(@Query("currentPage") currentPage: String, @Query("uid") uid: String): Observable<BasePagingResp<MutableList<CameranmanWorks>>>
}
