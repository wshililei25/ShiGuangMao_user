package com.yizhipin.message.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMGroup
import com.hyphenate.easeui.EaseConstant
import com.yizhipin.base.ui.activity.BaseActivity
import com.yizhipin.message.R
import com.yizhipin.message.ui.adapter.GroupAdapter
import kotlinx.android.synthetic.main.activity_chat_group.*

/**
 * Created by ${XiLei} on 2019/2/23.
 * 群组界面
 */
class ChatGroupActivity : BaseActivity() {

    private lateinit var mGrouplist: List<EMGroup>
    private lateinit var mGroupAdapter: GroupAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_group)
        initView()
    }

    private fun initView() {
        mGrouplist = EMClient.getInstance().groupManager().allGroups
        mGroupAdapter = GroupAdapter(this, 1, mGrouplist)
        mGroupLv.adapter = mGroupAdapter

        mGroupLv.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                var intent = Intent(this@ChatGroupActivity, ChatActivity::class.java)
                intent.putExtra(EaseConstant.EXTRA_USER_ID, mGroupAdapter.getItem(position).groupId) //用户id
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP) //聊天类型
                startActivity(intent)
            }
        })
    }
}