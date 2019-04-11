package com.yizhipin.ordercender.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseActivity
import com.yizhipin.ordercender.R
import com.yizhipin.ordercender.common.OrderConstant
import com.yizhipin.ordercender.ui.adapter.OrderVpAdapter
import kotlinx.android.synthetic.main.activity_tablayout.*

/**
 * Created by ${XiLei} on 2018/9/25.
 */
class OrderActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tablayout)

        initView()
    }

    private fun initView() {
        mHeaderBar.getTiTleTv().text= getString(R.string.my_order)
        mTab.tabMode = TabLayout.MODE_FIXED
        mVp.adapter = OrderVpAdapter(supportFragmentManager, this)
        mTab.setupWithViewPager(mVp)

        mVp.currentItem = intent.getIntExtra(OrderConstant.KEY_ORDER_STATUS, 0)

        mCustomBtn.onClick {
            custom()
        }
    }
}