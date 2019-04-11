package com.yizhipin.usercenter.ui.activity

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseActivity
import com.yizhipin.usercenter.R
import com.yizhipin.usercenter.common.UserConstant
import kotlinx.android.synthetic.main.activity_relevance_user.*
import org.jetbrains.anko.startActivity


/**
 * Created by ${XiLei} on 2018/8/5.
 */
class ShareActivity : BaseActivity(), View.OnClickListener {

    @Autowired(name = UserConstant.KEY_INCITATION_CODE) //注解接收上个页面的传参
    @JvmField
    var mCode: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        initView()
    }

    private fun initView() {
        mBtn.onClick(this)
        mCustomBtn.onClick(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mCustomBtn -> custom()
            R.id.mBtn -> startActivity<InvitationActivity>(UserConstant.KEY_INCITATION_CODE to mCode)
        }
    }

}