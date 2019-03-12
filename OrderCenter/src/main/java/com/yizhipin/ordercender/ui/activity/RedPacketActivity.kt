package com.yizhipin.ordercender.ui.activity

import android.graphics.Paint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.kennyc.view.MultiStateView
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Coupon
import com.yizhipin.base.data.response.RedPacket
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.ordercender.R
import com.yizhipin.ordercender.injection.component.DaggerOrderComponent
import com.yizhipin.ordercender.injection.module.OrderModule
import com.yizhipin.ordercender.presenter.CouponPresenter
import com.yizhipin.ordercender.presenter.view.CouponView
import com.yizhipin.ordercender.ui.adapter.RedPacketAdapter
import com.yizhipin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_red_packet.*

/**
 * Created by ${XiLei} on 2018/9/24.
 * 现金红包
 */

@Route(path = RouterPath.OrderCenter.PATH_ORDER_RED)
class RedPacketActivity : BaseMvpActivity<CouponPresenter>(), CouponView, View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    private var mMaxPage: Int = 1
    private var mCurrentPage: Int = 1
    private lateinit var mCouponAdapter: RedPacketAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_red_packet)

        initView()
        initRefreshLayout()
    }

    private fun initView() {
        mLockTv.onClick(this)
        mLockTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG)
        mAddressRv.layoutManager = LinearLayoutManager(this!!)
        mCouponAdapter = RedPacketAdapter(this)
        mAddressRv.adapter = mCouponAdapter
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
        loadRedBalance()
        loadData()
    }

    override fun injectComponent() {
        DaggerOrderComponent.builder().activityComponent(mActivityComponent).orderModule(OrderModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mLockTv ->   ARouter.getInstance().build(RouterPath.UserCenter.BALANCE).navigation()
        }
    }

    /**
     * 获取红包记录
     */
    private fun loadData() {
        var map = mutableMapOf<String, String>()
        map.put("currentPage", mCurrentPage.toString())
        map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mBasePresenter.getRedPacketList(map)
    }

    /**
     * 获取红包记录成功
     */
    override fun onRedPacketListSuccess(result: BasePagingResp<MutableList<RedPacket>>) {
        mRefreshLayout.endLoadingMore()
        mRefreshLayout.endRefreshing()
        if (result != null && result.data != null && result.data!!.size > 0) {
            mMaxPage = result!!.pi.totalPage
            if (mCurrentPage == 1) {
                mCouponAdapter.setData(result.data!!)
            } else {
                mCouponAdapter.dataList.addAll(result.data!!)
                mCouponAdapter.notifyDataSetChanged()
            }
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    /**
     * 获取红包总额
     */
    private fun loadRedBalance() {
        var map = mutableMapOf<String, String>()
        map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mBasePresenter.getRedBalance(map)
    }

    /**
     * 获取红包总额成功
     */
    override fun onRedBalanceSuccess(result: String) {
        mAmountTv.text = result
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

    override fun onCouponListSuccess(result: BasePagingResp<MutableList<Coupon>>) {
    }
}