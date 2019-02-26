package com.yizhipin.goods.ui.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.common.CloudDiskStatus
import com.yizhipin.goods.ui.fragment.CloudDiskFragment

/**
 * Created by ${XiLei} on 2018/9/22.
 */
class CloudDiskVpAdapter(fragmentManager: FragmentManager, val context: Context) : FragmentStatePagerAdapter(fragmentManager) {
    private val mTitles = arrayOf("私密云盘", "共享云盘", "传输列表", "回收站")
    override fun getItem(position: Int): Fragment {
        val fragment = CloudDiskFragment()
        val bunder = Bundle()
        when (position) {
            0 -> bunder.putString(BaseConstant.KEY_CLOUD_DISK_STATUS, CloudDiskStatus.CLOUD_SECRET)
            1 -> bunder.putString(BaseConstant.KEY_CLOUD_DISK_STATUS, CloudDiskStatus.CLOUD_SHARE)
//            2 -> bunder.putString(OrderConstant.KEY_ORDER_STATUS, OrderStatus.ORDER_PIN)
//            3 -> bunder.putString(OrderConstant.KEY_ORDER_STATUS, OrderStatus.ORDER_WAIT_SEND)
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