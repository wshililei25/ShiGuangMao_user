package com.yizhipin.shop.injection.component

import com.yizhipin.base.injection.PerComponentScope
import com.yizhipin.base.injection.component.ActivityComponent
import com.yizhipin.shop.injection.module.ShopModule
import com.yizhipin.shop.ui.activity.ScenicActivity
import com.yizhipin.shop.ui.activity.ScenicDetailActivity
import com.yizhipin.shop.ui.activity.ShopActivity
import com.yizhipin.shop.ui.activity.ShopDetailActivity
import dagger.Component

/**
 * Created by ${XiLei} on 2018/8/4.
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(ShopModule::class))
interface ShopComponent {

    fun inject(activity: ShopActivity)
    fun inject(activity: ShopDetailActivity)
    fun inject(activity: ScenicActivity)
    fun inject(activity: ScenicDetailActivity)
}