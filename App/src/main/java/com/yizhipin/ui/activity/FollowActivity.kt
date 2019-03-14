package com.yizhipin.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import com.yizhipin.R
import com.yizhipin.base.ui.activity.BaseActivity
import com.yizhipin.ui.adapter.FollowVpAdapter
import kotlinx.android.synthetic.main.activity_follow.*

/**
 * Created by ${XiLei} on 2018/9/25.
 * 我的关注
 */
class FollowActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_follow)

        initView()
    }

    private fun initView() {
        mOrderTab.tabMode = TabLayout.MODE_FIXED
        mOrderVp.adapter = FollowVpAdapter(supportFragmentManager, this)
        mOrderTab.setupWithViewPager(mOrderVp)
    }
}