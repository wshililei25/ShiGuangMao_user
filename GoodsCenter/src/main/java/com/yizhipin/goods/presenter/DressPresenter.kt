package com.yizhipin.goods.presenter

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Dress
import com.yizhipin.base.data.response.DressCategory
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.goods.presenter.view.DressView
import com.yizhipin.goods.service.impl.GoodsServiceImpl
import javax.inject.Inject

class DressPresenter @Inject constructor() : BasePresenter<DressView>() {

    @Inject
    lateinit var mGoodsServiceImpl: GoodsServiceImpl

    fun getDressCategory(map: MutableMap<String, String>) {
        mView.showLoading()
        mGoodsServiceImpl.getDressCategory(map).execute(object : BaseSubscriber<MutableList<DressCategory>>(mView) {
            override fun onNext(t: MutableList<DressCategory>) {
                mView.onGetDressCategorySuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun getDressList(map: MutableMap<String, String>) {
        mView.showLoading()
        mGoodsServiceImpl.getDressList(map).execute(object : BaseSubscriber<BasePagingResp<MutableList<Dress>>>(mView) {
            override fun onNext(t: BasePagingResp<MutableList<Dress>>) {
                mView.onGetDressListSuccess(t)
            }
        }, mLifecycleProvider)
    }
}
