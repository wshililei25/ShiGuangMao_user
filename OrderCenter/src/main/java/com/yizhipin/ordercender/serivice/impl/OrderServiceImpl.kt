package com.yizhipin.ordercender.serivice.impl

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.BuyResult
import com.yizhipin.base.data.response.DressDetails
import com.yizhipin.base.data.response.OrderDetails
import com.yizhipin.base.ext.convert
import com.yizhipin.base.ext.convertBoolean
import com.yizhipin.base.ext.convertPaging
import com.yizhipin.ordercender.data.repository.OrderRepository
import com.yizhipin.ordercender.data.response.Order
import com.yizhipin.ordercender.data.response.ShipAddress
import com.yizhipin.ordercender.serivice.OrderService
import io.reactivex.Observable
import javax.inject.Inject

/*
    订单业务实现类
 */
class OrderServiceImpl @Inject constructor() : OrderService {

    @Inject
    lateinit var mRepository: OrderRepository

    override fun getGoodsDetail(map: MutableMap<String, String>): Observable<DressDetails> {
        return mRepository.getGoodsDetail(map).convert()
    }

    override fun getDefaultAddress(map: MutableMap<String, String>): Observable<ShipAddress> {
        return mRepository.getDefaultAddress(map).convert()
    }

    override fun getOrderById(map: MutableMap<String, String>): Observable<Order> {
        return mRepository.getOrderById(map).convert()
    }

    override fun submitOrder(map: MutableMap<String, String>): Observable<String> {
        return mRepository.submitOrder(map).convert()
    }

    override fun mealFrontMoney(map: MutableMap<String, String>): Observable<String> {
        return mRepository.mealFrontMoney(map).convert()
    }
    override fun mealBalancePayment(map: MutableMap<String, String>): Observable<String> {
        return mRepository.mealBalancePayment(map).convert()
    }

    override fun dressBuy(map: MutableMap<String, String>): Observable<BuyResult> {
        return mRepository.dressBuy(map).convert()
    }

    override fun dressHire(map: MutableMap<String, String>): Observable<BuyResult> {
        return mRepository.dressHire(map).convert()
    }

    override fun submitOrderReside(map: MutableMap<String, String>): Observable<String> {
        return mRepository.submitOrderReside(map).convert()
    }

    override fun getOrderList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Order>>> {
        return mRepository.getOrderList(map).convertPaging()

    }

    /*
        取消订单
     */
    override fun cancelOrder(map: MutableMap<String, String>): Observable<Boolean> {
        return mRepository.cancelOrder(map).convertBoolean()
    }

    /*
        订单确认收货
     */
    override fun confirmOrder(orderId: Int): Observable<Boolean> {
        return mRepository.confirmOrder(orderId).convertBoolean()
    }

    override fun getOrderDetails(map: MutableMap<String, String>): Observable<OrderDetails> {
        return mRepository.getOrderDetails(map).convert()
    }
}
