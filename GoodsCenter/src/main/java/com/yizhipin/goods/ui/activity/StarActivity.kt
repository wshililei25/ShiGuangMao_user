package com.yizhipin.goods.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.Star
import com.yizhipin.base.data.response.Store
import com.yizhipin.base.data.response.Teacher
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.goods.R
import com.yizhipin.goods.injection.component.DaggerGoodsComponent
import com.yizhipin.goods.injection.module.GoodsModule
import com.yizhipin.goods.presenter.StarPresenter
import com.yizhipin.goods.presenter.view.StarView
import com.yizhipin.goods.ui.adapter.KeeperAdapter
import com.yizhipin.goods.ui.adapter.StarShopAdapter
import com.yizhipin.goods.ui.adapter.TeacherAdapter
import com.yizhipin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_star.*
import org.jetbrains.anko.startActivity

/**
 * Created by ${XiLei} on 2018/9/22.
 * 明星榜
 */
class StarActivity : BaseMvpActivity<StarPresenter>(), StarView, View.OnClickListener {

    private lateinit var mStarShopAdapter: StarShopAdapter
    private lateinit var mKeeperAdapter: KeeperAdapter
    private lateinit var mTeacherAdapter: TeacherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_star)

        initView()
    }

    override fun injectComponent() {
        DaggerGoodsComponent.builder().activityComponent(mActivityComponent).goodsModule(GoodsModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initView() {
        mCustomBtn.onClick(this)
        mStarShopRv.layoutManager = LinearLayoutManager(this)
        mStarShopAdapter = StarShopAdapter(this)
        mStarShopRv.adapter = mStarShopAdapter
        mStarShopAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Store> {
            override fun onItemClick(item: Store, position: Int) {
                ARouter.getInstance().build(RouterPath.ShopCenter.PATH_SHOP_DETAILS).withString(BaseConstant.KEY_SHOP_ID, item.id).navigation()
            }
        })

        mKeeperRv.layoutManager = LinearLayoutManager(this)
        mKeeperAdapter = KeeperAdapter(this)
        mKeeperRv.adapter = mKeeperAdapter

        mTeacherRv.layoutManager = LinearLayoutManager(this)
        mTeacherAdapter = TeacherAdapter(this, "")
        mTeacherRv.adapter = mTeacherAdapter
        mTeacherAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Teacher> {
            override fun onItemClick(item: Teacher, position: Int) {
                startActivity<TeacherDetailActivity>(BaseConstant.KEY_TEACHER_ID to item.id)
            }
        })

        mBasePresenter.getStar()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mCustomBtn -> custom()
        }
    }

    override fun onGetStarSuccess(result: Star) {

        result.stores?.let {
            mStarShopAdapter.setData(result.stores)
        }
        result.storeManagers?.let {
            mKeeperAdapter.setData(result.storeManagers)
        }
        result.teachers?.let {
            mTeacherAdapter.setData(result.teachers)
        }

    }

}