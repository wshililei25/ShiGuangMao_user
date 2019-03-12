package com.yizhipin.goods.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.kennyc.view.MultiStateView
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Integral
import com.yizhipin.base.ext.startLoading
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.base.ui.fragment.BaseMvpFragment
import com.yizhipin.goods.R
import com.yizhipin.goods.injection.component.DaggerGoodsComponent
import com.yizhipin.goods.injection.module.GoodsModule
import com.yizhipin.goods.presenter.IntegralPresenter
import com.yizhipin.goods.presenter.view.IntegralView
import com.yizhipin.goods.ui.activity.IntegralDetailActivity
import com.yizhipin.goods.ui.adapter.IntegralAdapter
import kotlinx.android.synthetic.main.fragment_dress.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by ${XiLei} on 2018/9/25.
 * 积分商城
 */
class IntegralFragment : BaseMvpFragment<IntegralPresenter>(), IntegralView, BGARefreshLayout.BGARefreshLayoutDelegate {

    private var mMaxPage: Int = 1
    private var mCurrentPage: Int = 1
    private lateinit var mOrderAdapter: IntegralAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initRefreshLayout()
        loadData()
    }

    private fun initView() {
        mOrderRv.layoutManager = LinearLayoutManager(activity)
        mOrderAdapter = IntegralAdapter(activity!!)
        mOrderRv.adapter = mOrderAdapter

        mOrderAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Integral> {
            override fun onItemClick(item: Integral, position: Int) {
                startActivity<IntegralDetailActivity>(BaseConstant.KEY_INTEGRAL_ID to item.id)
            }
        })
    }

    private fun initRefreshLayout() {
        mRefreshLayout.setDelegate(this)
        mRefreshLayout.setPullDownRefreshEnable(false) //禁止下拉刷新
        val viewHolder = BGANormalRefreshViewHolder(activity, true)
        viewHolder.setRefreshViewBackgroundDrawableRes(R.color.yBgGray)
        viewHolder.setLoadMoreBackgroundColorRes(R.color.yBgGray)
        mRefreshLayout.setRefreshViewHolder(viewHolder)
    }

    private fun loadData() {
        var map = mutableMapOf<String, String>()
        map.put("currentPage", mCurrentPage.toString())
        map.put("uid", arguments!!.getString(BaseConstant.KEY_SP_USER_ID, "-1"))
        mMultiStateView.startLoading()
        mBasePresenter.getIntegralList(map)
    }

    override fun injectComponent() {
        DaggerGoodsComponent.builder().activityComponent(mActivityComponent).goodsModule(GoodsModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    override fun onGetIntegralListSuccess(result: BasePagingResp<MutableList<Integral>>) {

        mRefreshLayout.endLoadingMore()
        mRefreshLayout.endRefreshing()
        if (result != null && result.data != null && result.data!!.size > 0) {
            mMaxPage = result!!.pi.totalPage
            if (mCurrentPage == 1) {
                mOrderAdapter.setData(result.data!!)
            } else {
                mOrderAdapter.dataList.addAll(result.data!!)
                mOrderAdapter.notifyDataSetChanged()
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

    override fun onGetIntegralDetailSuccess(result: Integral) {
    }
}