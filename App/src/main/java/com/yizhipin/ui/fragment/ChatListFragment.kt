package com.yizhipin.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.hyphenate.EMConnectionListener
import com.hyphenate.EMError
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMConversation
import com.hyphenate.easeui.EaseConstant
import com.yizhipin.R
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.fragment.BaseFragment
import com.yizhipin.message.ui.activity.ChatActivity
import com.yizhipin.message.ui.activity.ChatGroupActivity
import kotlinx.android.synthetic.main.fragment_chat_list.*
import org.jetbrains.anko.support.v4.startActivity
import java.util.*

/**
 * Created by ${XiLei} on 2018/8/23.
 * 聊天列表界面
 */
class ChatListFragment : BaseFragment(), View.OnClickListener {

    protected var conversationList: MutableList<EMConversation> = ArrayList()

    private val MSG_REFRESH = 2
    protected var hidden: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_chat_list, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onResume() {
        super.onResume()
        if (!hidden) {
            refresh()
        }
    }

    private fun initView() {

        mGroupTv.onClick(this)
        conversationList.addAll(loadConversationList())
        mChatLv.init(conversationList)
        EMClient.getInstance().addConnectionListener(connectionListener)

        mChatLv.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val conversation = mChatLv.getItem(position)
                var intent = Intent(activity, ChatActivity::class.java)
                intent.putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()) //用户id
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE,  EaseConstant.CHATTYPE_GROUP) //聊天类型
                startActivity(intent)
            }
        })
    }

    /**
     * load conversation list
     */
    protected fun loadConversationList(): List<EMConversation> {
        // get all conversations
        val conversations = EMClient.getInstance().chatManager().allConversations
        val sortList = ArrayList<Pair<Long, EMConversation>>()
        /**
         * lastMsgTime will change if there is new message during sorting
         * so use synchronized to make sure timestamp of last message won't change.
         */
        synchronized(conversations) {
            for (conversation in conversations.values) {
                if (conversation.allMessages.size != 0) {
                    sortList.add(Pair(conversation.lastMessage.msgTime, conversation))
                }
            }
        }
        try {
            // Internal is TimSort algorithm, has bug
            sortConversationByLastChatTime(sortList)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val list = ArrayList<EMConversation>()
        for (sortItem in sortList) {
            list.add(sortItem.second)
        }
        return list
    }

    /**
     * sort conversations according time stamp of last message
     * @param conversationList
     */
    private fun sortConversationByLastChatTime(conversationList: List<Pair<Long, EMConversation>>) {
        Collections.sort(conversationList) { con1, con2 ->
            if (con1.first == con2.first) {
                0
            } else if (con2.first.toLong() > con1.first.toLong()) {
                1
            } else {
                -1
            }
        }
    }

    private var isConflict: Boolean = false
    protected var connectionListener: EMConnectionListener = object : EMConnectionListener {

        override fun onDisconnected(error: Int) {
            if (error == EMError.USER_REMOVED || error == EMError.USER_LOGIN_ANOTHER_DEVICE || error == EMError.SERVER_SERVICE_RESTRICTED
                    || error == EMError.USER_KICKED_BY_CHANGE_PASSWORD || error == EMError.USER_KICKED_BY_OTHER_DEVICE) {
                isConflict = true
            } else {
                handler.sendEmptyMessage(0)
            }
        }

        override fun onConnected() {
            handler.sendEmptyMessage(1)
        }
    }

    protected var handler: Handler = object : Handler() {
        override fun handleMessage(msg: android.os.Message) {
            when (msg.what) {
                0 -> onConnectionDisconnected()
                1 -> onConnectionConnected()

                MSG_REFRESH -> {
                    conversationList.clear()
                    conversationList.addAll(loadConversationList())
                    mChatLv.refresh()
                }
                else -> {
                }
            }
        }
    }

    protected fun onConnectionDisconnected() {
//        errorItemContainer.setVisibility(View.VISIBLE)
    }

    protected fun onConnectionConnected() {
//        errorItemContainer.setVisibility(View.GONE)
    }

    /**
     * refresh ui
     */
    fun refresh() {
        if (!handler.hasMessages(MSG_REFRESH)) {
            handler.sendEmptyMessage(MSG_REFRESH)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mGroupTv -> startActivity<ChatGroupActivity>()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().removeConnectionListener(connectionListener)
    }
}



