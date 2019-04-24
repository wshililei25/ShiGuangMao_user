package com.yizhipin.base.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.hyphenate.helpdesk.easeui.util.IntentBuilder
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.yizhipin.base.common.AppManager
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.utils.AppPrefsUtils
import org.jetbrains.anko.find

/**
 * Created by ${XiLei} on 2018/5/28.
 */
open class BaseActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ARouter.getInstance().inject(this)
        AppManager.instance.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.finishActivity(this)
    }

    //获取Window中视图content
    val contentView: View
        get() {
            val content = find<FrameLayout>(android.R.id.content)
            return content.getChildAt(0)
        }

    /**
     * 客服
     */
    fun custom() {
        if(AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID).isNotEmpty()){
            var intent = IntentBuilder(this)
                    .setServiceIMNumber("100") //客服关联的IM服务号
                    .build()
            startActivity(intent)
        }else{
            ARouter.getInstance().build("/userCenter/login").navigation()
        }

    }
}