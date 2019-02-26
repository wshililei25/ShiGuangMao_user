package com.yizhipin.usercenter.service

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.News
import com.yizhipin.base.data.response.OssAddress
import com.yizhipin.base.data.response.ScenicSpot
import com.yizhipin.base.data.response.Store
import com.yizhipin.base.data.response.Banner
import io.reactivex.Observable


/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface MainService {

    fun getBanner(): Observable<MutableList<Banner>>
    fun getGoodsList(map: MutableMap<String, String>): Observable<MutableList<ScenicSpot>>
    fun getDefaultStore(map: MutableMap<String, String>): Observable<Store>
    fun getNews(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<News>>>
    fun getOssAddress(): Observable<OssAddress>
    fun getUnreadNewCount(map: MutableMap<String, String>): Observable<Int>
    fun getShopList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Store>>>
}
