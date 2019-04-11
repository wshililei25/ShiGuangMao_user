package com.yizhipin.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import com.hyphenate.helpdesk.easeui.util.IntentBuilder
import com.yizhipin.R
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseActivity
import com.yizhipin.ui.adapter.FollowVpAdapter
import kotlinx.android.synthetic.main.activity_tablayout.*

/**
 * Created by ${XiLei} on 2018/9/25.
 * 我的关注
 */
class FollowActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tablayout)

        initView()
    }

    private fun initView() {
        mHeaderBar.getTiTleTv().text = getString(R.string.my_follow)
        mTab.tabMode = TabLayout.MODE_FIXED
        mVp.adapter = FollowVpAdapter(supportFragmentManager, this)
        mTab.setupWithViewPager(mVp)

        mCustomBtn.onClick {
            custom()
        }
    }
}