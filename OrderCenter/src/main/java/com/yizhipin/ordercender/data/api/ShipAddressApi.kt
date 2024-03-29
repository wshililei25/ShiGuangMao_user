package com.yizhipin.ordercender.data.api

import com.yizhipin.base.data.protocol.BaseResp
import com.yizhipin.base.data.response.ShipAddress
import com.yizhipin.ordercender.common.OrderConstant
import com.yizhipin.usercenter.data.api.Api
import io.reactivex.Observable
import retrofit2.http.*


/*
    地址管理 接口
 */
interface ShipAddressApi {

    @POST(Api.ADD_ADDRESS) //新增收货地址
    fun addShipAddress(): Observable<BaseResp<ShipAddress>>

    @DELETE("${Api.ADD_ADDRESS}${"/{id}"}") //删除收货地址
    fun deleteShipAddress(@Path(OrderConstant.KEY_ORDER_ID) id: String): Observable<BaseResp<String>>

    @PUT("${Api.ADD_ADDRESS}${"/{id}"}") //修改收货地址
    fun editShipAddress(@Path(OrderConstant.KEY_ORDER_ID) id: String): Observable<BaseResp<ShipAddress>>

    @POST(Api.SET_DEFAULT_ADDRESS) //设置默认收货地址
    fun setDefaultShipAddress(@Query("id") id: String): Observable<BaseResp<Boolean>>

    @GET(Api.ADDRESS_LIST) //收货地址列表
    fun getShipAddressList(@Query("uid") uid: String): Observable<BaseResp<MutableList<ShipAddress>?>>
}
