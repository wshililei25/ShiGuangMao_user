package com.yizhipin.base.common

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.hyphenate.chat.EMClient
import com.hyphenate.easeui.EaseUI
import com.yizhipin.base.injection.component.AppComponent
import com.yizhipin.base.injection.component.DaggerAppComponent
import com.yizhipin.base.injection.moudule.AppModule


/**
 * Created by ${XiLei} on 2018/8/4.
 */
open class BaseApplication : MultiDexApplication() {

    lateinit var mAppComponent: AppComponent

    companion object {
        lateinit var context: Context
        lateinit var app: Application
    }

    override fun onCreate() {
        super.onCreate()
        initAppInjection()
        context = this
        app = this
        initRouter()
        initHuanXin()
    }

    private fun initAppInjection() {
        mAppComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    private fun initRouter() {
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);
    }

    /**
     * 环信
     */
    private fun initHuanXin() {
        //EaseUI 方式
        EaseUI.getInstance().init(this, null)
        EMClient.getInstance().setDebugMode(true)
    }
}