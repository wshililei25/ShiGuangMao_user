package com.yizhipin.generalizecenter.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kennyc.view.MultiStateView
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Comment
import com.yizhipin.base.data.response.Interaction
import com.yizhipin.base.data.response.InteractionDetails
import com.yizhipin.base.event.LikeEvent
import com.yizhipin.base.ext.startLoading
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.base.ui.fragment.BaseMvpFragment
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.generalizecenter.R
import com.yizhipin.generalizecenter.presenter.InteractionPresenter
import com.yizhipin.generalizecenter.presenter.view.ReportView
import com.yizhipin.generalizecenter.ui.activity.InteractionDetailsActivity
import com.yizhipin.generalizecenter.ui.adapter.InteractionAdapter
import com.yizhipin.goods.injection.component.DaggerGeneralizeComponent
import com.yizhipin.goods.injection.module.GeneralizeModule
import kotlinx.android.synthetic.main.fragment_invest.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by ${XiLei} on 2018/9/2.
 */
class InteractionFragment : BaseMvpFragment<InteractionPresenter>(), ReportView, BGARefreshLayout.BGARefreshLayoutDelegate {

    private var mCurrentPage: Int = 1
    private var mMaxPage: Int = 1
    private lateinit var mEvaluateAdapter: InteractionAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_invest, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initRefreshLayout()
        initObserve()
    }

    private fun initView() {
        mRv.layoutManager = LinearLayoutManager(activity)
        mEvaluateAdapter = InteractionAdapter(activity!!)
        mRv.adapter = mEvaluateAdapter
        mEvaluateAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Interaction> {
            override fun onItemClick(item: Interaction, position: Int) {
                startActivity<InteractionDetailsActivity>(BaseConstant.KEY_INTERACTION_ID to item.id)
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

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        var map = mutableMapOf<String, String>()
        map.put("currentPage", mCurrentPage.toString())
        map.put("type", arguments!!.getString(BaseConstant.KEY_INTERACTION_STATUS, "-1").toString())
        map.put("loginUid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mMultiStateView.startLoading()
        mBasePresenter.getInteractionList(map)
    }

    override fun injectComponent() {
        DaggerGeneralizeComponent.builder().activityComponent(mActivityComponent).generalizeModule(GeneralizeModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    override fun onGetInteractionListSuccess(result: BasePagingResp<MutableList<Interaction>>) {
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

    /**
     * 点赞 / 取消点赞
     */
    private fun initObserve() {
        Bus.observe<LikeEvent>()
                .subscribe { t: LikeEvent ->
                    run {
                        var map = mutableMapOf<String, String>()
                        map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
                        map.put("interactiveId", t.evaId.toString())
                        mBasePresenter.giveLike(map)
                    }
                }
                .registerInBus(this)

    }

    override fun onGetInteractionDetailsSuccess(result: InteractionDetails) {
    }

    override fun onCommentSuccess(result: Comment) {
    }
}