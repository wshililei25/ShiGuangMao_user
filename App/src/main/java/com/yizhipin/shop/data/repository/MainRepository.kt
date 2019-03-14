package com.yizhipin.usercenter.data.repository

import com.yizhipin.base.data.net.RetrofitFactoryGet
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.protocol.BaseResp
import com.yizhipin.base.data.response.*
import com.yizhipin.shop.data.api.MainApi
import com.yizhipin.usercenter.data.api.UserApi
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/27.
 */
class MainRepository @Inject constructor() {


    fun getBanner(): Observable<BaseResp<MutableList<Banner>>> {
        return RetrofitFactoryGet().create(MainApi::class.java)
                .getBanner()
    }

    fun getShopList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Store>>> {
        return RetrofitFactoryGet().create(MainApi::class.java)
                .getShopList(map["currentPage"]!!)
    }

    fun getShopFollowList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<StoreFollow>>> {
        return RetrofitFactoryGet().create(MainApi::class.java)
                .getShopFollowList(map["currentPage"]!!, map["uid"]!!)
    }

    fun getGoodsList(map: MutableMap<String, String>): Observable<BaseResp<MutableList<ScenicSpot>>> {
        return RetrofitFactoryGet().create(MainApi::class.java)
                .getGoodsList(map["storeId"]!!, map["hot"]!!)
    }

    fun getUnreadNewCount(map: MutableMap<String, String>): Observable<BaseResp<Int>> {
        return RetrofitFactoryGet().create(UserApi::class.java)
                .getUnreadNewCount(map["uid"]!!)
    }

    fun getDefaultStore(map: MutableMap<String, String>): Observable<BaseResp<Store>> {
        return RetrofitFactoryGet().create(MainApi::class.java)
                .getDefaultStore(map["lng"]!!, map["lat"]!!)
    }
    fun getInformationDetails(map: MutableMap<String, String>): Observable<BaseResp<News>> {
        return RetrofitFactoryGet().create(MainApi::class.java)
                .getInformationDetails(map["id"]!!)
    }

    fun getNews(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<News>>> {
        return RetrofitFactoryGet().create(MainApi::class.java)
                .getNews(map["currentPage"]!!, map["type"]!!)
    }
    fun getHelpList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Help>>> {
        return RetrofitFactoryGet().create(MainApi::class.java)
                .getHelpList(map["currentPage"]!!)
    }

    fun getOssAddress(): Observable<BaseResp<OssAddress>> {
        return RetrofitFactoryGet().create(MainApi::class.java).getOssAddress()
    }

    fun getMealList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<MealFollow>>> {
        return RetrofitFactoryGet().create(MainApi::class.java).getMealList(map["currentPage"]!!, map["uid"]!!)
    }

    fun getCameramanList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Cameraman>>> {
        return RetrofitFactoryGet().create(MainApi::class.java).getCameramanList(map["currentPage"]!!, map["uid"]!!)
    }
}