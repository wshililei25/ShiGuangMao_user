package com.yizhipin.shop.service

import com.yizhipin.base.data.response.CashPledge
import com.yizhipin.base.data.response.UserInfo
import io.reactivex.Observable


/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface PayService {

    fun recharge(map: MutableMap<String, String>): Observable<String>
    fun rechargeCashPledge(map: MutableMap<String, String>): Observable<String>
    fun getCashPledge(map: MutableMap<String, String>): Observable<CashPledge>
    fun getUserInfo(map: MutableMap<String, String>): Observable<UserInfo>
    fun applyWithdraw(map: MutableMap<String, String>): Observable<String>
}
