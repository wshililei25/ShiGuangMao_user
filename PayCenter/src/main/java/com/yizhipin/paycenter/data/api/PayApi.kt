package com.yizhipin.shop.data.api

import com.yizhipin.base.data.protocol.BaseResp
import com.yizhipin.base.data.response.ShopDetails
import com.yizhipin.usercenter.data.api.Api
import io.reactivex.Observable
import retrofit2.http.POST


/**
 * Created by ${XiLei} on 2018/7/27.
 */
interface PayApi {

    @POST(Api.RECHERGE)
    fun recharge(): Observable<BaseResp<String>>
}