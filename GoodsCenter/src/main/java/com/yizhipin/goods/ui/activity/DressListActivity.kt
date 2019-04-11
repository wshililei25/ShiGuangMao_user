package com.yizhipin.goods.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Dress
import com.yizhipin.base.data.response.DressCategory
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.goods.R
import com.yizhipin.goods.common.GoodsConstant
import com.yizhipin.goods.injection.component.DaggerGoodsComponent
import com.yizhipin.goods.injection.module.GoodsModule
import com.yizhipin.goods.presenter.DressPresenter
import com.yizhipin.goods.presenter.view.DressView
import com.yizhipin.goods.ui.adapter.DressVpAdapter
import kotlinx.android.synthetic.main.activity_dress_list.*

/**
 * Created by ${XiLei} on 2018/9/25.
 * 服装列表
 */
class DressListActivity : BaseMvpActivity<DressPresenter>(), DressView, View.OnClickListener {

    @Autowired(name = GoodsConstant.KEY_DRESS_SHOP_STATUS) //注解接收上个页面的传参
    @JvmField
    var mDressShopStatus: String = "" //服装馆类别

    @Autowired(name = BaseConstant.KEY_DRESS)
    @JvmField
    var mType: Int = -1 //是否男装女装

    private var mSex = "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dress_list)

        initView()
        getDressCategory()
    }

    override fun injectComponent() {
        DaggerGoodsComponent.builder().activityComponent(mActivityComponent).goodsModule(GoodsModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initView() {

        mBackIv.onClick(this)
        mWomenTv.onClick(this)
        mManTv.onClick(this)
        mCustomBtn.onClick(this)
        when (mType) {
            0 -> initWomenView()
            1 -> initManView()
        }
    }

    /**
     * 获取服装类别
     */
    private fun getDressCategory() {
        var map = mutableMapOf<String, String>()
        map.put("sex", mSex)
        map.put("type", mDressShopStatus)
        mBasePresenter.getDressCategory(map)
    }

    /**
     * 获取服装类别成功
     */
    override fun onGetDressCategorySuccess(result: MutableList<DressCategory>) {
        mOrderTab.tabMode = TabLayout.MODE_SCROLLABLE
        mOrderVp.adapter = DressVpAdapter(supportFragmentManager, this, result, mDressShopStatus,mType)
        mOrderTab.setupWithViewPager(mOrderVp)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mCustomBtn -> custom()
            R.id.mBackIv -> finish()
            R.id.mWomenTv -> initWomenView()
            R.id.mManTv -> initManView()
        }
    }

    private fun initWomenView() {
        mSex = "1"
        mWomenTv.setTextColor(ContextCompat.getColor(this, R.color.yWhite))
        mManTv.setTextColor(ContextCompat.getColor(this, R.color.yRed))
        mWomenTv.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_red_solid_left))
        mManTv.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_red_corner_right))
        getDressCategory()
    }

    private fun initManView() {
        mSex = "0"
        mWomenTv.setTextColor(ContextCompat.getColor(this, R.color.yRed))
        mManTv.setTextColor(ContextCompat.getColor(this, R.color.yWhite))
        mWomenTv.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_red_corder_left))
        mManTv.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_red_solid_right))
        getDressCategory()
    }

    override fun onGetDressListSuccess(result: BasePagingResp<MutableList<Dress>>) {
    }

}