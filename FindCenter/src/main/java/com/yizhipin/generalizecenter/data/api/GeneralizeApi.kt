package com.yizhipin.goods.data.api

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.protocol.BaseResp
import com.yizhipin.base.data.response.*
import com.yizhipin.usercenter.data.api.Api
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GeneralizeApi {

    @GET(Api.INTARACTION_LIST)
    fun getInteractionList(@Query("currentPage") currentPage: String, @Query("type") type: String, @Query("loginUid") loginUid: String): Observable<BasePagingResp<MutableList<Interaction>>>

    @GET("${Api.INTARACTION_DETAILS}${"/{id}"}")
    fun getInteractionDetails(@Path("id") id: String): Observable<BaseResp<InteractionDetails>>

    @POST(Api.COMMENT)
    fun comment(): Observable<BaseResp<Comment>>

    @POST(Api.GIVE_LIKE)
    fun giveLike(): Observable<BaseResp<Boolean>>


    /**
     * 获取倒计时
     */
    @POST(Api.END_TIME)
    fun getEndTime(): Observable<BaseResp<String>>

    /**
     * 投资收益金额
     */
    @GET(Api.GENERALIZE_INVEST_AMOUNT)
    fun getInvestStatistics(@Query("uid") uid: String): Observable<BaseResp<GeneralizeInvestAmount>>


    /**
     * 投资明细列表
     */
    @GET(Api.INVEST_DETAILS_LIST)
    fun getInvestDetailsList(@Query("uid") uid: String): Observable<BaseResp<MutableList<InvestList>>>

}
