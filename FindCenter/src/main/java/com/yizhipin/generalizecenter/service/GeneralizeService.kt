package com.yizhipin.generalizecenter.service

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.*
import io.reactivex.Observable


/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface GeneralizeService {

    fun getInvestStatistics(map: MutableMap<String, String>): Observable<GeneralizeInvestAmount>
    fun getInvestDetailsList(map: MutableMap<String, String>): Observable<MutableList<InvestList>>
    fun getEndTime(): Observable<String>
    fun getInteractionList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Interaction>>>
    fun giveLike(map: MutableMap<String, String>): Observable<Boolean>
    fun getInteractionDetails(map: MutableMap<String, String>): Observable<InteractionDetails>
    fun comment(map: MutableMap<String, String>): Observable<Comment>
}
