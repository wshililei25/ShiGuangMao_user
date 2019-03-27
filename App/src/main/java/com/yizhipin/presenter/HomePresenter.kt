package com.yizhipin.presenter

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.News
import com.yizhipin.base.data.response.OssAddress
import com.yizhipin.base.data.response.ScenicSpot
import com.yizhipin.base.data.response.Store
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.presenter.view.HomeView
import com.yizhipin.base.data.response.Banner
import com.yizhipin.usercenter.service.impl.MainServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class HomePresenter @Inject constructor() : BasePresenter<HomeView>() {

    @Inject
    lateinit var mServiceImpl: MainServiceImpl

    fun getBanner() {
        mServiceImpl.getBanner().execute(object : BaseSubscriber<MutableList<Banner>>(mView) {
            override fun onNext(t: MutableList<Banner>) {
                mView.onGetBannerSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun getGoodsList(map: MutableMap<String, String>) {
        mServiceImpl.getGoodsList(map)
                .execute(object : BaseSubscriber<MutableList<ScenicSpot>>(mView) {
                    override fun onNext(t: MutableList<ScenicSpot>) {
                        mView.onGetGoodsListSuccess(t)
                    }
                }, mLifecycleProvider)

    }

    fun getUnreadNewCount(map: MutableMap<String, String>) {
        mServiceImpl.getUnreadNewCount(map).execute(object : BaseSubscriber<Int>(mView) {
            override fun onNext(t: Int) {
                mView.getUnReadNewCount(t)
            }
        }, mLifecycleProvider)
    }

    fun getDefaultStore(map: MutableMap<String, String>) {
//        mView.showLoading()
        mServiceImpl.getDefaultStore(map)
                .execute(object : BaseSubscriber<Store>(mView) {
                    override fun onNext(t: Store) {
                        mView.onGetDefaultStoreSuccess(t)
                    }
                }, mLifecycleProvider)

    }

    fun getInformation(map: MutableMap<String, String>) {
        mServiceImpl.getInformation(map)
                .execute(object : BaseSubscriber<BasePagingResp<MutableList<News>>>(mView) {
                    override fun onNext(t: BasePagingResp<MutableList<News>>) {
                        mView.onGetNewsSuccess(t)
                    }
                }, mLifecycleProvider)

    }

    fun getOssAddress() {
//        mView.showLoading()
        mServiceImpl.getOssAddress()
                .execute(object : BaseSubscriber<OssAddress>(mView) {
                    override fun onNext(t: OssAddress) {
                        mView.onGetOssAddressSuccess(t)
                    }
                }, mLifecycleProvider)

    }
}

