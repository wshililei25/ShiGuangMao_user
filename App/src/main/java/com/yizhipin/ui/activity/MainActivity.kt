package com.yizhipin.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.hyphenate.helpdesk.easeui.util.IntentBuilder
import com.yizhipin.R
import com.yizhipin.base.common.AppManager
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.event.HomeIntentEvent
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.ui.dialog.CustomDialog
import com.yizhipin.base.ui.dialog.RedPackageDialog
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.presenter.MainPresenter
import com.yizhipin.presenter.view.MainView
import com.yizhipin.ui.fragment.ChatListFragment
import com.yizhipin.ui.fragment.FindFragment
import com.yizhipin.ui.fragment.HomeFragment
import com.yizhipin.ui.fragment.MeFragment
import com.yizhipin.usercenter.injection.component.DaggerMainComponent
import com.yizhipin.usercenter.injection.module.MianModule
import kotlinx.android.synthetic.main.activity_main.*
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener
import org.jetbrains.anko.toast


class MainActivity : BaseMvpActivity<MainPresenter>(), MainView {

    private var mFragments: MutableList<Fragment>? = null
    private val mHomeFragment by lazy { HomeFragment() }
    private val mCategoryFragment by lazy { ChatListFragment() }
    private val mFindFragment by lazy { FindFragment() }
    private val mMeFragment by lazy { MeFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initFragment()
        initBottomNav()
        initObserve()
        initDialog()
    }

    override fun injectComponent() {
        DaggerMainComponent.builder().activityComponent(mActivityComponent).mianModule(MianModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initView(){
        mCustomBtn.onClick {
           custom()
        }
    }

    private fun initFragment() {

        mFragments = ArrayList()
        mFragments!!.add(mHomeFragment)
        mFragments!!.add(mCategoryFragment)
        mFragments!!.add(mFindFragment)
        mFragments!!.add(mMeFragment)
        if (mHomeFragment != null) { //默认选中第一个
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.mFrameLayout, mHomeFragment)
            transaction.commit()
        }

    }

    private fun initBottomNav() {

        var navigationController = mBottomNavBar.material()
                .addItem(R.drawable.house, getString(com.yizhipin.base.R.string.nav_bar_home))
                .addItem(R.drawable.speech_bubble2, getString(com.yizhipin.base.R.string.nav_bar_category))
                .addItem(R.drawable.other, getString(com.yizhipin.base.R.string.nav_bar_extend))
                .addItem(R.drawable.user, getString(com.yizhipin.base.R.string.nav_bar_user))
                .setDefaultColor(ContextCompat.getColor(this, R.color.yGray))
                .build()

        //底部按钮的点击事件监听
        navigationController.addTabItemSelectedListener(object : OnTabItemSelectedListener {
            override fun onSelected(index: Int, old: Int) {
                val currentFragment = mFragments!!.get(index)
                if (currentFragment != null) {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.mFrameLayout, currentFragment!!)
                    transaction.commit()
                }
            }

            override fun onRepeat(index: Int) {}
        })
    }

    /*   private fun changeFragment(position: Int) {
           val manager = supportFragmentManager.beginTransaction()
           for (fragment in mStack) {
               manager.hide(fragment)
           }
           manager.show(mStack[position])
           manager.commit()
       }*/

    private fun initDialog() {
        if (!AppPrefsUtils.getBoolean("isFirst")) {
            var customDialog = CustomDialog(this)
            customDialog.show()
            AppPrefsUtils.putBoolean("isFirst", true)
        }
    }

    private fun initObserve() {
        Bus.observe<HomeIntentEvent>()
                .subscribe { t: HomeIntentEvent ->
                    run {

                        if (t.position == 3) {
//                            changeFragment(2)
//                            mBottomNavBar.selectTab(2)
                        } else {
//                            changeFragment(1)
//                            mBottomNavBar.selectTab(1)
                        }
                    }
                }.registerInBus(this)
    }

    override fun onStart() {
        super.onStart()
        var map = mutableMapOf<String, String>()
        map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mBasePresenter.getIsShowRedPackage(map)
    }

    override fun onIsShowRedPackageSuccess(result: Boolean) {
        if (result) {
            var redPackageDialog = RedPackageDialog(this)
            redPackageDialog.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    private var mPressTime: Long = 0
    override fun onBackPressed() {
        val time = System.currentTimeMillis()
        if (time - mPressTime > 2000) {
            toast("再按一次退出App")
            mPressTime = time
        } else {
            AppManager.instance.exitApp(this)
        }
    }
}


