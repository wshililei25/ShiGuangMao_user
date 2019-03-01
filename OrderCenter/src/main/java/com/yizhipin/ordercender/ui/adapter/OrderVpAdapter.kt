package com.yizhipin.ordercender.ui.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.yizhipin.ordercender.common.OrderConstant
import com.yizhipin.ordercender.common.OrderStatus
import com.yizhipin.ordercender.ui.fragment.OrderFragment

/**
 * Created by ${XiLei} on 2018/9/22.
 */
class OrderVpAdapter(fragmentManager: FragmentManager, context: Context) : FragmentStatePagerAdapter(fragmentManager) {

    private val mTitles = arrayOf("待完成", "已完成")

    override fun getItem(position: Int): Fragment {
        val fragment = OrderFragment()
        val bunder = Bundle()
        when (position) {
            0 -> bunder.putString(OrderConstant.KEY_ORDER_STATUS, OrderStatus.ORDER_UNFINISH)
            1 -> bunder.putString(OrderConstant.KEY_ORDER_STATUS, OrderStatus.ORDER_FINISHED)
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