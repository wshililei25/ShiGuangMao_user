package com.yizhipin.base.ui.activity

import android.os.Bundle
import com.yizhipin.base.R
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.ext.onClick
import kotlinx.android.synthetic.main.activity_pay_success.*

/**
 *  @author: XiLei  @date: 2019/2/26
 */
class PaySuccessActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_success)

        mContentTv.text = intent.getStringExtra(BaseConstant.KEY_PAY_CONTENT)
        mConfirmBtn.onClick { finish() }
    }
}