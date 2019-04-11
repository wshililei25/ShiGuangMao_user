package com.yizhipin.shop.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.kennyc.view.MultiStateView
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Store
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.provider.common.ProvideReqCode
import com.yizhipin.shop.R
import com.yizhipin.shop.injection.component.DaggerShopComponent
import com.yizhipin.shop.injection.module.ShopModule
import com.yizhipin.shop.presenter.ShopPresenter
import com.yizhipin.shop.presenter.view.ShopView
import com.yizhipin.shop.ui.adapter.ShopAdapter
import kotlinx.android.synthetic.main.activity_recyclerview.*
import org.jetbrains.anko.startActivityForResult

/**
 * Created by ${XiLei} on 2018/9/24.
 * 门店
 */

class ShopActivity : BaseMvpActivity<ShopPresenter>(), ShopView, View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    private var mMaxPage: Int = 1
    private var mCurrentPage: Int = 1
    private lateinit var mShopAdapter: ShopAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        initView()
        initRefreshLayout()
    }

    private fun initView() {
        mCustomBtn.onClick(this)

        mHeaderBar.getTiTleTv().text = getString(R.string.select_shop)
        mRv.layoutManager = LinearLayoutManager(this)
        mShopAdapter = ShopAdapter(this)
        mRv.adapter = mShopAdapter
        mShopAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Store> {
            override fun onItemClick(item: Store, position: Int) {
                startActivityForResult<ShopDetailActivity>(ProvideReqCode.CODE_REQ_SHOP, BaseConstant.KEY_SHOP_ID to item.id.toString())
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            ProvideReqCode.CODE_RESULT_SHOP -> {
                var intent = Intent()
                intent.putExtra(BaseConstant.KEY_SHOP_ID, data!!.getStringExtra(BaseConstant.KEY_SHOP_ID))
                intent.putExtra(BaseConstant.KEY_SHOP_NAME, data!!.getStringExtra(BaseConstant.KEY_SHOP_NAME))
                setResult(ProvideReqCode.CODE_RESULT_SHOP, intent)
                finish()
            }
        }
    }

    private fun initRefreshLayout() {
        mRefreshLayout.setDelegate(this)
        mRefreshLayout.setPullDownRefreshEnable(false) //禁止下拉刷新
        val viewHolder = BGANormalRefreshViewHolder(this, true)
        viewHolder.setRefreshViewBackgroundDrawableRes(R.color.yBgGray)
        viewHolder.setLoadMoreBackgroundColorRes(R.color.yBgGray)
        mRefreshLayout.setRefreshViewHolder(viewHolder)
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        var map = mutableMapOf<String, String>()
        map.put("currentPage", mCurrentPage.toString())
//        map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mBasePresenter.getShopList(map)
    }

    override fun injectComponent() {
        DaggerShopComponent.builder().activityComponent(mActivityComponent).shopModule(ShopModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mCustomBtn -> custom()
        }
    }

    override fun onGetShopListSuccess(result: BasePagingResp<MutableList<Store>>) {
        mRefreshLayout.endLoadingMore()
        mRefreshLayout.endRefreshing()
        if (result != null && result.data != null && result.data!!.size > 0) {
            mMaxPage = result!!.pi.totalPage
            if (mCurrentPage == 1) {
                mShopAdapter.setData(result.data!!)
            } else {
                mShopAdapter.dataList.addAll(result.data!!)
                mShopAdapter.notifyDataSetChanged()
            }
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    /**
     * 加载更多
     */
    override fun onBGARefreshLayoutBeginLoadingMore(refreshLayout: BGARefreshLayout?): Boolean {
        return if (mCurrentPage < mMaxPage) {
            mCurrentPage++
            loadData()
            true
        } else {
            false
        }
    }

    /**
     * 下拉刷新
     */
    override fun onBGARefreshLayoutBeginRefreshing(refreshLayout: BGARefreshLayout?) {
        mCurrentPage = 1
        loadData()
    }

}