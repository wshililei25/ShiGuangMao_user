package com.yizhipin.shop.injection.component

import com.yizhipin.base.injection.PerComponentScope
import com.yizhipin.base.injection.component.ActivityComponent
import com.yizhipin.paycenter.ui.activity.CashPledgeActivity
import com.yizhipin.paycenter.ui.activity.RechargeActivity
import com.yizhipin.shop.injection.module.PayModule
import dagger.Component

/**
 * Created by ${XiLei} on 2018/8/4.
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(PayModule::class))
interface PayComponent {

    fun inject(activity: RechargeActivity)
    fun inject(activity: CashPledgeActivity)
}