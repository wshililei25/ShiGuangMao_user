package com.yizhipin.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.yizhipin.R
import com.yizhipin.base.common.AppManager
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.event.HomeIntentEvent
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
import org.jetbrains.anko.toast
import java.util.*

class MainActivity : BaseMvpActivity<MainPresenter>(), MainView {

    private val mStack = Stack<Fragment>()
    private val mHomeFragment by lazy { HomeFragment() }
    private val mCategoryFragment by lazy { ChatListFragment() }
    private val mFindFragment by lazy { FindFragment() }
    private val mMeFragment by lazy { MeFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragment()
        initBottomNav()
        changeFragment(0)
        initObserve()
        initDialog()
    }

    override fun injectComponent() {
        DaggerMainComponent.builder().activityComponent(mActivityComponent).mianModule(MianModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initFragment() {
        val manager = supportFragmentManager.beginTransaction()
        manager.add(R.id.mContaier, mHomeFragment)
        manager.add(R.id.mContaier, mCategoryFragment)
        manager.add(R.id.mContaier, mFindFragment)
        manager.add(R.id.mContaier, mMeFragment)
        manager.commit()

        mStack.add(mHomeFragment)
        mStack.add(mCategoryFragment)
        mStack.add(mFindFragment)
        mStack.add(mMeFragment)
    }

    private fun initBottomNav() {
        mBottomNavBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabReselected(position: Int) {
            }

            override fun onTabUnselected(position: Int) {
            }

            override fun onTabSelected(position: Int) {
                changeFragment(position)
            }

        })
    }

    private fun changeFragment(position: Int) {
        val manager = supportFragmentManager.beginTransaction()
        for (fragment in mStack) {
            manager.hide(fragment)
        }
        manager.show(mStack[position])
        manager.commit()
    }

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
                            changeFragment(2)
                            mBottomNavBar.selectTab(2)
                        } else {
                            changeFragment(1)
                            mBottomNavBar.selectTab(1)
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


