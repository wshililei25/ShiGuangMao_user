package com.yizhipin.shop.data.repository

import com.yizhipin.base.data.net.RetrofitFactoryGet
import com.yizhipin.base.data.net.RetrofitFactoryPost
import com.yizhipin.base.data.protocol.BaseResp
import com.yizhipin.base.data.response.CashPledge
import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.shop.data.api.PayApi
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/27.
 */
class PayRepository @Inject constructor() {

    fun recharge(map: MutableMap<String, String>): Observable<BaseResp<String>> {
        return RetrofitFactoryPost(map).create(PayApi::class.java)
                .recharge()
    }
    fun rechargeCashPledge(map: MutableMap<String, String>): Observable<BaseResp<String>> {
        return RetrofitFactoryPost(map).create(PayApi::class.java)
                .rechargeCashPledge()
    }
    fun getCashPledge(map: MutableMap<String, String>): Observable<BaseResp<CashPledge>> {
        return RetrofitFactoryGet().create(PayApi::class.java)
                .getCashPledge(map["uid"]!!)
    }
    fun getUserInfo(map: MutableMap<String, String>): Observable<BaseResp<UserInfo>> {
        return RetrofitFactoryGet().create(PayApi::class.java)
                .getUserInfo(map["id"]!!)
    }
    fun applyWithdraw(map: MutableMap<String, String>): Observable<BaseResp<String>> {
        return RetrofitFactoryPost(map).create(PayApi::class.java)
                .applyWithdraw()
    }
}