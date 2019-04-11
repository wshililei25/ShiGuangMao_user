package com.yizhipin.ui.activity

import android.os.Bundle
import com.hyphenate.helpdesk.easeui.util.IntentBuilder
import com.yizhipin.R
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_custom_service.*

/**
 * Created by ${XiLei} on 2018/8/21.
 * 客服电话
 */
class CustomServiceActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_service)
        initView()
    }

    private fun initView() {
        mCustomBtn.onClick {
            custom()
        }
    }

}