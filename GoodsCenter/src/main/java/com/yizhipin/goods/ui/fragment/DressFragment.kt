package com.yizhipin.goods.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.kennyc.view.MultiStateView
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Dress
import com.yizhipin.base.data.response.DressCategory
import com.yizhipin.base.ext.startLoading
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.base.ui.fragment.BaseMvpFragment
import com.yizhipin.goods.R
import com.yizhipin.goods.common.GoodsConstant
import com.yizhipin.goods.injection.component.DaggerGoodsComponent
import com.yizhipin.goods.injection.module.GoodsModule
import com.yizhipin.goods.presenter.DressPresenter
import com.yizhipin.goods.presenter.view.DressView
import com.yizhipin.goods.ui.activity.DressDetailActivity
import com.yizhipin.goods.ui.adapter.DressAdapter
import com.yizhipin.provider.common.ProvideReqCode
import kotlinx.android.synthetic.main.fragment_dress.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by ${XiLei} on 2018/9/25.
 */
class DressFragment : BaseMvpFragment<DressPresenter>(), DressView, BGARefreshLayout.BGARefreshLayoutDelegate {

    private var mMaxPage: Int = 1
    private var mCurrentPage: Int = 1
    private lateinit var mOrderAdapter: DressAdapter
    private var mSex = "1"

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
        mOrderRv.layoutManager = GridLayoutManager(activity, 2)
        mOrderAdapter = DressAdapter(activity!!, arguments!!.getString(GoodsConstant.KEY_DRESS_SHOP_STATUS, "-1"))
        mOrderRv.adapter = mOrderAdapter

        mOrderAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Dress> {
            override fun onItemClick(item: Dress, position: Int) {

                when (arguments!!.getInt(BaseConstant.KEY_DRESS)) {
                    0 -> { //女装选择
                        var intent = Intent()
                        intent.putExtra(BaseConstant.KEY_DRESS_WOMEN_IMAGE, item.imgurl)
                        intent.putExtra(BaseConstant.KEY_DRESS_POSITION, activity!!.intent.getIntExtra(BaseConstant.KEY_DRESS_POSITION, -1))
                        activity!!.setResult(ProvideReqCode.CODE_RESULT_DRESS_WOMEN, intent)
                        activity!!.finish()
                        return
                    }
                    1 -> { //男装选择
                        var intent = Intent()
                        intent.putExtra(BaseConstant.KEY_DRESS_MAN_IMAGE, item.imgurl)
                        intent.putExtra(BaseConstant.KEY_DRESS_POSITION, activity!!.intent.getIntExtra(BaseConstant.KEY_DRESS_POSITION, -1))
                        activity!!.setResult(ProvideReqCode.CODE_RESULT_DRESS_MAN, intent)
                        activity!!.finish()
                        return
                    }
                }

                startActivity<DressDetailActivity>(GoodsConstant.KEY_DRESS_ID to item.id
                        , BaseConstant.KEY_IS_BUY to if (arguments!!.getString(GoodsConstant.KEY_DRESS_SHOP_STATUS, "-1") == "0") true else false)
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
        map.put("sex", mSex)
        map.put("catagory", arguments!!.getString(GoodsConstant.KEY_DRESS_CATEGORY, "-1"))
        map.put("sellType", arguments!!.getString(GoodsConstant.KEY_DRESS_SHOP_STATUS, "-1"))
        mMultiStateView.startLoading()
        mBasePresenter.getDressList(map)
    }

    override fun injectComponent() {
        DaggerGoodsComponent.builder().activityComponent(mActivityComponent).goodsModule(GoodsModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    override fun onGetDressListSuccess(result: BasePagingResp<MutableList<Dress>>) {

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

    override fun onGetDressCategorySuccess(result: MutableList<DressCategory>) {
    }

}