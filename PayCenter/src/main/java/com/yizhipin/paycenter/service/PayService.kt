package com.yizhipin.shop.service

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.*
import io.reactivex.Observable


/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface PayService {

    fun recharge(map: MutableMap<String, String>): Observable<String>
}
