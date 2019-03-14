package com.yizhipin.generalizecenter.ui.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.common.InteractionStatus
import com.yizhipin.generalizecenter.ui.fragment.InteractionFragment

/**
 * Created by ${XiLei} on 2018/9/22.
 */
class InteractionVpAdapter(fragmentManager: FragmentManager, context: Context) : FragmentStatePagerAdapter(fragmentManager) {

    private val mTitles = arrayOf("全部", "动态", "评论", "我的动态")

    override fun getItem(position: Int): Fragment {
        val fragment = InteractionFragment()
        val bunder = Bundle()
        when (position) {
            0 -> bunder.putString(BaseConstant.KEY_INTERACTION_STATUS, InteractionStatus.INTERACTION_All)
            1 -> bunder.putString(BaseConstant.KEY_INTERACTION_STATUS, InteractionStatus.INTERACTION_INTERACTION)
            2 -> bunder.putString(BaseConstant.KEY_INTERACTION_STATUS, InteractionStatus.INTERACTION_COMMENT)
            3 -> bunder.putString(BaseConstant.KEY_INTERACTION_STATUS, InteractionStatus.INTERACTION_INTERACTION)
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