package com.yizhipin.usercenter.ui.activity

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.yizhipin.base.ext.enable
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.provider.router.RouterPath
import com.yizhipin.usercenter.R
import com.yizhipin.usercenter.injection.component.DaggerUserComponent
import com.yizhipin.usercenter.injection.module.UserModule
import com.yizhipin.usercenter.presenter.ResetPwdPresenter
import com.yizhipin.usercenter.presenter.view.ResetPwdView
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

/**
 * Created by ${XiLei} on 2018/8/5.
 */
@Route(path = RouterPath.UserCenter.UPDATE_LOGIN_PWD)
class ResetPwdActivity : BaseMvpActivity<ResetPwdPresenter>(), ResetPwdView, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pwd)
        initView()
    }

    private fun initView() {
        mBackIv.onClick(this)
        mSendCodeTv.onClick(this)
        mLoginBtn.onClick(this)
        mLoginBtn.enable(mMobileEt, { isBtnEnable() })
        mLoginBtn.enable(mCodeEt, { isBtnEnable() })
        mLoginBtn.enable(mPswEt, { isBtnEnable() })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mBackIv -> finish()
            R.id.mSendCodeTv -> {
                if (mMobileEt.text.toString().isNullOrEmpty()) {
                    toast(R.string.input_mobile)
                    return
                }
                var map = mutableMapOf<String, String>()
                map.put("mobile", mMobileEt.text.toString())
                mBasePresenter.getCode(map)
            }

            R.id.mLoginBtn -> {
                var map = mutableMapOf<String, String>()
                map.put("mobile", mMobileEt.text.toString())
                map.put("smsCode", mCodeEt.text.toString())
                map.put("password", mPswEt.text.toString())
                map.put("type", "0")
                mBasePresenter.resetPwd(map)
            }
        }
    }

    private fun isBtnEnable(): Boolean {
        return mMobileEt.text.isNullOrEmpty().not() &&
                mCodeEt.text.isNullOrEmpty().not() &&
                mPswEt.text.isNullOrEmpty().not()
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    /**
     * 获取验证码成功
     */
    override fun onGetCodeSuccess(result: Boolean) {
        toast(R.string.get_code_success)
    }

    override fun onResetPwdSuccess(result: Boolean) {
        finish()
    }

}