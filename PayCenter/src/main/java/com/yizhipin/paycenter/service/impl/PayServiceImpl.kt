package com.yizhipin.shop.service.impl

import com.yizhipin.base.data.response.CashPledge
import com.yizhipin.base.ext.convert
import com.yizhipin.shop.data.repository.PayRepository
import com.yizhipin.shop.service.PayService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class PayServiceImpl @Inject constructor() : PayService {

    @Inject
    lateinit var mRepository: PayRepository

    override fun recharge(map: MutableMap<String, String>): Observable<String> {
        return mRepository.recharge(map)
                .convert()
    }

    override fun rechargeCashPledge(map: MutableMap<String, String>): Observable<String> {
        return mRepository.rechargeCashPledge(map)
                .convert()
    }

    override fun getCashPledge(map: MutableMap<String, String>): Observable<CashPledge> {
        return mRepository.getCashPledge(map)
                .convert()
    }


}