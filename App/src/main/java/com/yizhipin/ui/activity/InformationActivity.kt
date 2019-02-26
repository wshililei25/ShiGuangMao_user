package com.yizhipin.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import com.yizhipin.R
import com.yizhipin.base.ui.activity.BaseActivity
import com.yizhipin.ordercender.common.OrderConstant
import com.yizhipin.ui.adapter.InformationVpAdapter
import kotlinx.android.synthetic.main.activity_information.*

/**
 * Created by ${XiLei} on 2018/9/25.
 * 资讯
 */
class InformationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        initView()
    }

    private fun initView() {
        mOrderTab.tabMode = TabLayout.MODE_FIXED
        mOrderVp.adapter = InformationVpAdapter(supportFragmentManager, this)
        mOrderTab.setupWithViewPager(mOrderVp)

        mOrderVp.currentItem = intent.getIntExtra(OrderConstant.KEY_ORDER_STATUS, 0)
    }
}