package com.yizhipin.goods.ui.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.DressCategory
import com.yizhipin.goods.common.GoodsConstant
import com.yizhipin.goods.ui.fragment.DressFragment

/**
 * Created by ${XiLei} on 2018/9/22.
 */
class DressVpAdapter(fragmentManager: FragmentManager, context: Context, var list: MutableList<DressCategory>
                     , var mDressShopStatus: String, var mType: Int) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        val fragment = DressFragment()
        val bunder = Bundle()
        bunder.putString(GoodsConstant.KEY_DRESS_CATEGORY, list[position].id.toString())
        bunder.putString(GoodsConstant.KEY_DRESS_SHOP_STATUS, mDressShopStatus)
        bunder.putInt(BaseConstant.KEY_DRESS, mType)
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