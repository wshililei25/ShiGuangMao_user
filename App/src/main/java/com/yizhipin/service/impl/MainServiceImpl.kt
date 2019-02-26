package com.yizhipin.usercenter.service.impl

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.News
import com.yizhipin.base.data.response.OssAddress
import com.yizhipin.base.data.response.ScenicSpot
import com.yizhipin.base.data.response.Store
import com.yizhipin.base.ext.convert
import com.yizhipin.base.ext.convertPaging
import com.yizhipin.base.data.response.Banner
import com.yizhipin.usercenter.data.repository.MainRepository
import com.yizhipin.usercenter.service.MainService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class MainServiceImpl @Inject constructor() : MainService {

    @Inject
    lateinit var mRepository: MainRepository

    override fun getBanner(): Observable<MutableList<Banner>> {
        return mRepository.getBanner()
                .convert()
    }
    override fun getShopList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Store>>> {
        return mRepository.getShopList(map)
                .convertPaging()
    }

    override fun getGoodsList(map: MutableMap<String, String>): Observable<MutableList<ScenicSpot>> {

        return mRepository.getGoodsList(map).convert()
    }

    override fun getUnreadNewCount(map: MutableMap<String, String>): Observable<Int> {
        return mRepository.getUnreadNewCount(map)
                .convert()
    }
    override fun getDefaultStore(map: MutableMap<String,String>): Observable<Store> {

        return mRepository.getDefaultStore(map).convert()
    }
    override fun getNews(map: MutableMap<String,String>): Observable<BasePagingResp<MutableList<News>>> {

        return mRepository.getNews(map).convertPaging()
    }

    override fun getOssAddress(): Observable<OssAddress> {

        return mRepository.getOssAddress().convert()
    }
}