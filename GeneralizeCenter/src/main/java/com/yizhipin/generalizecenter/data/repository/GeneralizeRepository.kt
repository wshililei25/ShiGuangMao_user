package com.yizhipin.goods.data.repository

import com.yizhipin.base.data.net.RetrofitFactoryGet
import com.yizhipin.base.data.net.RetrofitFactoryPost
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.protocol.BaseResp
import com.yizhipin.base.data.response.Comment
import com.yizhipin.base.data.response.Interaction
import com.yizhipin.base.data.response.InteractionDetails
import com.yizhipin.generalizecenter.data.response.*
import com.yizhipin.goods.data.api.GeneralizeApi
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/27.
 */
class GeneralizeRepository @Inject constructor() {

    fun getGenBiddingList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<GeneralizeCollect>>> {
        return RetrofitFactoryGet().create(GeneralizeApi::class.java).getGenBiddingList(map["currentPage"]!!, map["bidding"]!!)
    }

    fun getInteractionList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Interaction>>> {
        return RetrofitFactoryGet().create(GeneralizeApi::class.java).getInteractionList(map["currentPage"]!!, map["type"]!!, map["loginUid"]!!)
    }

    fun getInteractionDetails(map: MutableMap<String, String>): Observable<BaseResp<InteractionDetails>> {
        return RetrofitFactoryGet().create(GeneralizeApi::class.java).getInteractionDetails(map["id"]!!)
    }

    fun comment(map: MutableMap<String, String>): Observable<BaseResp<Comment>> {
        return RetrofitFactoryPost(map).create(GeneralizeApi::class.java).comment()
    }

    fun getGenBiddingDetails(map: MutableMap<String, String>): Observable<BaseResp<GeneralizeCollect>> {
        return RetrofitFactoryGet().create(GeneralizeApi::class.java).getGenBiddingDetails(map["id"]!!, map["uid"]!!)
    }

    fun giveLike(map: MutableMap<String, String>): Observable<BaseResp<Boolean>> {
        return RetrofitFactoryPost(map).create(GeneralizeApi::class.java).giveLike()
    }

    fun getGenGroupDetails(map: MutableMap<String, String>): Observable<BaseResp<GeneralizeGroupDetails>> {
        return RetrofitFactoryGet().create(GeneralizeApi::class.java).getGenGroupDetails(map["id"]!!)
    }

    fun payPersonage(map: MutableMap<String, String>): Observable<BaseResp<String>> {
        return RetrofitFactoryPost(map).create(GeneralizeApi::class.java).payPersonage()
    }

    fun getEndTime(): Observable<BaseResp<String>> {
        return RetrofitFactoryGet().create(GeneralizeApi::class.java).getEndTime()
    }

    fun getInvestStatistics(map: MutableMap<String, String>): Observable<BaseResp<GeneralizeInvestAmount>> {
        return RetrofitFactoryGet().create(GeneralizeApi::class.java).getInvestStatistics(map["uid"]!!)
    }

    fun getGenInvestList(map: MutableMap<String, String>): Observable<BaseResp<MutableList<GeneralizeInvest>>> {
        return RetrofitFactoryGet().create(GeneralizeApi::class.java).getGenInvestList(map["uid"]!!, map["status"]!!)
    }

    fun getInvestDetailsList(map: MutableMap<String, String>): Observable<BaseResp<MutableList<InvestList>>> {
        return RetrofitFactoryGet().create(GeneralizeApi::class.java).getInvestDetailsList(map["uid"]!!)
    }

    fun getInvestDetails(map: MutableMap<String, String>): Observable<BaseResp<InvestDetails>> {
        return RetrofitFactoryGet().create(GeneralizeApi::class.java).getInvestDetails(map["uid"]!!, map["investmentId"]!!)
    }


}