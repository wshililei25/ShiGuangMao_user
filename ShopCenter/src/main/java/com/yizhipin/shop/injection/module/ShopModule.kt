package com.yizhipin.shop.injection.module

import com.yizhipin.shop.service.ShopService
import com.yizhipin.shop.service.impl.ShopServiceImpl
import dagger.Module
import dagger.Provides

/**
 * Created by ${XiLei} on 2018/8/4.
 */
@Module
class ShopModule {

    @Provides
    fun providesShopService(service: ShopServiceImpl): ShopService {
        return service
    }
}