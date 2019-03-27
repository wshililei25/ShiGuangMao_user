package com.yizhipin.usercenter.service

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.*
import io.reactivex.Observable


/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface MainService {

    fun getBanner(): Observable<MutableList<Banner>>
    fun getGoodsList(map: MutableMap<String, String>): Observable<MutableList<ScenicSpot>>
    fun getDefaultStore(map: MutableMap<String, String>): Observable<Store>
    fun getInformation(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<News>>>
    fun getOssAddress(): Observable<OssAddress>
    fun getUnreadNewCount(map: MutableMap<String, String>): Observable<Int>
    fun getShopList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Store>>>
    fun getMealList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<MealFollow>>>
    fun getCameramanList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<TeacherFollow>>>
    fun getShopFollowList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<StoreFollow>>>
    fun getInformationDetails(map: MutableMap<String, String>): Observable<News>
    fun getHelpList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Help>>>
    fun getIsShowRedPackage(map: MutableMap<String, String>): Observable<Boolean>
    fun getNews(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<News>>>
}
