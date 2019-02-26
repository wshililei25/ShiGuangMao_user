package com.yizhipin.goods.ui.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.goods.common.MealStatus
import com.yizhipin.goods.ui.fragment.SetMealFragment

/**
 * Created by ${XiLei} on 2018/9/22.
 */
class SetMealVpAdapter(fragmentManager: FragmentManager, context: Context, val type: String) : FragmentPagerAdapter(fragmentManager) {

    private val mTitles = arrayOf("全部", "私人定制", "套餐")

    override fun getItem(position: Int): Fragment {
        val fragment = SetMealFragment()
        val bunder = Bundle()
        bunder.putString(BaseConstant.KEY_PHOTOGRAPH, type)
        when (position) {
            0 -> bunder.putString(BaseConstant.KEY_MEAL_STATUS, MealStatus.DEAL_ALL)
            1 -> bunder.putString(BaseConstant.KEY_MEAL_STATUS, MealStatus.DEAL_PERSONAL)
            2 -> bunder.putString(BaseConstant.KEY_MEAL_STATUS, MealStatus.DEAL_DEAL)
        }
        fragment.arguments = bunder
        return fragment
    }

    override fun getCount(): Int {
        return mTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }
}