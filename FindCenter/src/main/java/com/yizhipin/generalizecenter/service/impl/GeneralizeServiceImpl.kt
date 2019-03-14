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

    override fun getInteractionList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Interaction>>> {
        return mRepository.getInteractionList(map).convertPaging()
    }

    override fun getInteractionDetails(map: MutableMap<String, String>): Observable<InteractionDetails> {
        return mRepository.getInteractionDetails(map).convert()
    }

    override fun comment(map: MutableMap<String, String>): Observable<Comment> {
        return mRepository.comment(map).convert()
    }

    override fun giveLike(map: MutableMap<String, String>): Observable<Boolean> {
        return mRepository.giveLike(map).convertBoolean()
    }


    override fun getEndTime(): Observable<String> {
        return mRepository.getEndTime().convert()
    }

    override fun getInvestStatistics(map: MutableMap<String, String>): Observable<GeneralizeInvestAmount> {

        return mRepository.getInvestStatistics(map).convert()
    }

    override fun getInvestDetailsList(map: MutableMap<String, String>): Observable<MutableList<InvestList>> {
        return mRepository.getInvestDetailsList(map).convert()
    }
}