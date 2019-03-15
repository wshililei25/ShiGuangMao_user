package com.yizhipin.ui.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.yizhipin.ui.fragment.MealFollowFragment
import com.yizhipin.ui.fragment.ShopFollowFragment
import com.yizhipin.ui.fragment.TeacherFoloowFragment

/**
 * Created by ${XiLei} on 2018/9/22.
 */
class FollowVpAdapter(fragmentManager: FragmentManager, context: Context) : FragmentPagerAdapter(fragmentManager) {

    private val mTitles = arrayOf("套餐", "老师", "门店")

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = MealFollowFragment()
            1 -> fragment = TeacherFoloowFragment()
            2 -> fragment = ShopFollowFragment()
        }
        return fragment!!
    }

    override fun getCount(): Int {
        return mTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }
}