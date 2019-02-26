package com.yizhipin.base.widgets

import android.content.Context
import android.util.AttributeSet
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.yizhipin.base.R

/**
 * Created by ${XiLei} on 2018/8/19.
 * 主界面底部导航栏
 */
class BottomNavBar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BottomNavigationBar(context, attrs, defStyleAttr) {

    init {
        //首页
        val homeItem = BottomNavigationItem(R.drawable.house, resources.getString(R.string.nav_bar_home))
                .setInactiveIconResource(R.drawable.house2)
                .setActiveColorResource(R.color.yRed)
                .setInActiveColorResource(R.color.yBlackDeep)
        //消息
        val categoryItem = BottomNavigationItem(R.drawable.message, resources.getString(R.string.nav_bar_category))
                .setInactiveIconResource(R.drawable.message2)
                .setActiveColorResource(R.color.yRed)
                .setInActiveColorResource(R.color.yBlackDeep)
        //发现
        val msgItem = BottomNavigationItem(R.drawable.other, resources.getString(R.string.nav_bar_extend))
                .setInactiveIconResource(R.drawable.other2)
                .setActiveColorResource(R.color.yRed)
                .setInActiveColorResource(R.color.yBlackDeep)
        //我的
        val userItem = BottomNavigationItem(R.drawable.user, resources.getString(R.string.nav_bar_user))
                .setInactiveIconResource(R.drawable.user2)
                .setActiveColorResource(R.color.yRed)
                .setInActiveColorResource(R.color.yBlackDeep)

        setMode(BottomNavigationBar.MODE_FIXED)
        setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
        setBarBackgroundColor(R.color.common_white)

        addItem(homeItem)
                .addItem(categoryItem)
                .addItem(msgItem)
                .addItem(userItem)
                .setFirstSelectedPosition(0)
                .initialise()
    }

}