package com.yizhipin.goods.ui.activity

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseActivity
import com.yizhipin.goods.R
import com.yizhipin.goods.common.DressShopStatus
import com.yizhipin.goods.common.GoodsConstant
import com.yizhipin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_dress_shop.*
import org.jetbrains.anko.startActivity

/**
 * Created by ${XiLei} on 2018/9/24.
 * 服装馆
 */

@Route(path = RouterPath.GoodsCenter.PATH_GOODS_DRESSSHOP)
class DressShopActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dress_shop)

        mSellIv.onClick(this)
        mShareIv.onClick(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mSellIv -> startActivity<DressListActivity>(GoodsConstant.KEY_DRESS_SHOP_STATUS to DressShopStatus.DRESS_SHOP_SELL)
            R.id.mShareIv -> startActivity<DressListActivity>(GoodsConstant.KEY_DRESS_SHOP_STATUS to DressShopStatus.DRESS_SHOP_SHARE)
        }
    }
}