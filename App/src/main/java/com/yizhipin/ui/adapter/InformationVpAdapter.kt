package com.yizhipin.ui.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.yizhipin.common.InformationStatus
import com.yizhipin.common.MainConstant
import com.yizhipin.ui.fragment.InformationFragment

/**
 * Created by ${XiLei} on 2018/9/22.
 */
class InformationVpAdapter(fragmentManager: FragmentManager, context: Context) : FragmentPagerAdapter(fragmentManager) {

    private val mTitles = arrayOf("新闻动态", "备婚须知")

    override fun getItem(position: Int): Fragment {
        val fragment = InformationFragment()
        val bunder = Bundle()
        when (position) {
            0 -> bunder.putString(MainConstant.INFORMATIN_TYPE, InformationStatus.NEWS)
            1 -> bunder.putString(MainConstant.INFORMATIN_TYPE, InformationStatus.MARRIAGE)
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