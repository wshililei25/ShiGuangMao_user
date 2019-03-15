package com.yizhipin.goods.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kennyc.view.MultiStateView
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.AddCameraman
import com.yizhipin.base.data.response.Cameraman
import com.yizhipin.base.event.CameramanCheckedEvent
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ext.setVisible
import com.yizhipin.base.ext.startLoading
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.goods.R
import com.yizhipin.goods.injection.component.DaggerGoodsComponent
import com.yizhipin.goods.injection.module.GoodsModule
import com.yizhipin.goods.presenter.TeacherPresenter
import com.yizhipin.goods.presenter.view.TeacherView
import com.yizhipin.goods.ui.adapter.CameramanAdapter
import com.yizhipin.provider.common.ProvideReqCode
import kotlinx.android.synthetic.main.activity_cameraman.*
import org.jetbrains.anko.startActivity


/**
 * Created by ${XiLei} on 2018/8/23.
 * 老师
 */
class TeacherActivity : BaseMvpActivity<TeacherPresenter>(), TeacherView, View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    @Autowired(name = BaseConstant.KEY_MEAL_ORDER_ID)
    @JvmField
    var mOrderId: String = "" //订单id

    @Autowired(name = BaseConstant.KEY_CAMERAMAN_TYPE)
    @JvmField
    var mTeacherType: String = "sheying" //老师类型

    private var mMaxPage: Int = 1
    private var mCurrentPage: Int = 1
    private lateinit var mAdapter: CameramanAdapter
    private var mCameraman: Cameraman? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cameraman)

        initView()
        initObserve()
        initRefreshLayout()
        mAmountTv.setText("0.00")
    }

    private fun initView() {

        mBtn.onClick(this)
        if (!mOrderId.isNullOrBlank()) {
            mBottomView.setVisible(true)
        }

        mRv.layoutManager = LinearLayoutManager(this)
        mAdapter = CameramanAdapter(this, if (mOrderId.isNullOrBlank()) "" else mOrderId)
        mRv.adapter = mAdapter
        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Cameraman> {
            override fun onItemClick(item: Cameraman, position: Int) {
                startActivity<TeacherDetailActivity>(BaseConstant.KEY_CAMERAMAN_ID to item.id
                        , BaseConstant.KEY_TEACHER_USER_ID to item.uid)
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


    override fun onStart() {
        super.onStart()
        loadData()
    }

    override fun injectComponent() {
        DaggerGoodsComponent.builder().activityComponent(mActivityComponent).goodsModule(GoodsModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        var map = mutableMapOf<String, String>()
        map.put("currentPage", mCurrentPage.toString())
        map.put("storeId", AppPrefsUtils.getString(BaseConstant.KEY_SHOP_ID))
        map.put("teacherType", mTeacherType)
        mBasePresenter.getCameramanList(map)
    }

    /**
     * 获取摄影师列表成功
     */
    override fun onGetCameramanListSuccess(result: BasePagingResp<MutableList<Cameraman>>) {

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

    private fun initObserve() {
        Bus.observe<CameramanCheckedEvent>()
                .subscribe { t: CameramanCheckedEvent ->
                    run {
                        mCameraman = t.cameraman
                        if (t.cameraman.webUser.extraAmount.toDouble() > 0) {
                            mAmountTv.text = "¥${t.cameraman.webUser.photoAmount}/套服装 + ¥${t.cameraman.webUser.extraAmount}"
                        } else {
                            mAmountTv.text = "¥${t.cameraman.webUser.photoAmount}/套服装"
                        }
                    }
                }.registerInBus(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mBtn -> {
                if (null == mCameraman) {
                    finish()
                    return
                }
                var map = mutableMapOf<String, String>()
                map.put("orderId", mOrderId)
                map.put("teacherId", mCameraman!!.uid)
                mBasePresenter.addCameraman(map)
            }
        }
    }

    override fun onAddCameramanSuccess(result: AddCameraman) {

        var intent = Intent()
        intent.putExtra(BaseConstant.KEY_ADD_CAMERAMAN, mCameraman!!.webUser.nickname.plus(" | ").plus(mCameraman!!.teacherType))
        intent.putExtra(BaseConstant.KEY_ADD_CAMERAMAN_AMOUNT, "¥${mCameraman!!.webUser.photoAmount}")
        intent.putExtra(BaseConstant.KEY_ADD_CAMERAMAN_URL, mCameraman!!.webUser.imgurl)
        setResult(ProvideReqCode.CODE_RESULT_ADD_CAMERAMAN, intent)
        finish()
    }
}



