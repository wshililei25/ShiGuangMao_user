package com.yizhipin.usercenter.data.repository

import com.yizhipin.base.data.net.RetrofitFactoryGet
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.protocol.BaseResp
import com.yizhipin.base.data.response.News
import com.yizhipin.base.data.response.OssAddress
import com.yizhipin.base.data.response.ScenicSpot
import com.yizhipin.base.data.response.Store
import com.yizhipin.shop.data.api.MainApi
import com.yizhipin.base.data.response.Banner
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

    fun getNews(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<News>>> {
        return RetrofitFactoryGet().create(MainApi::class.java)
                .getNews(map["currentPage"]!!, map["type"]!!)
    }

    fun getOssAddress(): Observable<BaseResp<OssAddress>> {
        return RetrofitFactoryGet().create(MainApi::class.java)
                .getOssAddress()
    }
}