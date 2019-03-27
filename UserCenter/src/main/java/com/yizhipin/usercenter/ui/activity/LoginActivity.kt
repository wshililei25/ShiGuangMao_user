package com.yizhipin.usercenter.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.ext.enable
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.provider.router.RouterPath
import com.yizhipin.usercenter.R
import com.yizhipin.usercenter.injection.component.DaggerUserComponent
import com.yizhipin.usercenter.injection.module.UserModule
import com.yizhipin.usercenter.presenter.LoginPresenter
import com.yizhipin.usercenter.presenter.view.LoginView
import com.yizhipin.usercenter.utils.UserPrefsUtils
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity


/**
 * Created by ${XiLei} on 2018/8/5.
 * 登录
 */
@Route(path = RouterPath.UserCenter.PATH_LOGIN)
class LoginActivity : BaseMvpActivity<LoginPresenter>(), LoginView, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
    }

    private fun initView() {
        mBackIv.onClick(this)
        mRegistBtn.onClick(this)
        mLoginBtn.onClick(this)
        mRightTv.onClick(this)
        mLoginBtn.enable(mMobileEt, { isBtnEnable() })
        mLoginBtn.enable(mPswEt, { isBtnEnable() })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mBackIv -> finish()
            R.id.mRegistBtn -> startActivity<RegisterActivity>()
            R.id.mRightTv -> startActivity<ResetPwdActivity>()

            R.id.mLoginBtn -> {
                loginHuanXin()
                var map = mutableMapOf<String, String>()
                map.put("mobile", mMobileEt.text.toString().trim())
                map.put("password", mPswEt.text.toString().trim())
                map.put("deviceToken", "")
                map.put("deviceType", "android")
                map.put("type", "0")
                mBasePresenter.login(map)
            }
        }
    }

    private fun loginHuanXin() {
        EMClient.getInstance().login(mMobileEt.text.toString().trim(), mPswEt.text.toString().trim(), object : EMCallBack {

            override fun onSuccess() {
                Log.d("XiLei", "环信登录成功")
            }

            override fun onProgress(progress: Int, status: String) {
            }

            override fun onError(code: Int, error: String) {
                Log.d("XiLei", "环信登录失败" + code + "," + error)
//                runOnUiThread { Toast.makeText(applicationContext, "login failed", 0).show() }
            }
        })
    }

    private fun isBtnEnable(): Boolean {
        return mMobileEt.text.isNullOrEmpty().not() &&
                mPswEt.text.isNullOrEmpty().not()
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    /**
     * 登录成功
     */
    override fun onLoginSuccess(result: UserInfo) {

        UserPrefsUtils.putUserInfo(result)
        if (result.nickname.isNullOrEmpty()) {
            startActivity<UserInfoActivity>()
        }
        finish()
    }

}