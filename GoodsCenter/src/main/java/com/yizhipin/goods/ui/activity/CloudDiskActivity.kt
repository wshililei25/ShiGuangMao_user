package com.yizhipin.goods.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.yizhipin.base.ui.activity.BaseActivity
import com.yizhipin.goods.R
import com.yizhipin.goods.ui.adapter.CloudDiskVpAdapter
import com.yizhipin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_cloud_disk.*

/**
 * Created by ${XiLei} on 2018/9/25.
 * 时光云盘
 */
@Route(path = RouterPath.GoodsCenter.PATH_CLOUD_DISK)
class CloudDiskActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cloud_disk)

        initView()
    }

    private fun initView() {

        mOrderTab.tabMode = TabLayout.MODE_FIXED
        mOrderVp.adapter = CloudDiskVpAdapter(supportFragmentManager, this)
        mOrderTab.setupWithViewPager(mOrderVp)
    }

    override fun onClick(v: View) {
        when (v.id) {

        }
    }

}