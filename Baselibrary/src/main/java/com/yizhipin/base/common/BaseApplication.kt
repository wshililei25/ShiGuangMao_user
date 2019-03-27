package com.yizhipin.base.common

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.hyphenate.chat.ChatClient
import com.hyphenate.chat.EMClient
import com.hyphenate.easeui.EaseUI
import com.hyphenate.helpdesk.easeui.UIProvider
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

        //Kefu sdk 初始化简写方式  (客服SDK的初始化必须放在IM EaseUI和Kefu EaseUI之前)
          ChatClient.getInstance().init(this, ChatClient.Options().setAppkey("1135180519177002#shiguangmao")
                  .setTenantId("35")) //tenantId获取地址：kefu.easemob.com，“管理员模式 > 设置 > 企业信息”页面的“租户ID”
        //IM EaseUI 方式
        EaseUI.getInstance().init(this, null)
        EMClient.getInstance().setDebugMode(true)
        //Kefu EaseUI的初始化
        UIProvider.getInstance().init(this);
    }
}