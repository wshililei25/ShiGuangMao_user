package com.yizhipin.usercenter.service.impl

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.*
import com.yizhipin.base.ext.convert
import com.yizhipin.base.ext.convertBoolean
import com.yizhipin.base.ext.convertPaging
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
        return mRepository.getBanner().convert()
    }

    override fun getShopList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Store>>> {
        return mRepository.getShopList(map).convertPaging()
    }

    override fun getShopFollowList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<StoreFollow>>> {
        return mRepository.getShopFollowList(map).convertPaging()
    }

    override fun getGoodsList(map: MutableMap<String, String>): Observable<MutableList<ScenicSpot>> {
        return mRepository.getGoodsList(map).convert()
    }

    override fun getUnreadNewCount(map: MutableMap<String, String>): Observable<Int> {
        return mRepository.getUnreadNewCount(map).convert()
    }

    override fun getIsShowRedPackage(map: MutableMap<String, String>): Observable<Boolean> {
        return mRepository.getIsShowRedPackage(map).convertBoolean()
    }

    override fun getDefaultStore(map: MutableMap<String, String>): Observable<Store> {
        return mRepository.getDefaultStore(map).convert()
    }

    override fun getInformationDetails(map: MutableMap<String, String>): Observable<News> {
        return mRepository.getInformationDetails(map).convert()
    }
    override fun getNews(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<News>>> {
        return mRepository.getNews(map).convertPaging()
    }
    override fun getInformation(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<News>>> {
        return mRepository.getInformation(map).convertPaging()
    }


    override fun getHelpList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Help>>> {
        return mRepository.getHelpList(map).convertPaging()
    }

    override fun getOssAddress(): Observable<OssAddress> {
        return mRepository.getOssAddress().convert()
    }

    override fun getMealList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<MealFollow>>> {
        return mRepository.getMealList(map).convertPaging()
    }

    override fun getCameramanList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<TeacherFollow>>> {
        return mRepository.getCameramanList(map).convertPaging()
    }
}