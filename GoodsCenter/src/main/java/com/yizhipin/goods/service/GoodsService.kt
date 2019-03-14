package com.yizhipin.goods.service

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.*
import io.reactivex.Observable


/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface GoodsService {
    fun getDressCategory(map: MutableMap<String, String>): Observable<MutableList<DressCategory>>
    fun getDressList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Dress>>>
    fun getGoodsDetail(map: MutableMap<String, String>): Observable<DressDetails>
    fun getEvaluateNew(map: MutableMap<String, String>): Observable<Evaluate>
    fun getReportNew(map: MutableMap<String, String>): Observable<Report>
    fun getCartCountData(map: MutableMap<String, String>): Observable<String>
    fun getEvaluateList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Evaluate>>>
    fun getReportList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Evaluate>>>
    fun giveLike(map: MutableMap<String, String>): Observable<Boolean>
    fun giveLikeReport(map: MutableMap<String, String>): Observable<Boolean>
    fun getComplainShop(map: MutableMap<String, String>): Observable<Complain>
    fun getUserDetails(map: MutableMap<String, String>): Observable<UserInfo>
    fun getCrowdorderList(map: MutableMap<String, String>): Observable<MutableList<UserInfo>>
    fun getShareBillList(map: MutableMap<String, String>): Observable<MutableList<ShareBill>>
    fun followDress(map: MutableMap<String, String>): Observable<DressDetails>
    fun getGoodNorm(map: MutableMap<String, String>): Observable<MutableList<DressNorm>>
    fun getMealList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Meal>>>
    fun getMealDetails(map: MutableMap<String, String>): Observable<SetMealDetails>
    fun getFollow(map: MutableMap<String, String>): Observable<Boolean>
    fun getEvaluateData(map: MutableMap<String, String>): Observable<MutableList<Evaluate>>
    fun getBasicServicesData(map: MutableMap<String, String>): Observable<MutableList<BasicServices>>
    fun getCloudDiskList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<CloudDisk>>>
    fun getCloudDiskImageList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<CloudDiskImage>>>
    fun getTimeSuperMarketCategory(): Observable<MutableList<DressCategory>>
    fun getTimeSuperMarketList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<TimeSuperMarket>>>
    fun getTimeSuperMarketDetail(map: MutableMap<String, String>): Observable<TimeSuperMarket>
    fun getFollowMarket(map: MutableMap<String, String>): Observable<Boolean>
    fun getIntegralList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Integral>>>
    fun getIntegralDetail(map: MutableMap<String, String>): Observable<Integral>
    fun getCameramanList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Cameraman>>>
    fun getCameramanDetails(map: MutableMap<String, String>): Observable<Cameraman>
    fun getCameramanFollow(map: MutableMap<String, String>): Observable<Boolean>
    fun getTeacherWorks(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<CameranmanWorks>>>
    fun getStar(): Observable<Star>
    fun order(map: MutableMap<String, String>): Observable<OrderDetails>
    fun getOrderDetails(map: MutableMap<String, String>): Observable<OrderDetails>
    fun addBrideInfo(map: MutableMap<String, String>): Observable<BrideInfo>
    fun addCameraman(map: MutableMap<String, String>): Observable<AddCameraman>
    fun orderTeacher(map: MutableMap<String, String>): Observable<OrderDetails>
    fun orderDress(map: MutableMap<String, String>): Observable<OrderDetails>
}
