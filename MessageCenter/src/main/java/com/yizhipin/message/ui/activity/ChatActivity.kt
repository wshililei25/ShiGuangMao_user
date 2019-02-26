package com.yizhipin.message.ui.activity

import android.os.Bundle
import com.hyphenate.easeui.ui.EaseChatFragment
import com.yizhipin.base.ui.activity.BaseActivity
import com.yizhipin.message.R

/**
 * Created by ${XiLei} on 2019/2/23.
 * 聊天界面
 */
class ChatActivity : BaseActivity() {

    private lateinit var mEaseChatFragment: EaseChatFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initView()
    }

    private fun initView() {
        mEaseChatFragment = EaseChatFragment()
        mEaseChatFragment.arguments = intent.extras //将参数传递给聊天界面
        supportFragmentManager.beginTransaction().add(R.id.container, mEaseChatFragment).commit()
    }
}