package com.yizhipin.generalizecenter.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import com.yizhipin.base.ui.activity.BaseActivity
import com.yizhipin.generalizecenter.R
import com.yizhipin.generalizecenter.ui.adapter.InteractionVpAdapter
import kotlinx.android.synthetic.main.activity_interaction.*

/**
 * Created by ${XiLei} on 2018/9/25.
 * 互动吧
 */
class InteractionActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interaction)

        initView()
    }

    private fun initView() {
        mTab.tabMode = TabLayout.MODE_SCROLLABLE
        mVp.adapter = InteractionVpAdapter(supportFragmentManager, this)
        mTab.setupWithViewPager(mVp)

//        mVp.currentItem = intent.getIntExtra(OrderConstant.KEY_ORDER_STATUS, 0)
    }
}