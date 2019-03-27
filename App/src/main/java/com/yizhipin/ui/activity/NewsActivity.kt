package com.yizhipin.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.kennyc.view.MultiStateView
import com.yizhipin.R
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.News
import com.yizhipin.base.ext.startLoading
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.presenter.NewsPresenter
import com.yizhipin.presenter.view.NewsView
import com.yizhipin.ui.adapter.InformationAdapter
import com.yizhipin.ui.adapter.NewsAdapter
import com.yizhipin.usercenter.injection.component.DaggerMainComponent
import com.yizhipin.usercenter.injection.module.MianModule
import kotlinx.android.synthetic.main.activity_recyclerview.*

/**
 * Created by ${XiLei} on 2018/9/25.
 * 消息
 */
class NewsActivity : BaseMvpActivity<NewsPresenter>(), NewsView, BGARefreshLayout.BGARefreshLayoutDelegate {

    private var mMaxPage: Int = 1
    private var mCurrentPage: Int = 1
    private lateinit var mAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)


        initView()
        initRefreshLayout()
        loadData()
    }

    private fun initView() {
        mHeaderBar.getTiTleTv().text = getString(R.string.system_news)
        mRv.layoutManager = LinearLayoutManager(this!!)
        mAdapter = NewsAdapter(this!!)
        mRv.adapter = mAdapter
        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<News> {
            override fun onItemClick(item: News, position: Int) {
//                startActivity<InformationDetailsActivity>(BaseConstant.KEY_INFORMATION_ID to item.id)
            }
        })
    }

    private fun initRefreshLayout() {
        mRefreshLayout.setDelegate(this)
        mRefreshLayout.setPullDownRefreshEnable(false) //禁止下拉刷新
        val viewHolder = BGANormalRefreshViewHolder(this, true)
        viewHolder.setRefreshViewBackgroundDrawableRes(R.color.yBgGray)
        viewHolder.setLoadMoreBackgroundColorRes(R.color.yBgGray)
        mRefreshLayout.setRefreshViewHolder(viewHolder)
    }

    private fun loadData() {
        var map = mutableMapOf<String, String>()
        map.put("currentPage", mCurrentPage.toString())
        map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mMultiStateView.startLoading()
        mBasePresenter.getNews(map)
    }

    override fun injectComponent() {
        DaggerMainComponent.builder().activityComponent(mActivityComponent).mianModule(MianModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    override fun onGetNewsSuccess(result: BasePagingResp<MutableList<News>>) {

        mRefreshLayout.endLoadingMore()
        mRefreshLayout.endRefreshing()
        if (result != null && result.data != null && result.data!!.size > 0) {
            mMaxPage = result!!.pi.totalPage
            if (mCurrentPage == 1) {
                mAdapter.setData(result.data!!)
            } else {
                mAdapter.dataList.addAll(result.data!!)
                mAdapter.notifyDataSetChanged()
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