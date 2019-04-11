package com.yizhipin.goods.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseActivity
import com.yizhipin.goods.R
import com.yizhipin.goods.ui.adapter.IntegralVpAdapter
import com.yizhipin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_tablayout.*

/**
 * Created by ${XiLei} on 2018/9/25.
 * 积分
 */
@Route(path = RouterPath.GoodsCenter.PATH_INTEGRAL)
class IntegralActivity() : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tablayout)

        initView()
    }

    private fun initView() {
        mHeaderBar.getTiTleTv().text = getString(R.string.integral_mall)
        mTab.tabMode = TabLayout.MODE_FIXED
        mVp.adapter = IntegralVpAdapter(supportFragmentManager, this)
        mTab.setupWithViewPager(mVp)

        mCustomBtn.onClick {
            custom()
        }
    }

}