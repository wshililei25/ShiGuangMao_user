package com.yizhipin.goods.ui.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.goods.ui.fragment.IntegralFragment

/**
 * Created by ${XiLei} on 2018/9/22.
 */
class IntegralVpAdapter(fragmentManager: FragmentManager, context: Context) : FragmentStatePagerAdapter(fragmentManager) {
    private val mTitles = arrayOf("全部", "可兑换")
    override fun getItem(position: Int): Fragment {
        val fragment = IntegralFragment()
        val bunder = Bundle()
        when (position) {
            0 -> bunder.putString(BaseConstant.KEY_SP_TOKEN, "")
            1 -> bunder.putString(BaseConstant.KEY_SP_TOKEN, AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN))
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