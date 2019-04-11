package com.yizhipin.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import com.hyphenate.helpdesk.easeui.util.IntentBuilder
import com.yizhipin.R
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseActivity
import com.yizhipin.ordercender.common.OrderConstant
import com.yizhipin.ui.adapter.InformationVpAdapter
import kotlinx.android.synthetic.main.activity_tablayout.*

/**
 * Created by ${XiLei} on 2018/9/25.
 * 资讯
 */
class InformationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tablayout)

        initView()
    }

    private fun initView() {
        mHeaderBar.getTiTleTv().text = getString(R.string.information)
        mTab.tabMode = TabLayout.MODE_FIXED
        mVp.adapter = InformationVpAdapter(supportFragmentManager, this)
        mTab.setupWithViewPager(mVp)

        mVp.currentItem = intent.getIntExtra(OrderConstant.KEY_ORDER_STATUS, 0)

        mCustomBtn.onClick {
            custom()
        }
    }
}