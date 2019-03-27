package com.yizhipin.shop.data.api

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.protocol.BaseResp
import com.yizhipin.base.data.response.*
import com.yizhipin.usercenter.data.api.Api
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by ${XiLei} on 2018/7/27.
 */
interface MainApi {

    /**
     * 获取图片地址
     */
    @GET(Api.IMAGE_ADDRESS)
    fun getOssAddress(): Observable<BaseResp<OssAddress>>

    /**
     * 获取banner
     */
    @GET("${Api.BANNER}")
    fun getBanner(): Observable<BaseResp<MutableList<Banner>>>

    @GET(Api.SHOP_LIST)
    fun getShopList(@Query("currentPage") currentPage: String): Observable<BasePagingResp<MutableList<Store>>>

    @GET(Api.SHOP_FOLLOW_LIST)
    fun getShopFollowList(@Query("currentPage") currentPage: String, @Query("uid") uid: String): Observable<BasePagingResp<MutableList<StoreFollow>>>

    /**
     * 获取热门景点
     */
    @GET(Api.HOT_GOODS_LIST)
    fun getGoodsList(@Query("hot") hot: String): Observable<BaseResp<MutableList<ScenicSpot>>>

    /**
     * 获取附近门店
     */
    @GET(Api.DEFAULT_STORE)
    fun getDefaultStore(@Query("lng") lng: String, @Query("lat") lat: String): Observable<BaseResp<Store>>

    @GET("${Api.NEWS_DETAILS}${"/{id}"}")
    fun getInformationDetails(@Path("id") id: String): Observable<BaseResp<News>>

    @GET(Api.INFORMATION)
    fun getInformation(@Query("currentPage") currentPage: String, @Query("type") type: String): Observable<BasePagingResp<MutableList<News>>>

    @GET(Api.NEWS_LIST)
    fun getNews(@Query("currentPage") currentPage: String, @Query("uid") uid: String): Observable<BasePagingResp<MutableList<News>>>

    @GET(Api.HELP_LIST)
    fun getHelpList(@Query("currentPage") currentPage: String): Observable<BasePagingResp<MutableList<Help>>>

    @GET(Api.MEAL_FOLLOW_LIST)
    fun getMealList(@Query("currentPage") currentPage: String, @Query("uid") uid: String): Observable<BasePagingResp<MutableList<MealFollow>>>

    @GET(Api.TEACHER_FOLLOW_LIST)
    fun getCameramanList(@Query("currentPage") currentPage: String, @Query("uid") uid: String): Observable<BasePagingResp<MutableList<TeacherFollow>>>
}