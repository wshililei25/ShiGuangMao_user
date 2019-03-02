package com.yizhipin.shop.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.kennyc.view.MultiStateView
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.ScenicSpot
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.provider.common.ProvideReqCode
import com.yizhipin.provider.router.RouterPath
import com.yizhipin.shop.R
import com.yizhipin.shop.injection.component.DaggerShopComponent
import com.yizhipin.shop.injection.module.ShopModule
import com.yizhipin.shop.presenter.SceincPresenter
import com.yizhipin.shop.presenter.view.SceincView
import com.yizhipin.shop.ui.adapter.ScenicAdapter
import kotlinx.android.synthetic.main.activity_shop.*
import org.jetbrains.anko.startActivityForResult

/**
 * Created by ${XiLei} on 2018/9/24.
 * 景点
 */
@Route(path = RouterPath.ShopCenter.PATH_SHOP)
class ScenicActivity : BaseMvpActivity<SceincPresenter>(), SceincView, View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    @Autowired(name = BaseConstant.KEY_DRESS_POSITION)
    @JvmField
    var mPosition: Int = -1

    private var mMaxPage: Int = 1
    private var mCurrentPage: Int = 1
    private lateinit var mShopAdapter: ScenicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sceinc)

        initView()
        initRefreshLayout()
        loadData()
    }

    private fun initView() {

        mAddressRv.layoutManager = LinearLayoutManager(this)
        mShopAdapter = ScenicAdapter(this)
        mAddressRv.adapter = mShopAdapter
        mShopAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<ScenicSpot> {
            override fun onItemClick(item: ScenicSpot, position: Int) {

                if (mPosition == -1) {
                    startActivityForResult<ScenicDetailActivity>(ProvideReqCode.CODE_REQ_SHOP, BaseConstant.KEY_SCENIC_ID to item.id.toString())
                } else { //套餐关联景点
                    var intent = Intent()
                    intent.putExtra(BaseConstant.KEY_SCENIC_NAME, item.title)
                    intent.putExtra(BaseConstant.KEY_SCENIC_AMOUNT, item.amount)
                    intent.putExtra(BaseConstant.KEY_SCENIC_IMAGE, item.imgurl)
                    intent.putExtra(BaseConstant.KEY_DRESS_POSITION, mPosition)
                    setResult(ProvideReqCode.CODE_RESULT_FEATURE, intent)
                    finish()
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            ProvideReqCode.CODE_RESULT_SHOP -> {
                var intent = Intent()
                intent.putExtra(BaseConstant.KEY_SHOP_ID, data!!.getStringExtra(BaseConstant.KEY_SHOP_ID))
                intent.putExtra(BaseConstant.KEY_SHOP_NAME, data!!.getStringExtra(BaseConstant.KEY_SHOP_NAME))
                setResult(ProvideReqCode.CODE_RESULT_SHOP, intent)
                finish()
            }
        }
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
        map.put("storeId", AppPrefsUtils.getString(BaseConstant.KEY_SHOP_ID))
        map.put("hot", "false")
        mBasePresenter.getSceincList(map)
    }

    override fun injectComponent() {
        DaggerShopComponent.builder().activityComponent(mActivityComponent).shopModule(ShopModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    override fun onClick(v: View) {
        when (v.id) {

        }
    }

    override fun onGetScenicListSuccess(result: BasePagingResp<MutableList<ScenicSpot>>) {
        mRefreshLayout.endLoadingMore()
        mRefreshLayout.endRefreshing()
        if (result != null && result.data != null && result.data!!.size > 0) {
            mMaxPage = result!!.pi.totalPage
            if (mCurrentPage == 1) {
                mShopAdapter.setData(result.data!!)
            } else {
                mShopAdapter.dataList.addAll(result.data!!)
                mShopAdapter.notifyDataSetChanged()
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