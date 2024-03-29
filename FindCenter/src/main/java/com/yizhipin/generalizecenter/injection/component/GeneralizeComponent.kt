package com.yizhipin.goods.injection.component

import com.yizhipin.base.injection.PerComponentScope
import com.yizhipin.base.injection.component.ActivityComponent
import com.yizhipin.generalizecenter.ui.activity.*
import com.yizhipin.generalizecenter.ui.fragment.InteractionFragment
import com.yizhipin.goods.injection.module.GeneralizeModule
import dagger.Component

/**
 * Created by ${XiLei} on 2018/8/4.
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(GeneralizeModule::class))
interface GeneralizeComponent {

    fun inject(activity: InteractionFragment)
    fun inject(activity: InteractionDetailsActivity)
}