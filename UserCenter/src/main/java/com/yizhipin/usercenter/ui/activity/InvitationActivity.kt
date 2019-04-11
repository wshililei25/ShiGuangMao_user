package com.yizhipin.usercenter.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.kennyc.view.MultiStateView
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ext.startLoading
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.usercenter.R
import com.yizhipin.usercenter.common.UserConstant
import com.yizhipin.usercenter.injection.component.DaggerUserComponent
import com.yizhipin.usercenter.injection.module.UserModule
import com.yizhipin.usercenter.presenter.InvitationPresenter
import com.yizhipin.usercenter.presenter.InvitationView
import com.yizhipin.usercenter.ui.adapter.InvitationAdapter
import kotlinx.android.synthetic.main.activity_invitation.*
import org.jetbrains.anko.startActivity

/**
 * Created by ${XiLei} on 2018/9/24.
 *
 * 邀请码
 */
class InvitationActivity : BaseMvpActivity<InvitationPresenter>(), InvitationView, View.OnClickListener {
    @Autowired(name = UserConstant.KEY_INCITATION_CODE) //注解接收上个页面的传参
    @JvmField
    var mCode: String = ""

    private lateinit var mFeeRecordAdapter: InvitationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invitation)

        initView()
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initView() {
        mInvitationTv.onClick(this)
        mCustomBtn.onClick(this)

        mIntegralTv.text = mCode
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mFeeRecordAdapter = InvitationAdapter(this)
        mRecyclerView.adapter = mFeeRecordAdapter
    }

    override fun onStart() {
        super.onStart()
        getInvitationList()
    }

    /**
     * 邀请人列表
     */
    private fun getInvitationList() {
        var map = mutableMapOf<String, String>()
        map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mMultiStateView.startLoading()
        mBasePresenter.getInvitationList(map)
    }

    /**
     * 获取邀请人列表成功
     */
    override fun getIntegralListSuccess(result: MutableList<UserInfo>) {

        if (result != null && result.size > 0) {
            mFeeRecordAdapter.setData(result!!)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mCustomBtn -> custom()
            R.id.mInvitationTv -> startActivity<InvitationAddActivity>()
        }
    }

    override fun addIntegralSuccess(result: UserInfo) {
    }

}