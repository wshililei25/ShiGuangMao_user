package com.yizhipin.usercenter.ui.activity

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.ext.enable
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.usercenter.R
import com.yizhipin.usercenter.injection.component.DaggerUserComponent
import com.yizhipin.usercenter.injection.module.UserModule
import com.yizhipin.usercenter.presenter.RegisterPresenter
import com.yizhipin.usercenter.presenter.view.RegisterView
import fr.quentinklein.slt.LocationTracker
import fr.quentinklein.slt.TrackerSettings
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast


/**
 * Created by ${XiLei} on 2018/8/5.
 */
class RegisterActivity : BaseMvpActivity<RegisterPresenter>(), RegisterView, View.OnClickListener {

    private var mLongitude: Double = 0.00
    private var mLatitude: Double = 0.00
    private var mCurrentNum = 60
    private val TIME: Long = 1000

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initView()
        initLocation()
    }

    private fun initView() {
        mBackIv.onClick(this)
        mSendCodeTv.onClick(this)
        mLoginBtn.onClick(this)
        mCustomBtn.onClick(this)
        mLoginBtn.enable(mMobileEt, { isBtnEnable() })
        mLoginBtn.enable(mCodeEt, { isBtnEnable() })
        mLoginBtn.enable(mPswEt, { isBtnEnable() })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mCustomBtn -> custom()
            R.id.mBackIv -> finish()
            R.id.mSendCodeTv -> {
                if (mMobileEt.text.toString().isNullOrEmpty()) {
                    toast(R.string.input_mobile)
                    return
                }
                mCurrentNum = 60
                mSendCodeTv.postDelayed(mRefreshRunnable, TIME);
                var map = mutableMapOf<String, String>()
                map.put("mobile", mMobileEt.text.toString())
                mBasePresenter.getCode(map)
            }

            R.id.mLoginBtn -> {
                var map = mutableMapOf<String, String>()
                map.put("mobile", mMobileEt.text.toString().trim())
                map.put("smsCode", mCodeEt.text.toString().trim())
                map.put("password", mPswEt.text.toString().trim())
                map.put("requestCode", mInvitationCodeEt.text.toString().trim())
                map.put("type", "0")
                map.put("lng", mLongitude.toString())
                map.put("lat", mLatitude.toString())
                mBasePresenter.register(map)
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

    /**
     * 注册成功
     */
    override fun onRegisterSuccess(result: UserInfo) {

        //由于后台做了环信注册，这里不需要再注册
        /*Thread(object : Runnable {
            override fun run() {
                try {
                    EMClient.getInstance().createAccount(mMobileEt.text.toString().trim(), mPswEt.text.toString().trim())
                    Log.d("XiLei", "环信注册成功")
                } catch (e: HyphenateException) {
                    e.printStackTrace()
                    Log.d("XiLei", "环信注册失败--- " + e.errorCode + "," + e.message)
                }
            }

        }).start()*/
        AppPrefsUtils.putString(BaseConstant.KEY_SP_USER_ID, result.id)
        finish()
    }

    /**
     * 获取经纬度
     */
    @SuppressLint("MissingPermission")
    private fun initLocation() {

        //允许GPS、WiFi、基站定位，设置超时时间5秒
        val trackerSettings = TrackerSettings()
        trackerSettings.setUseGPS(true).setUseNetwork(true).setUsePassive(true).timeout = 5000

        RxPermissions(this).request(android.Manifest.permission.ACCESS_COARSE_LOCATION
                , android.Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe({ granted ->
                    if (granted) {
                        val locationTracker = object : LocationTracker(this, trackerSettings) {
                            override fun onLocationFound(location: Location) {
                                //定位成功时回调
                                if (location != null) {
                                    mLongitude = location.longitude
                                    mLatitude = location.latitude
                                    Log.d("XiLei", "经纬度：" + location.longitude + "," + location.latitude)
                                }
                            }

                            override fun onTimeout() {
                                //定位超时回调
                                Log.d("XiLei", "定位超时")
                            }
                        }
                        locationTracker.startListening()
                    } else {
                        Log.d("XiLei", "请开启定位权限111")
                    }
                })
    }

    private val mRefreshRunnable: Runnable = object : Runnable {
        override fun run() {
            mSendCodeTv.text = mCurrentNum.toString() + "s"

            if (mCurrentNum == 0) {
                mSendCodeTv.removeCallbacks(this)
                mSendCodeTv.isEnabled = true
                mSendCodeTv.text = "重发验证码"
            } else {
                mCurrentNum -= 1
                mSendCodeTv.isEnabled = false
                mSendCodeTv.postDelayed(this, TIME);
            }
        }
    }

}