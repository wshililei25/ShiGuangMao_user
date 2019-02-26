package com.yizhipin.goods.presenter

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.CloudDisk
import com.yizhipin.base.data.response.CloudDiskImage
import com.yizhipin.base.ext.execute
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.rx.BaseSubscriber
import com.yizhipin.goods.presenter.view.CloudDiskView
import com.yizhipin.goods.service.impl.GoodsServiceImpl
import javax.inject.Inject

class CloudDiskPresenter @Inject constructor() : BasePresenter<CloudDiskView>() {

    @Inject
    lateinit var mGoodsServiceImpl: GoodsServiceImpl

    fun getCloudDiskList(map: MutableMap<String, String>) {
        mView.showLoading()
        mGoodsServiceImpl.getCloudDiskList(map).execute(object : BaseSubscriber<BasePagingResp<MutableList<CloudDisk>>>(mView) {
            override fun onNext(t: BasePagingResp<MutableList<CloudDisk>>) {
                mView.onGetCloudDiskSuccess(t)
            }
        }, mLifecycleProvider)
    }

    fun getCloudDiskImageList(map: MutableMap<String, String>) {
        mView.showLoading()
        mGoodsServiceImpl.getCloudDiskImageList(map).execute(object : BaseSubscriber<BasePagingResp<MutableList<CloudDiskImage>>>(mView) {
            override fun onNext(t: BasePagingResp<MutableList<CloudDiskImage>>) {
                mView.onGetCloudDiskImageSuccess(t)
            }
        }, mLifecycleProvider)
    }
}
