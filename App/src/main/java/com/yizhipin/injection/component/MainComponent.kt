package com.yizhipin.usercenter.injection.component

import com.yizhipin.base.injection.PerComponentScope
import com.yizhipin.base.injection.component.ActivityComponent
import com.yizhipin.ui.activity.HelpActivity
import com.yizhipin.ui.activity.InformationDetailsActivity
import com.yizhipin.ui.fragment.*
import com.yizhipin.usercenter.injection.module.MianModule
import dagger.Component

/**
 * Created by ${XiLei} on 2018/8/4.
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(MianModule::class))
interface MainComponent {

    fun inject(activity: HomeFragment)
    fun inject(activity: FindFragment)
    fun inject(activity: MeFragment)
    fun inject(activity: InformationFragment)
    fun inject(activity: MealFollowFragment)
    fun inject(activity: TeacherFoloowFragment)
    fun inject(activity: ShopFollowFragment)
    fun inject(activity: InformationDetailsActivity)
    fun inject(activity: HelpActivity)
}