package com.yizhipin.goods.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.yizhipin.base.ui.activity.BaseActivity
import com.yizhipin.goods.R
import com.yizhipin.goods.ui.adapter.IntegralVpAdapter
import com.yizhipin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_time_integral.*

/**
 * Created by ${XiLei} on 2018/9/25.
 * 积分
 */
@Route(path = RouterPath.GoodsCenter.PATH_INTEGRAL)
class IntegralActivity() : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_integral)

        initView()
    }

    private fun initView() {
        mOrderTab.tabMode = TabLayout.MODE_FIXED
        mOrderVp.adapter = IntegralVpAdapter(supportFragmentManager, this)
        mOrderTab.setupWithViewPager(mOrderVp)
    }

}