package com.yizhipin.goods.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.kennyc.view.MultiStateView
import com.yizhipin.base.common.BaseApplication.Companion.context
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.BasicServices
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ext.startLoading
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.ui.adapter.BasicServicesListAdapter
import com.yizhipin.goods.R
import com.yizhipin.goods.injection.component.DaggerGoodsComponent
import com.yizhipin.goods.injection.module.GoodsModule
import com.yizhipin.goods.presenter.BasicServicesPresenter
import com.yizhipin.goods.presenter.view.BasicServicesView
import kotlinx.android.synthetic.main.activity_recyclerview.*


/**
 * Created by ${XiLei} on 2018/8/23.
 * 基础服务
 */
class BasicServicesListActivity : BaseMvpActivity<BasicServicesPresenter>(), BasicServicesView {

    @Autowired(name = BaseConstant.KEY_SHOP_ID) //注解接收上个页面的传参
    @JvmField
    var mStoreId: String = ""

    private lateinit var mCartAdapter: BasicServicesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)
        initView()
        loadData()
    }

    private fun initView() {
        mHeaderBar.getTiTleTv().text = getString(R.string.basic_services)
        mRv.layoutManager = LinearLayoutManager(this!!)
        mCartAdapter = BasicServicesListAdapter(context!!)
        mRv.adapter = mCartAdapter

        mCustomBtn.onClick {
            custom()
        }
    }

    override fun injectComponent() {
        DaggerGoodsComponent.builder().activityComponent(mActivityComponent).goodsModule(GoodsModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        var map = mutableMapOf<String, String>()
        map.put("storeId", mStoreId)
        mBasePresenter.getBasicServicesData(map)
    }

    override fun onGetBasicServicesSuccess(result: MutableList<BasicServices>) {
        if (result != null && result.size > 0) {
            mCartAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

}



