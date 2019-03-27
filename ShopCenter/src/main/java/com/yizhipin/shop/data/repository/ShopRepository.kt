package com.yizhipin.shop.data.repository

import com.yizhipin.base.data.net.RetrofitFactoryGet
import com.yizhipin.base.data.net.RetrofitFactoryPost
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.protocol.BaseResp
import com.yizhipin.base.data.response.*
import com.yizhipin.shop.data.api.ShopApi
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/27.
 */
class ShopRepository @Inject constructor() {

    fun getShopList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Store>>> {
        return RetrofitFactoryGet().create(ShopApi::class.java)
                .getShopList(map["currentPage"]!!)
    }

    fun getSceincList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<ScenicSpot>>> {
        return RetrofitFactoryGet().create(ShopApi::class.java)
                .getSceincList(map["currentPage"]!!, map["hot"]!!)
    }

    fun getShopDetails(map: MutableMap<String, String>): Observable<BaseResp<ShopDetails>> {
        return RetrofitFactoryGet().create(ShopApi::class.java)
                .getShopDetails(map["id"]!!, map["loginUid"]!!)
    }

    fun getScenicDetails(map: MutableMap<String, String>): Observable<BaseResp<ScenicSpot>> {
        return RetrofitFactoryGet().create(ShopApi::class.java)
                .getScenicDetails(map["id"]!!, map["loginUid"]!!)
    }

    fun getBanner(map: MutableMap<String, String>): Observable<BaseResp<MutableList<Banner>>> {
        return RetrofitFactoryGet().create(ShopApi::class.java)
                .getBanner(map["storeId"]!!)
    }

    fun getHotMealData(map: MutableMap<String, String>): Observable<BaseResp<MutableList<Meal>>> {
        return RetrofitFactoryGet().create(ShopApi::class.java)
                .getHotMealData(map["storeId"]!!)
    }

    fun getMealData(map: MutableMap<String, String>): Observable<BaseResp<MutableList<Meal>>> {
        return RetrofitFactoryGet().create(ShopApi::class.java).getMealData(map["id"]!!)
    }
    fun orderScenic(map: MutableMap<String, String>): Observable<BaseResp<OrderDetails>> {
        return RetrofitFactoryPost(map).create(ShopApi::class.java).orderScenic()
    }

    fun getTimeTeacherData(map: MutableMap<String, String>): Observable<BaseResp<MutableList<Teacher>>> {
        return RetrofitFactoryGet().create(ShopApi::class.java)
                .getTimeTeacherData(map["storeId"]!!)
    }

    fun getFollow(map: MutableMap<String, String>): Observable<BaseResp<Boolean>> {
        return RetrofitFactoryPost(map).create(ShopApi::class.java)
                .getFollow()
    }

    fun getFollowScenic(map: MutableMap<String, String>): Observable<BaseResp<Boolean>> {
        return RetrofitFactoryPost(map).create(ShopApi::class.java)
                .getFollowScenic()
    }

    fun getEvaluateList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Evaluate>>> {
        return RetrofitFactoryGet().create(ShopApi::class.java).getEvaluateList(map["currentPage"]!! ,map["packageId"]!!, map["storeId"]!!)
    }
}