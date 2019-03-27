package com.yizhipin.goods.service.impl

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.*
import com.yizhipin.base.ext.convert
import com.yizhipin.base.ext.convertBoolean
import com.yizhipin.base.ext.convertPaging
import com.yizhipin.goods.data.repository.GoodsRepository
import com.yizhipin.goods.service.GoodsService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class GoodsServiceImpl @Inject constructor() : GoodsService {

    @Inject
    lateinit var mRepository: GoodsRepository

    override fun getDressCategory(map: MutableMap<String, String>): Observable<MutableList<DressCategory>> {
        return mRepository.getDressCategory(map).convert()
    }

    override fun getTimeSuperMarketCategory(): Observable<MutableList<DressCategory>> {
        return mRepository.getTimeSuperMarketCategory().convert()
    }

    override fun getDressList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Dress>>> {
        return mRepository.getDressList(map).convertPaging()
    }

    override fun getCameramanList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Teacher>>> {
        return mRepository.getCameramanList(map).convertPaging()
    }

    override fun addCameraman(map: MutableMap<String, String>): Observable<AddCameraman> {
        return mRepository.addCameraman(map).convert()
    }

    override fun getTimeSuperMarketList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<TimeSuperMarket>>> {
        return mRepository.getTimeSuperMarketList(map).convertPaging()
    }

    override fun getIntegralList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Integral>>> {
        return mRepository.getIntegralList(map).convertPaging()
    }

    override fun getMealList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Meal>>> {
        return mRepository.getMealList(map).convertPaging()
    }

    override fun getGoodsDetail(map: MutableMap<String, String>): Observable<DressDetails> {
        return mRepository.getGoodsDetail(map).convert()
    }

    override fun getStar(): Observable<Star> {
        return mRepository.getStar().convert()
    }

    override fun getTimeSuperMarketDetail(map: MutableMap<String, String>): Observable<TimeSuperMarket> {
        return mRepository.getTimeSuperMarketDetail(map).convert()
    }

    override fun getIntegralDetail(map: MutableMap<String, String>): Observable<Integral> {
        return mRepository.getIntegralDetail(map).convert()
    }

    override fun getGoodNorm(map: MutableMap<String, String>): Observable<MutableList<DressNorm>> {
        return mRepository.getGoodNorm(map).convert()
    }

    override fun followDress(map: MutableMap<String, String>): Observable<DressDetails> {
        return mRepository.followDress(map).convert()
    }

    override fun orderDress(map: MutableMap<String, String>): Observable<OrderDetails> {
        return mRepository.orderDress(map).convert()
    }

    override fun getEvaluateNew(map: MutableMap<String, String>): Observable<Evaluate> {
        return mRepository.getEvaluateNew(map).convert()
    }

    override fun getReportNew(map: MutableMap<String, String>): Observable<Report> {
        return mRepository.getReportNew(map).convert()
    }

    override fun getCartCountData(map: MutableMap<String, String>): Observable<String> {
        return mRepository.getCartCountData(map).convert()
    }

    override fun getEvaluateList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Evaluate>>> {
        return mRepository.getEvaluateList(map).convertPaging()
    }

    override fun getEvaluateTeacherList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Evaluate>>> {
        return mRepository.getEvaluateTeacherList(map).convertPaging()
    }

    override fun getReportList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Evaluate>>> {
        return mRepository.getReportList(map).convertPaging()
    }

    override fun giveLike(map: MutableMap<String, String>): Observable<Boolean> {
        return mRepository.giveLike(map).convertBoolean()
    }

    override fun giveLikeReport(map: MutableMap<String, String>): Observable<Boolean> {
        return mRepository.giveLikeReport(map).convertBoolean()
    }

    override fun getMealDetails(map: MutableMap<String, String>): Observable<SetMealDetails> {
        return mRepository.getMealDetails(map).convert()
    }

    override fun addBrideInfo(map: MutableMap<String, String>): Observable<BrideInfo> {
        return mRepository.addBrideInfo(map).convert()
    }

    override fun order(map: MutableMap<String, String>): Observable<OrderDetails> {
        return mRepository.order(map).convert()
    }

    override fun orderTeacher(map: MutableMap<String, String>): Observable<OrderDetails> {
        return mRepository.orderTeacher(map).convert()
    }

    override fun getOrderDetails(map: MutableMap<String, String>): Observable<OrderDetails> {
        return mRepository.getOrderDetails(map).convert()
    }

    override fun getCameramanDetails(map: MutableMap<String, String>): Observable<Teacher> {
        return mRepository.getCameramanDetails(map).convert()
    }

    override fun getUserDetails(map: MutableMap<String, String>): Observable<UserInfo> {
        return mRepository.getUserDetails(map).convert()
    }

    override fun getCrowdorderList(map: MutableMap<String, String>): Observable<MutableList<UserInfo>> {
        return mRepository.getCrowdorderList(map).convert()
    }

    override fun getComplainShop(map: MutableMap<String, String>): Observable<Complain> {
        return mRepository.getComplainShop(map).convert()
    }

    override fun getShareBillList(map: MutableMap<String, String>): Observable<MutableList<ShareBill>> {
        return mRepository.getShareBillList(map).convert()
    }

    override fun getFollow(map: MutableMap<String, String>): Observable<Boolean> {
        return mRepository.getFollow(map).convertBoolean()
    }

    override fun getCameramanFollow(map: MutableMap<String, String>): Observable<Boolean> {
        return mRepository.getCameramanFollow(map).convertBoolean()
    }


    override fun getBasicServicesData(map: MutableMap<String, String>): Observable<MutableList<BasicServices>> {
        return mRepository.getBasicServicesData(map)
                .convert()
    }

    override fun getCloudDiskList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<CloudDisk>>> {
        return mRepository.getCloudDiskList(map).convertPaging()
    }

    override fun getCloudDiskImageList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<CloudDiskImage>>> {
        return mRepository.getCloudDiskImageList(map).convertPaging()
    }

    override fun getTeacherWorks(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<TeacherWorks>>> {
        return mRepository.getTeacherWorks(map).convertPaging()
    }

    override fun getFollowMarket(map: MutableMap<String, String>): Observable<Boolean> {
        return mRepository.getFollowMarket(map)
                .convertBoolean()
    }

}