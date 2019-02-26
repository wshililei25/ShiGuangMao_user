package com.yizhipin.goods.presenter.view

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.CloudDisk
import com.yizhipin.base.data.response.CloudDiskImage
import com.yizhipin.base.presenter.view.BaseView

interface CloudDiskView : BaseView {

    fun onGetCloudDiskSuccess(result: BasePagingResp<MutableList<CloudDisk>>)
    fun onGetCloudDiskImageSuccess(result: BasePagingResp<MutableList<CloudDiskImage>>)
}
