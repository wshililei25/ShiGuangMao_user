package com.yizhipin.goods.ui.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.kennyc.view.MultiStateView
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.TeacherWorks
import com.yizhipin.base.ext.startLoading
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.goods.R
import com.yizhipin.goods.injection.component.DaggerGoodsComponent
import com.yizhipin.goods.injection.module.GoodsModule
import com.yizhipin.goods.presenter.TeacherWorksPresenter
import com.yizhipin.goods.presenter.view.TeacherWorksView
import com.yizhipin.goods.ui.adapter.WorksAdapter
import kotlinx.android.synthetic.main.activity_recyclerview.*

/**
 * Created by ${XiLei} on 2018/9/25.
 * 老师作品列表
 */
class TeacherWorksActivity : BaseMvpActivity<TeacherWorksPresenter>(), TeacherWorksView, BGARefreshLayout.BGARefreshLayoutDelegate {

    @Autowired(name = BaseConstant.KEY_TEACHER_USER_ID)
    @JvmField
    var mTeacherId = ""

    private var mMaxPage: Int = 1
    private var mCurrentPage: Int = 1
    private lateinit var mAdapter: WorksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)
        initView()
        initRefreshLayout()
        loadData()
    }

    private fun initView() {
        mHeaderBar.getTiTleTv().text = getString(R.string.works)
        mRv.layoutManager = GridLayoutManager(this, 2)
        mAdapter = WorksAdapter(this!!)
        mRv.adapter = mAdapter
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
        map.put("uid", mTeacherId)
        mMultiStateView.startLoading()
        mBasePresenter.getTeacherWorks(map)
    }

    override fun injectComponent() {
        DaggerGoodsComponent.builder().activityComponent(mActivityComponent).goodsModule(GoodsModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    override fun onGetTeacherWorksSuccess(result: BasePagingResp<MutableList<TeacherWorks>>) {

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