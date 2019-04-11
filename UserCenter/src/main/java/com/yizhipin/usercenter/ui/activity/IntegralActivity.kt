package com.yizhipin.usercenter.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.kennyc.view.MultiStateView
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.FeeRecord
import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ext.startLoading
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.provider.common.afterLogin
import com.yizhipin.provider.router.RouterPath
import com.yizhipin.usercenter.R
import com.yizhipin.usercenter.injection.component.DaggerUserComponent
import com.yizhipin.usercenter.injection.module.UserModule
import com.yizhipin.usercenter.presenter.IntegralPresenter
import com.yizhipin.usercenter.presenter.view.IntegralView
import com.yizhipin.usercenter.ui.adapter.FeeRecordAdapter
import kotlinx.android.synthetic.main.activity_integral.*

/**
 * Created by ${XiLei} on 2018/9/24.
 *
 * 积分
 */
class IntegralActivity : BaseMvpActivity<IntegralPresenter>(), IntegralView, View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    private var mMaxPage: Int = 1
    private var mCurrentPage: Int = 1
    private lateinit var mFeeRecordAdapter: FeeRecordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_integral)

        initView()
        initRefreshLayout()
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initView() {
        mIntegralMallTv.onClick(this)
        mCustomBtn.onClick(this)

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mFeeRecordAdapter = FeeRecordAdapter(this)
        mRecyclerView.adapter = mFeeRecordAdapter
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
        loadBalance()
        getIntegralList()
    }

    private fun loadBalance() {
        var map = mutableMapOf<String, String>()
        map.put("id", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mBasePresenter.getUserInfo(map)
    }

    /**
     * 获取用户信息成功
     */
    override fun getUserResult(result: UserInfo) {
        mIntegralTv.text = result.score.toString()
    }

    /**
     * 积分明细
     */
    private fun getIntegralList() {
        var map = mutableMapOf<String, String>()
        map.put("currentPage", mCurrentPage.toString())
        map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mMultiStateView.startLoading()
        mBasePresenter.getIntegralList(map)
    }

    /**
     * 获取积分明细成功
     */
    override fun getIntegralListSuccess(result: BasePagingResp<MutableList<FeeRecord>>) {

        mRefreshLayout.endLoadingMore()
        mRefreshLayout.endRefreshing()
        if (result != null && result.data != null && result.data!!.size > 0) {
            mMaxPage = result!!.pi.totalPage
            if (mCurrentPage == 1) {
                mFeeRecordAdapter.setData(result.data!!)
            } else {
                mFeeRecordAdapter.dataList.addAll(result.data!!)
                mFeeRecordAdapter.notifyDataSetChanged()
            }
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mCustomBtn -> custom()
            R.id.mIntegralMallTv -> afterLogin { ARouter.getInstance().build(RouterPath.GoodsCenter.PATH_INTEGRAL).navigation() }
        }
    }

    override fun onBGARefreshLayoutBeginRefreshing(refreshLayout: BGARefreshLayout?) {
        mCurrentPage = 1
        getIntegralList()
    }

    override fun onBGARefreshLayoutBeginLoadingMore(refreshLayout: BGARefreshLayout?): Boolean {
        return if (mCurrentPage < mMaxPage) {
            mCurrentPage++
            getIntegralList()
            true
        } else {
            false
        }
    }

}