package com.yizhipin.goods.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.kennyc.view.MultiStateView
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Evaluate
import com.yizhipin.base.ext.startLoading
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.ui.adapter.EvaluateAdapter
import com.yizhipin.goods.R
import com.yizhipin.goods.injection.component.DaggerGoodsComponent
import com.yizhipin.goods.injection.module.GoodsModule
import com.yizhipin.goods.presenter.EvaluatePresenter
import com.yizhipin.goods.presenter.view.ReportView
import com.yizhipin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_recyclerview.*

/**
 * Created by ${XiLei} on 2018/9/2.
 * 评价列表(套餐、门店、老师 通用页面)
 */
@Route(path = RouterPath.GoodsCenter.PATH_EVALUATE)
class EvaluateActivity : BaseMvpActivity<EvaluatePresenter>(), ReportView, BGARefreshLayout.BGARefreshLayoutDelegate {

    @Autowired(name = BaseConstant.KEY_MEAL_ID)
    @JvmField
    var mMealId: String = ""

    @Autowired(name = BaseConstant.KEY_SHOP_ID)
    @JvmField
    var mStoreId: String = ""

    @Autowired(name = BaseConstant.KEY_TEACHER_ID)
    @JvmField
    var mTeacherId: String = ""

    private var mCurrentPage: Int = 1
    private var mMaxPage: Int = 1
    private lateinit var mEvaluateAdapter: EvaluateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        initView()
        initRefreshLayout()
        mMultiStateView.startLoading()
    }

    private fun initView() {
        mHeaderBar.getTiTleTv().text = getString(R.string.evaluate)
        mRv.layoutManager = LinearLayoutManager(this)
        mEvaluateAdapter = EvaluateAdapter(this!!)
        mRv.adapter = mEvaluateAdapter
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
        if (mTeacherId.isNullOrEmpty()) {
            map.put("packageId", if (mMealId.isNullOrBlank()) "" else mMealId) //套餐的评价
            map.put("storeId", if (mStoreId.isNullOrBlank()) "" else mStoreId) //店铺的评价
            mBasePresenter.getEvaluateList(map)
        } else {
            map.put("teacherId", if (mTeacherId.isNullOrBlank()) "" else mTeacherId) //老师的评价
            mBasePresenter.getEvaluateTeacherList(map)
        }

    }

    override fun injectComponent() {
        DaggerGoodsComponent.builder().activityComponent(mActivityComponent).goodsModule(GoodsModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    override fun onGetEvaluateListSuccess(result: BasePagingResp<MutableList<Evaluate>>) {
        mRefreshLayout.endLoadingMore()
        mRefreshLayout.endRefreshing()
        if (result != null && result.data != null && result.data!!.size > 0) {
            mMaxPage = result!!.pi.totalPage
            if (mCurrentPage == 1) {
                mEvaluateAdapter.setData(result.data!!)
            } else {
                mEvaluateAdapter.dataList.addAll(result.data!!)
                mEvaluateAdapter.notifyDataSetChanged()
            }
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    override fun onBGARefreshLayoutBeginRefreshing(refreshLayout: BGARefreshLayout?) {
        mCurrentPage = 1
        loadData()
    }

    override fun onBGARefreshLayoutBeginLoadingMore(refreshLayout: BGARefreshLayout?): Boolean {
        return if (mCurrentPage < mMaxPage) {
            mCurrentPage++
            loadData()
            true
        } else {
            false
        }
    }

    override fun onDataIsNull() {
        mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
    }
}