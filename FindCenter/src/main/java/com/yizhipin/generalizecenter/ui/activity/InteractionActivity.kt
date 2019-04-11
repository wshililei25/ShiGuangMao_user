package com.yizhipin.generalizecenter.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseActivity
import com.yizhipin.generalizecenter.R
import com.yizhipin.generalizecenter.ui.adapter.InteractionVpAdapter
import kotlinx.android.synthetic.main.activity_tablayout.*

/**
 * Created by ${XiLei} on 2018/9/25.
 * 互动吧
 */
class InteractionActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tablayout)

        initView()
    }

    private fun initView() {
        mHeaderBar.getTiTleTv().text = getString(R.string.interaction)
        mTab.tabMode = TabLayout.MODE_FIXED
        mVp.adapter = InteractionVpAdapter(supportFragmentManager, this)
        mTab.setupWithViewPager(mVp)

        mCustomBtn.onClick {
            custom()
        }
    }
}