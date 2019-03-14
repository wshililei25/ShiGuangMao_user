package com.yizhipin.shop.service

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.*
import io.reactivex.Observable


/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface ShopService {

    fun getShopList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Store>>>
    fun getShopDetails(map: MutableMap<String, String>): Observable<ShopDetails>
    fun getBanner(map: MutableMap<String, String>): Observable<MutableList<Banner>>
    fun getFollow(map: MutableMap<String, String>): Observable<Boolean>
    fun getHotMealData(map: MutableMap<String, String>): Observable<MutableList<Meal>>
    fun getTimeTeacherData(map: MutableMap<String, String>): Observable<MutableList<Teacher>>
    fun getEvaluateData(map: MutableMap<String, String>): Observable<MutableList<Evaluate>>
    fun getSceincList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<ScenicSpot>>>
    fun getScenicDetails(map: MutableMap<String, String>): Observable<ScenicSpot>
    fun getFollowScenic(map: MutableMap<String, String>): Observable<Boolean>
    fun getMealData(map: MutableMap<String, String>): Observable<MutableList<Meal>>
    fun orderScenic(map: MutableMap<String, String>): Observable<OrderDetails>
}
