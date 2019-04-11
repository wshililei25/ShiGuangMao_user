package com.yizhipin.goods.injection.component

import com.yizhipin.base.injection.PerComponentScope
import com.yizhipin.base.injection.component.ActivityComponent
import com.yizhipin.goods.injection.module.GoodsModule
import com.yizhipin.goods.ui.activity.*
import com.yizhipin.goods.ui.fragment.*
import dagger.Component

/**
 * Created by ${XiLei} on 2018/8/4.
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(GoodsModule::class))
interface GoodsComponent {

    fun inject(activity: DressListActivity)
    fun inject(activity: DressFragment)
    fun inject(activity: DressDetailActivity)
    fun inject(activity: SetMealFragment)
    fun inject(activity: SetMealDetailActivity)
    fun inject(activity: BasicServicesListActivity)
    fun inject(activity: MealOrderConfirmActivity)
    fun inject(activity: CloudDiskFragment)
    fun inject(activity: CloudDiskImageActivity)
    fun inject(activity: TimeSuperMarketActivity)
    fun inject(activity: TimeSuperMarketFragment)
    fun inject(activity: TimeSuperMarketDetailActivity)
    fun inject(activity: IntegralFragment)
    fun inject(activity: IntegralDetailActivity)
    fun inject(activity: TeacherActivity)
    fun inject(activity: TeacherDetailActivity)
    fun inject(activity: StarActivity)
    fun inject(activity: BrideInfoActivity)
    fun inject(activity: MealOrderDetailsActivity)
    fun inject(activity: EvaluateActivity)
    fun inject(activity: TeacherWorksActivity)
}