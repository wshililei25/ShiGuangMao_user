package com.yizhipin.generalizecenter.service.impl

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.*
import com.yizhipin.base.ext.convert
import com.yizhipin.base.ext.convertBoolean
import com.yizhipin.base.ext.convertPaging
import com.yizhipin.generalizecenter.service.GeneralizeService
import com.yizhipin.goods.data.repository.GeneralizeRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class GeneralizeServiceImpl @Inject constructor() : GeneralizeService {

    @Inject
    lateinit var mRepository: GeneralizeRepository

    override fun getGenBiddingList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<GeneralizeCollect>>> {

        return mRepository.getGenBiddingList(map).convertPaging()
    }

    override fun getInteractionList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Interaction>>> {
        return mRepository.getInteractionList(map).convertPaging()
    }

    override fun getInteractionDetails(map: MutableMap<String, String>): Observable<InteractionDetails> {
        return mRepository.getInteractionDetails(map).convert()
    }

    override fun comment(map: MutableMap<String, String>): Observable<Comment> {
        return mRepository.comment(map).convert()
    }

    override fun getGenBiddingDetails(map: MutableMap<String, String>): Observable<GeneralizeCollect> {

        return mRepository.getGenBiddingDetails(map).convert()
    }

    override fun giveLike(map: MutableMap<String, String>): Observable<Boolean> {

        return mRepository.giveLike(map).convertBoolean()
    }

    override fun getGenGroupDetails(map: MutableMap<String, String>): Observable<GeneralizeGroupDetails> {

        return mRepository.getGenGroupDetails(map).convert()
    }

    override fun payPersonage(map: MutableMap<String, String>): Observable<String> {

        return mRepository.payPersonage(map).convert()
    }

    override fun getEndTime(): Observable<String> {

        return mRepository.getEndTime().convert()
    }

    override fun getInvestStatistics(map: MutableMap<String, String>): Observable<GeneralizeInvestAmount> {

        return mRepository.getInvestStatistics(map).convert()
    }

    override fun getGenInvestList(map: MutableMap<String, String>): Observable<MutableList<GeneralizeInvest>> {

        return mRepository.getGenInvestList(map).convert()
    }

    override fun getInvestDetailsList(map: MutableMap<String, String>): Observable<MutableList<InvestList>> {

        return mRepository.getInvestDetailsList(map).convert()
    }

    override fun getInvestDetails(map: MutableMap<String, String>): Observable<InvestDetails> {

        return mRepository.getInvestDetails(map).convert()
    }

}