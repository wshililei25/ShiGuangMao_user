package com.yizhipin.goods.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.kennyc.view.MultiStateView
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.CloudDisk
import com.yizhipin.base.data.response.CloudDiskImage
import com.yizhipin.base.ext.startLoading
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.goods.R
import com.yizhipin.goods.injection.component.DaggerGoodsComponent
import com.yizhipin.goods.injection.module.GoodsModule
import com.yizhipin.goods.presenter.CloudDiskPresenter
import com.yizhipin.goods.presenter.view.CloudDiskView
import com.yizhipin.goods.ui.adapter.CloudDiskImageAdapter
import kotlinx.android.synthetic.main.acivity_cloud_disk_image.*

/**
 * Created by ${XiLei} on 2018/9/25.
 */
class CloudDiskImageActivity : BaseMvpActivity<CloudDiskPresenter>(), CloudDiskView, BGARefreshLayout.BGARefreshLayoutDelegate {

    private var mMaxPage: Int = 1
    private var mCurrentPage: Int = 1
    private lateinit var mOrderAdapter: CloudDiskImageAdapter
    private lateinit var mCloudDisk: CloudDisk

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acivity_cloud_disk_image)

        initView()
        initRefreshLayout()
        loadData()
    }

    private fun initView() {

        mCloudDisk = intent.getParcelableExtra<CloudDisk>(BaseConstant.KEY_CLOUD_DISK_ITEM)
        mHeaderBar.getTiTleTv().text = mCloudDisk.folder

        mOrderRv.layoutManager = LinearLayoutManager(this!!)
        mOrderAdapter = CloudDiskImageAdapter(this!!)
        mOrderRv.adapter = mOrderAdapter

        mOrderAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<CloudDiskImage> {
            override fun onItemClick(item: CloudDiskImage, position: Int) {
//                startActivity<DressDetailActivity>(GoodsConstant.KEY_DRESS_ID to item.id
//                        , BaseConstant.KEY_IS_BUY to if (arguments!!.getString(BaseConstant.KEY_CLOUD_DISK_STATUS) == "0") true else false)
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
        map.put("folderId", mCloudDisk.id)
//        map.put("folderId", "112")
        mMultiStateView.startLoading()
        mBasePresenter.getCloudDiskImageList(map)
    }

    override fun injectComponent() {
        DaggerGoodsComponent.builder().activityComponent(mActivityComponent).goodsModule(GoodsModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    override fun onGetCloudDiskImageSuccess(result: BasePagingResp<MutableList<CloudDiskImage>>) {

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

    override fun onGetCloudDiskSuccess(result: BasePagingResp<MutableList<CloudDisk>>) {
    }
}