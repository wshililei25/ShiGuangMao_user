package com.yizhipin.shop.service

import com.yizhipin.base.data.response.CashPledge
import io.reactivex.Observable


/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface PayService {

    fun recharge(map: MutableMap<String, String>): Observable<String>
    fun rechargeCashPledge(map: MutableMap<String, String>): Observable<String>
    fun getCashPledge(map: MutableMap<String, String>): Observable<CashPledge>
}
