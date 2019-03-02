package com.yizhipin.goods.data.repository

import com.yizhipin.base.data.net.RetrofitFactoryGet
import com.yizhipin.base.data.net.RetrofitFactoryPost
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.protocol.BaseResp
import com.yizhipin.base.data.response.*
import com.yizhipin.goods.data.api.GoodsApi
import io.reactivex.Observable
import javax.inject.Inject

/*
    商品数据层
 */
class GoodsRepository @Inject constructor() {

    fun getDressCategory(map: MutableMap<String, String>): Observable<BaseResp<MutableList<DressCategory>>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getDressCategory(map["sex"]!!, map["type"]!!)
    }

    fun getTimeSuperMarketCategory(): Observable<BaseResp<MutableList<DressCategory>>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getTimeSuperMarketCategory()
    }

    fun getDressList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Dress>>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getDressList(map["currentPage"]!!, map["sex"]!!, map["catagory"]!!, map["sellType"]!!)
    }

    fun getCameramanList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Cameraman>>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getCameramanList(map["currentPage"]!!, map["storeId"]!!,map["teacherType"]!!)
    }
    fun addCameraman(map: MutableMap<String, String>): Observable<BaseResp<AddCameraman>> {
        return RetrofitFactoryPost(map).create(GoodsApi::class.java).addCameraman()
    }

    fun getTimeSuperMarketList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<TimeSuperMarket>>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getTimeSuperMarketList(map["currentPage"]!!, map["catagory"]!!)
    }

    fun getIntegralList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Integral>>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getIntegralList(map["currentPage"]!!, map["uid"]!!)
    }

    fun getMealList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Meal>>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getMealList(map["currentPage"]!!, map["storeId"]!!
                , map["type"]!!, map["packageType"]!!)
    }

    fun getGoodsDetail(map: MutableMap<String, String>): Observable<BaseResp<DressDetails>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getGoodsDetail(map["id"]!!, map["loginUid"]!!)
    }

    fun getStar(): Observable<BaseResp<Star>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getStar()
    }

    fun getTimeSuperMarketDetail(map: MutableMap<String, String>): Observable<BaseResp<TimeSuperMarket>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getTimeSuperMarketDetail(map["id"]!!, map["loginUid"]!!)
    }

    fun getIntegralDetail(map: MutableMap<String, String>): Observable<BaseResp<Integral>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getIntegralDetail(map["id"]!!)
    }

    fun getGoodNorm(map: MutableMap<String, String>): Observable<BaseResp<MutableList<DressNorm>>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getGoodNorm(map["clothId"]!!)
    }

    fun followDress(map: MutableMap<String, String>): Observable<BaseResp<DressDetails>> {
        return RetrofitFactoryPost(map).create(GoodsApi::class.java).followDress()
    }

    fun getEvaluateNew(map: MutableMap<String, String>): Observable<BaseResp<Evaluate>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getEvaluateNew(map["pid"]!!)
    }

    fun getReportNew(map: MutableMap<String, String>): Observable<BaseResp<Report>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getReportNew(map["pid"]!!)
    }

    fun getCartCountData(map: MutableMap<String, String>): Observable<BaseResp<String>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getCartCountData(map["uid"]!!)
    }

    fun getEvaluateList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Evaluate>>> {
        if (map["loginUid"].isNullOrEmpty()) {
            return RetrofitFactoryGet().create(GoodsApi::class.java).getEvaluateListNotLogin(map["currentPage"]!!, map["pid"]!!, map["shopId"]!!)
        } else {
            return RetrofitFactoryGet().create(GoodsApi::class.java).getEvaluateList(map["currentPage"]!!, map["pid"]!!, map["shopId"]!!, map["loginUid"]!!)
        }
    }

    fun getReportList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Evaluate>>> {
        if (map["loginUid"].isNullOrEmpty()) {
            return RetrofitFactoryGet().create(GoodsApi::class.java).getReportListNotLogin(map["currentPage"]!!, map["pid"]!!, map["shopId"]!!, map["uid"]!!)
        } else {
            return RetrofitFactoryGet().create(GoodsApi::class.java).getReportList(map["currentPage"]!!, map["pid"]!!, map["shopId"]!!, map["uid"]!!, map["loginUid"]!!)
        }
    }

    fun getCloudDiskList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<CloudDisk>>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getCloudDiskList(map["currentPage"]!!, map["uid"]!!, map["type"]!!)
    }

    fun getCloudDiskImageList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<CloudDiskImage>>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getCloudDiskImageList(map["currentPage"]!!, map["folderId"]!!)
    }

    fun getTeacherWorks(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<CameranmanWorks>>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getTeacherWorks(map["currentPage"]!!, map["uid"]!!)
    }

    fun giveLike(map: MutableMap<String, String>): Observable<BaseResp<Boolean>> {
        return RetrofitFactoryPost(map).create(GoodsApi::class.java).giveLike()
    }

    fun giveLikeReport(map: MutableMap<String, String>): Observable<BaseResp<Boolean>> {
        return RetrofitFactoryPost(map).create(GoodsApi::class.java).giveLikeReport()
    }

    fun getShopDetails(map: MutableMap<String, String>): Observable<BaseResp<Shop>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getShopDetails(map["id"]!!, map["loginUid"]!!)
    }

    fun getCameramanDetails(map: MutableMap<String, String>): Observable<BaseResp<Cameraman>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getCameramanDetails(map["id"]!!, map["loginUid"]!!)
    }

    fun getMealDetails(map: MutableMap<String, String>): Observable<BaseResp<SetMealDetails>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getMealDetails(map["id"]!!, map["loginUid"]!!)
    }
    fun addBrideInfo(map: MutableMap<String, String>): Observable<BaseResp<BrideInfo>> {
        return RetrofitFactoryPost(map).create(GoodsApi::class.java).addBrideInfo()
    }

    fun order(map: MutableMap<String, String>): Observable<BaseResp<OrderDetails>> {
        return RetrofitFactoryPost(map).create(GoodsApi::class.java).order()
    }
    fun getOrderDetails(map: MutableMap<String, String>): Observable<BaseResp<OrderDetails>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getOrderDetails(map["id"]!!)
    }

    fun getUserDetails(map: MutableMap<String, String>): Observable<BaseResp<UserInfo>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getUserDetails(map["id"]!!)
    }

    fun getCrowdorderList(map: MutableMap<String, String>): Observable<BaseResp<MutableList<UserInfo>>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getCrowdorderList(map["uid"]!!)
    }

    fun getComplainShop(map: MutableMap<String, String>): Observable<BaseResp<Complain>> {
        return RetrofitFactoryPost(map).create(GoodsApi::class.java).getComplainShop()
    }

    fun collectShop(map: MutableMap<String, String>): Observable<BaseResp<Collect>> {
        return RetrofitFactoryPost(map).create(GoodsApi::class.java).collectShop()
    }

    fun getShareBillList(map: MutableMap<String, String>): Observable<BaseResp<MutableList<ShareBill>>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getShareBillList(map["lng"]!!, map["lat"]!!, map["pid"]!!)
    }

    fun getFollow(map: MutableMap<String, String>): Observable<BaseResp<Boolean>> {
        return RetrofitFactoryPost(map).create(GoodsApi::class.java).getFollow()
    }

    fun getCameramanFollow(map: MutableMap<String, String>): Observable<BaseResp<Boolean>> {
        return RetrofitFactoryPost(map).create(GoodsApi::class.java).getCameramanFollow()
    }

    fun getFollowMarket(map: MutableMap<String, String>): Observable<BaseResp<Boolean>> {
        return RetrofitFactoryPost(map).create(GoodsApi::class.java).getFollowMarket()
    }

    fun getEvaluateData(map: MutableMap<String, String>): Observable<BaseResp<MutableList<Evaluate>>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getEvaluateData(map["packageId"]!!)
    }

    fun getBasicServicesData(map: MutableMap<String, String>): Observable<BaseResp<MutableList<BasicServices>>> {
        return RetrofitFactoryGet().create(GoodsApi::class.java).getBasicServicesData(map["storeId"]!!)
    }
}
