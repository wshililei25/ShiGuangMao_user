package com.yizhipin.usercenter.ui.activity

import android.os.Bundle
import android.view.View
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.RelevanceUser
import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ext.setVisible
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.usercenter.R
import com.yizhipin.usercenter.injection.component.DaggerUserComponent
import com.yizhipin.usercenter.injection.module.UserModule
import com.yizhipin.usercenter.presenter.RelevancePresenter
import com.yizhipin.usercenter.presenter.view.RelevanceView
import kotlinx.android.synthetic.main.activity_relevance_user.*
import org.jetbrains.anko.startActivity


/**
 * Created by ${XiLei} on 2018/8/5.
 * 关联用户
 */
class RelevanceUserActivity : BaseMvpActivity<RelevancePresenter>(), RelevanceView, View.OnClickListener {
    private lateinit var mRelevanceUser: RelevanceUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relevance_user)

        initView()
        getUser()
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initView() {
        mBtn.onClick(this)
        mRenewalTv.onClick(this)
        mDeleteTv.onClick(this)
        mCustomBtn.onClick(this)
    }

    override fun onStart() {
        super.onStart()
        getRelevanceUser()
    }

    private fun getUser() {
        var map = mutableMapOf<String, String>()
        map.put("id", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mBasePresenter.getUserInfo(map)
    }

    override fun getUserSuccess(result: UserInfo) {
        with(result) {
            mUserIconIv.loadUrl(imgurl)
            mUserNameTv.text = nickname
        }
    }

    private fun getRelevanceUser() {
        var map = mutableMapOf<String, String>()
        map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mBasePresenter.getRelevanceUser(map)
    }

    override fun getRelevanceUserSuccess(result: MutableList<RelevanceUser>) {
        if (result.size == 0) {
            mBtn.setVisible(true)
            mRelevanceView.setVisible(false)
        } else {
            mRelevanceUser = result[0]
            mBtn.setVisible(false)
            mRelevanceView.setVisible(true)

            mIconIv.loadUrl(result[0].imgurl)
            mNameTv.text = result[0].nickname
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mCustomBtn -> custom()
            R.id.mBtn -> startActivity<RelevanceAddActivity>(BaseConstant.KEY_IS_ADD to true)
            R.id.mRenewalTv -> startActivity<RelevanceAddActivity>(BaseConstant.KEY_IS_ADD to false,BaseConstant.KEY_RELEVANCE_ID to mRelevanceUser.id)
            R.id.mDeleteTv -> {
                var map = mutableMapOf<String, String>()
                map.put("id", mRelevanceUser.id)
                mBasePresenter.deleteRelevanceUser(map)
            }
        }
    }

    override fun deleteRelevanceUserSuccess(result: Boolean) {
        onStart()
    }

    override fun addRelevanceUserSuccess(result: RelevanceUser) {
    }

}