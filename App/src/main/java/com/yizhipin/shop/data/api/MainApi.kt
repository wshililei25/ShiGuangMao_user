package com.yizhipin.shop.data.api

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.protocol.BaseResp
import com.yizhipin.base.data.response.News
import com.yizhipin.base.data.response.OssAddress
import com.yizhipin.base.data.response.ScenicSpot
import com.yizhipin.base.data.response.Store
import com.yizhipin.base.data.response.Banner
import com.yizhipin.usercenter.data.api.Api
import io.reactivex.Observable
import retrofit2.http.GET
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

    /**
     * 获取热门景点
     */
    @GET(Api.HOT_GOODS_LIST)
    fun getGoodsList(@Query("storeId") storeId: String, @Query("hot") hot: String): Observable<BaseResp<MutableList<ScenicSpot>>>

    /**
     * 获取附近门店
     */
    @GET(Api.DEFAULT_STORE)
    fun getDefaultStore(@Query("lng") lng: String, @Query("lat") lat: String): Observable<BaseResp<Store>>

    /**
     * 资讯
     */
    @GET(Api.NEWS)
    fun getNews(@Query("currentPage") currentPage: String, @Query("type") type: String): Observable<BasePagingResp<MutableList<News>>>

}