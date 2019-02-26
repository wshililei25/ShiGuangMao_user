package com.yizhipin.goods.ui.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.DressCategory
import com.yizhipin.goods.ui.fragment.TimeSuperMarketFragment

/**
 * Created by ${XiLei} on 2018/9/22.
 */
class TimeSuperMarketVpAdapter(fragmentManager: FragmentManager, context: Context, var list: MutableList<DressCategory>) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        val fragment = TimeSuperMarketFragment()
        val bunder = Bundle()
        bunder.putString(BaseConstant.KEY_TIME_MARKET_CATEGORY, list[position].id.toString())
        fragment.arguments = bunder
        return fragment
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list[position].name
    }
}