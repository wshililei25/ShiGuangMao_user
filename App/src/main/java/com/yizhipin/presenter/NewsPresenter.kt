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
import com.yizhipin.presenter.view.NewsView
import com.yizhipin.usercenter.service.impl.MainServiceImpl
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class NewsPresenter @Inject constructor() : BasePresenter<NewsView>() {

    @Inject
    lateinit var mServiceImpl: MainServiceImpl

    fun getNews(map: MutableMap<String, String>) {
        mServiceImpl.getNews(map)
                .execute(object : BaseSubscriber<BasePagingResp<MutableList<News>>>(mView) {
                    override fun onNext(t: BasePagingResp<MutableList<News>>) {
                        mView.onGetNewsSuccess(t)
                    }
                }, mLifecycleProvider)

    }

}

