package com.yizhipin.shop.injection.module

import com.yizhipin.shop.service.PayService
import com.yizhipin.shop.service.impl.PayServiceImpl
import dagger.Module
import dagger.Provides

/**
 * Created by ${XiLei} on 2018/8/4.
 */
@Module
class PayModule {

    @Provides
    fun providesPayService(service: PayServiceImpl): PayService {
        return service
    }
}