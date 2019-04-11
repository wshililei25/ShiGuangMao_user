package com.yizhipin.goods.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.WindowManager
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kennyc.view.MultiStateView
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.TeacherWorks
import com.yizhipin.base.event.ImageLookEvent
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ext.startLoading
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.goods.R
import com.yizhipin.goods.injection.component.DaggerGoodsComponent
import com.yizhipin.goods.injection.module.GoodsModule
import com.yizhipin.goods.presenter.TeacherWorksPresenter
import com.yizhipin.goods.presenter.view.TeacherWorksView
import com.yizhipin.goods.ui.adapter.WorksAdapter
import kotlinx.android.synthetic.main.activity_recyclerview.*
import java.util.*

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

    private var screenWidth: Int = 0//屏幕宽度
    private var screenHeight: Int = 0//屏幕高度
    private var xyMap = HashMap<Int, FloatArray>()//所有子项的坐标

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)
        initView()
        initRefreshLayout()
        loadData()
        initObserve()
    }

    private fun initView() {

        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        screenWidth = wm.defaultDisplay.width
        screenHeight = wm.defaultDisplay.height

        mHeaderBar.getTiTleTv().text = getString(R.string.works)
        mRv.layoutManager = LinearLayoutManager(this)
        mAdapter = WorksAdapter(this!!)
        mRv.adapter = mAdapter
        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<TeacherWorks> {
            override fun onItemClick(item: TeacherWorks, position: Int) {
                lookImage(item.imgurls, 0)
            }
        })

        mCustomBtn.onClick {
            custom()
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

    private fun initObserve() {
        Bus.observe<ImageLookEvent>()
                .subscribe { t: ImageLookEvent ->
                    run {
                        lookImage(mAdapter.dataList.get(t.itemPosition).imgurls, t.position)
                    }
                }.registerInBus(this)
    }

    private fun lookImage(imgurls: String, position: Int) {
        var listResult = mutableListOf<String>()
        imgurls?.let {

            if (imgurls.contains(",")) {
                var list = imgurls!!.split(",").toMutableList()
                for (l in list) {
                    listResult.add(AppPrefsUtils.getString(BaseConstant.IMAGE_ADDRESS).plus(l))
                }
            } else {
                listResult.add(AppPrefsUtils.getString(BaseConstant.IMAGE_ADDRESS).plus(imgurls))
            }
        }

        val intent = Intent(this@TeacherWorksActivity, ImageLookActivity::class.java)
        intent.putStringArrayListExtra("urls", listResult as ArrayList<String>)
        intent.putExtra("position", position)
        xyMap.clear()//每一次点击前子项坐标都不一样，所以清空子项坐标

        //子项前置判断，是否在屏幕内，不在的话获取屏幕边缘坐标
        val view0 = mRv.getChildAt(0)
        val position0 = mRv.getChildPosition(view0)
        if (position0 > 0) {
            for (j in 0 until position0) {
                val xyf = floatArrayOf((1 / 6.0f + j % 3 * (1 / 3.0f)) * screenWidth, 0f)//每行3张图，每张图的中心点横坐标自然是屏幕宽度的1/6,3/6,5/6
                xyMap.put(j, xyf)
            }
        }

        //其余子项判断
        for (i in position0 until mRv.getAdapter()!!.getItemCount()) {
            val view1 = mRv.getChildAt(i - position0)
            if (mRv.getChildPosition(view1) == -1)
            //子项末尾不在屏幕部分同样赋值屏幕底部边缘
            {
                val xyf = floatArrayOf((1 / 6.0f + i % 3 * (1 / 3.0f)) * screenWidth, screenHeight.toFloat())
                xyMap.put(i, xyf)
            } else {
                val xy = IntArray(2)
                view1.getLocationOnScreen(xy)
                val xyf = floatArrayOf(xy[0] * 1.0f + view1.getWidth() / 2, xy[1] * 1.0f + view1.getHeight() / 2)
                xyMap.put(i, xyf)
            }
        }
        intent.putExtra("xyMap", xyMap)
        intent.putExtra("size", listResult.size)
        startActivity(intent)
    }
}