package com.yizhipin.shop.service.impl

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.*
import com.yizhipin.base.ext.convert
import com.yizhipin.base.ext.convertBoolean
import com.yizhipin.base.ext.convertPaging
import com.yizhipin.shop.data.repository.ShopRepository
import com.yizhipin.shop.service.ShopService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class ShopServiceImpl @Inject constructor() : ShopService {

    @Inject
    lateinit var mRepository: ShopRepository

    override fun getShopList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Store>>> {
        return mRepository.getShopList(map)
                .convertPaging()
    }

    override fun getSceincList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<ScenicSpot>>> {
        return mRepository.getSceincList(map)
                .convertPaging()
    }

    override fun getShopDetails(map: MutableMap<String, String>): Observable<ShopDetails> {
        return mRepository.getShopDetails(map)
                .convert()
    }

    override fun getScenicDetails(map: MutableMap<String, String>): Observable<ScenicSpot> {
        return mRepository.getScenicDetails(map)
                .convert()
    }

    override fun getBanner(map: MutableMap<String, String>): Observable<MutableList<Banner>> {
        return mRepository.getBanner(map)
                .convert()
    }

    override fun getHotMealData(map: MutableMap<String, String>): Observable<MutableList<Meal>> {
        return mRepository.getHotMealData(map)
                .convert()
    }

    override fun getMealData(map: MutableMap<String, String>): Observable<MutableList<Meal>> {
        return mRepository.getMealData(map).convert()
    }

    override fun orderScenic(map: MutableMap<String, String>): Observable<OrderDetails> {
        return mRepository.orderScenic(map).convert()
    }

    override fun getTimeTeacherData(map: MutableMap<String, String>): Observable<MutableList<Teacher>> {
        return mRepository.getTimeTeacherData(map)
                .convert()
    }

    override fun getEvaluateData(map: MutableMap<String, String>): Observable<MutableList<Evaluate>> {
        return mRepository.getEvaluateData(map)
                .convert()
    }

    override fun getFollow(map: MutableMap<String, String>): Observable<Boolean> {
        return mRepository.getFollow(map)
                .convertBoolean()
    }

    override fun getFollowScenic(map: MutableMap<String, String>): Observable<Boolean> {
        return mRepository.getFollowScenic(map)
                .convertBoolean()
    }

}