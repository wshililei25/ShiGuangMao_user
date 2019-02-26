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
import com.yizhipin.base.data.response.CloudDisk
import com.yizhipin.base.data.response.CloudDiskImage
import com.yizhipin.base.ext.startLoading
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.base.ui.fragment.BaseMvpFragment
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.goods.R
import com.yizhipin.goods.injection.component.DaggerGoodsComponent
import com.yizhipin.goods.injection.module.GoodsModule
import com.yizhipin.goods.presenter.CloudDiskPresenter
import com.yizhipin.goods.presenter.view.CloudDiskView
import com.yizhipin.goods.ui.activity.CloudDiskImageActivity
import com.yizhipin.goods.ui.adapter.CloudDiskAdapter
import kotlinx.android.synthetic.main.fragment_dress.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by ${XiLei} on 2018/9/25.
 */
class CloudDiskFragment : BaseMvpFragment<CloudDiskPresenter>(), CloudDiskView, BGARefreshLayout.BGARefreshLayoutDelegate {

    private var mMaxPage: Int = 1
    private var mCurrentPage: Int = 1
    private lateinit var mOrderAdapter: CloudDiskAdapter

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
        mOrderRv.layoutManager = LinearLayoutManager(activity!!)
        mOrderAdapter = CloudDiskAdapter(activity!!)
        mOrderRv.adapter = mOrderAdapter

        mOrderAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<CloudDisk> {
            override fun onItemClick(item: CloudDisk, position: Int) {

                startActivity<CloudDiskImageActivity>(BaseConstant.KEY_CLOUD_DISK_ITEM to item)
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
        map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN))
        map.put("type", arguments!!.getString(BaseConstant.KEY_CLOUD_DISK_STATUS, "-1"))
        mMultiStateView.startLoading()
        mBasePresenter.getCloudDiskList(map)
    }

    override fun injectComponent() {
        DaggerGoodsComponent.builder().activityComponent(mActivityComponent).goodsModule(GoodsModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    override fun onGetCloudDiskSuccess(result: BasePagingResp<MutableList<CloudDisk>>) {

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

    override fun onGetCloudDiskImageSuccess(result: BasePagingResp<MutableList<CloudDiskImage>>) {
    }
}