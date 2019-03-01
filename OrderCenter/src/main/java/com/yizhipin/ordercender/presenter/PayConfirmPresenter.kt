package com.yizhipin.ordercender.presenter

import com.yizhipin.base.data.response.BuyResult
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.ordercender.serivice.impl.OrderServiceImpl
import javax.inject.Inject

/*
    订单确认页 Presenter
 */
class PayConfirmPresenter @Inject constructor() : BasePresenter<PayConfirmView>() {

    @Inject
    lateinit var mOrderServiceImpl: OrderServiceImpl

    fun mealFrontMoney(map: MutableMap<String, String>) {
        mView.showLoading()
        mOrderServiceImpl.mealFrontMoney(map).execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.onMealFrontMoneySuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun dressBuy(map: MutableMap<String, String>) {
        mView.showLoading()
        mOrderServiceImpl.dressBuy(map).execute(object : BaseSubscriber<BuyResult>(mView) {
            override fun onNext(t: BuyResult) {
                mView.onDressBuySuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun dressHire(map: MutableMap<String, String>) {
        mView.showLoading()
        mOrderServiceImpl.dressHire(map).execute(object : BaseSubscriber<BuyResult>(mView) {
            override fun onNext(t: BuyResult) {
                mView.onDressBuySuccess(t)
            }
        }, mLifecycleProvider)
    }

    /**
     * 提交订单
     */
    fun submitOrder(map: MutableMap<String, String>) {
        mView.showLoading()
        mOrderServiceImpl.submitOrder(map).execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.onSubmitOrderSuccess(t)
            }
        }, mLifecycleProvider)

    }

    /**
     * 提交订单(一品小住)
     */
    fun submitOrderReside(map: MutableMap<String, String>) {
        mView.showLoading()
        mOrderServiceImpl.submitOrderReside(map).execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.onSubmitOrderSuccess(t)
            }
        }, mLifecycleProvider)

    }


}
