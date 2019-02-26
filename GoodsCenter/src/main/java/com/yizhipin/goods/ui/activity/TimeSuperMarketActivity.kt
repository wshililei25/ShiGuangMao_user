package com.yizhipin.goods.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.DressCategory
import com.yizhipin.base.data.response.TimeSuperMarket
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.goods.R
import com.yizhipin.goods.injection.component.DaggerGoodsComponent
import com.yizhipin.goods.injection.module.GoodsModule
import com.yizhipin.goods.presenter.TimeSuperMarketPresenter
import com.yizhipin.goods.presenter.view.TimeSuperMarketView
import com.yizhipin.goods.ui.adapter.TimeSuperMarketVpAdapter
import com.yizhipin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_time_supermarket.*

/**
 * Created by ${XiLei} on 2018/9/25.
 * 时光超市
 */
@Route(path = RouterPath.GoodsCenter.PATH_TIME_SUPERMARKET)
class TimeSuperMarketActivity() : BaseMvpActivity<TimeSuperMarketPresenter>(), TimeSuperMarketView, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_supermarket)

        initView()
        getTimeSuperMarketCategory()
    }

    override fun injectComponent() {
        DaggerGoodsComponent.builder().activityComponent(mActivityComponent).goodsModule(GoodsModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initView() {

    }

    /**
     * 获取时光超市类别
     */
    private fun getTimeSuperMarketCategory() {
        mBasePresenter.getTimeSuperMarketCategory()
    }

    /**
     * 获取服装类别成功
     */
    override fun onGetTimeSuperMarketCategorySuccess(result: MutableList<DressCategory>) {
        mOrderTab.tabMode = TabLayout.MODE_SCROLLABLE
        mOrderVp.adapter = TimeSuperMarketVpAdapter(supportFragmentManager, this, result)
        mOrderTab.setupWithViewPager(mOrderVp)
    }

    override fun onClick(v: View) {
        when (v.id) {

        }
    }

    override fun onGetTimeSuperMarketListSuccess(result: BasePagingResp<MutableList<TimeSuperMarket>>) {
    }
    override fun onGetTimeSuperMarketDetailSuccess(result: TimeSuperMarket) {
    }
    override fun onFollowSuccess(result: Boolean) {
    }
}