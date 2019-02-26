package com.yizhipin.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import com.alibaba.android.arouter.launcher.ARouter
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import com.yizhipin.R
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseActivity
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.provider.common.ProviderConstant
import com.yizhipin.provider.common.afterLogin
import com.yizhipin.provider.router.RouterPath
import com.yizhipin.usercenter.utils.UserPrefsUtils
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.startActivity

/**
 * Created by ${XiLei} on 2018/8/21.
 */
class SettingActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        initView()
    }

    private fun initView() {

        mToggleButton.isChecked = AppPrefsUtils.getBoolean(ProviderConstant.KEY_IS_PUSH)
        mToggleButton.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked) {
                    AppPrefsUtils.putBoolean(ProviderConstant.KEY_IS_PUSH, true)
                } else {
                    AppPrefsUtils.putBoolean(ProviderConstant.KEY_IS_PUSH, false)
                }
            }

        })
        mAboutTv.onClick(this)
        mLogoutBtn.onClick(this)
        mUpdateLoginTv.onClick(this)
    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.mAboutTv -> startActivity<VersionInfoActivity>()

            R.id.mLogoutBtn -> {
                EMClient.getInstance().logout(false, object : EMCallBack {
                    override fun onSuccess() {
                        Log.d("2", "环信退出登录成功")
                    }

                    override fun onProgress(p0: Int, p1: String?) {
                    }

                    override fun onError(p0: Int, p1: String?) {
                        Log.d("2", "环信退出登录失败---" + p0 + "," + p1)
                    }

                })
                UserPrefsUtils.clearUserInfo()
                finish()
            }
            R.id.mUpdateLoginTv -> {

                afterLogin {
                    ARouter.getInstance().build(RouterPath.UserCenter.UPDATE_LOGIN_PWD).navigation()
                }

            }
        }
    }
}