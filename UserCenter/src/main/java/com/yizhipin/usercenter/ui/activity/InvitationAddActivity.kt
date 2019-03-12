package com.yizhipin.usercenter.ui.activity

import android.os.Bundle
import android.view.View
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.ext.enable
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.usercenter.R
import com.yizhipin.usercenter.injection.component.DaggerUserComponent
import com.yizhipin.usercenter.injection.module.UserModule
import com.yizhipin.usercenter.presenter.InvitationPresenter
import com.yizhipin.usercenter.presenter.InvitationView
import kotlinx.android.synthetic.main.activity_invitation_add.*


/**
 * Created by ${XiLei} on 2018/8/5.
 * 填写邀请码
 */
class InvitationAddActivity : BaseMvpActivity<InvitationPresenter>(), InvitationView, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invitation_add)

        initView()
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initView() {
        mBtn.onClick(this)
        mBtn.enable(mEt, { isBtnEnable() })
    }

    private fun isBtnEnable(): Boolean {
        return mEt.text.isNullOrEmpty().not()
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.mBtn -> {
                var map = mutableMapOf<String, String>()
                map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
                map.put("requestCode", mEt.text.toString())
                mBasePresenter.addInvitation(map)

            }
        }
    }

    override fun addIntegralSuccess(result: UserInfo) {
        finish()
    }

    override fun getIntegralListSuccess(result: MutableList<UserInfo>) {
    }
}