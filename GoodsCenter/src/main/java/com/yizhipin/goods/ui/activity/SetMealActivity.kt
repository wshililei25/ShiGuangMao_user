package com.yizhipin.goods.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseActivity
import com.yizhipin.goods.R
import com.yizhipin.goods.ui.adapter.SetMealVpAdapter
import com.yizhipin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_tablayout.*

/**
 * Created by ${XiLei} on 2018/9/25.
 * 套餐
 */
@Route(path = RouterPath.GoodsCenter.PATH_MEAL)
class SetMealActivity : BaseActivity() {

    @Autowired(name = BaseConstant.KEY_PHOTOGRAPH)
    @JvmField
    var mType: String = "" //摄影类型

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tablayout)
        initView()
    }

    private fun initView() {
        mHeaderBar.getTiTleTv().text = getString(R.string.select_meal)
        mTab.tabMode = TabLayout.MODE_FIXED
        mVp.adapter = SetMealVpAdapter(supportFragmentManager, this, mType)
        mTab.setupWithViewPager(mVp)

//        mOrderVp.currentItem = intent.getIntExtra(OrderConstant.KEY_ORDER_STATUS, 0)

        mCustomBtn.onClick {
            custom()
        }
    }
}