package com.yizhipin.shop.data.api

import com.yizhipin.base.data.protocol.BaseResp
import com.yizhipin.base.data.response.CashPledge
import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.usercenter.data.api.Api
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by ${XiLei} on 2018/7/27.
 */
interface PayApi {

    @POST(Api.RECHERGE)
    fun recharge(): Observable<BaseResp<String>>

    @POST(Api.RECHERGE_CASH_PLEDGE)
    fun rechargeCashPledge(): Observable<BaseResp<String>>

    @GET(Api.CASH_PLEDGE)
    fun getCashPledge(@Query("uid") uid: String): Observable<BaseResp<CashPledge>>

    @GET("${Api.EDIT_USER_INFO}${"/{id}"}")
    fun getUserInfo(@Path("id") id: String): Observable<BaseResp<UserInfo>>

    @POST(Api.APPLY_WITHDRAW)
    fun applyWithdraw(): Observable<BaseResp<String>>
}