package com.yizhipin.shop.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.Meal
import com.yizhipin.base.data.response.OrderDetails
import com.yizhipin.base.data.response.ScenicSpot
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.ui.adapter.MealAdapter
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.base.widgets.BannerImageLoader
import com.yizhipin.provider.common.afterLogin
import com.yizhipin.provider.router.RouterPath
import com.yizhipin.shop.R
import com.yizhipin.shop.injection.component.DaggerShopComponent
import com.yizhipin.shop.injection.module.ShopModule
import com.yizhipin.shop.presenter.ScenicDetailsPresenter
import com.yizhipin.shop.presenter.view.ScenicDetailsView
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.activity_scenic_details.*
import org.jetbrains.anko.startActivity

/**
 * Created by ${XiLei} on 2018/9/22.
 * 景点详情
 */
class ScenicDetailActivity : BaseMvpActivity<ScenicDetailsPresenter>(), ScenicDetailsView, View.OnClickListener {

    @Autowired(name = BaseConstant.KEY_SCENIC_ID)
    @JvmField
    var mScenicId: String = ""

    private lateinit var mScenicSpot: ScenicSpot
    private var mAttention: Boolean = false //是否关注
    private lateinit var mMealAdapter: MealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scenic_details)

        initView()
        initBanner()
        loadMealData()
    }

    override fun injectComponent() {
        DaggerShopComponent.builder().activityComponent(mActivityComponent).shopModule(ShopModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initView() {

        //热门套餐
        mMealRv.layoutManager = LinearLayoutManager(this!!)
        mMealAdapter = MealAdapter(this)
        mMealRv.adapter = mMealAdapter

        mBackIv.onClick { finish() }
        mCollectionView.onClick(this)
        mShopView.onClick(this)
        mBtn.onClick(this)
    }

    private fun initBanner() {
        //设置图片加载器
        mBanner.setImageLoader(BannerImageLoader())
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(2000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.mShopView -> startActivity<ShopDetailActivity>(BaseConstant.KEY_SHOP_ID to mScenicSpot.store.id.toString())

            R.id.mCollectionView -> {

                afterLogin {

                    mAttention = !mAttention
                    if (mAttention) {
                        mCollectionTv.text = getString(R.string.follow_y)
                        mCollectionIv.setImageDrawable(resources.getDrawable(R.drawable.heart2))
                    } else {
                        mCollectionTv.text = getString(R.string.follow)
                        mCollectionIv.setImageDrawable(resources.getDrawable(R.drawable.heart))
                    }
                    loadFollow()
                }
            }

            R.id.mBtn -> {
                afterLogin {
                    var map = mutableMapOf<String, String>()
                    map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
                    map.put("attractionsId", mScenicId)
                    mBasePresenter.orderScenic(map)
                }
            }
        }
    }

    /**
     * 下单成功
     */
    override fun onOrderSuccess(result: OrderDetails) {

        ARouter.getInstance().build(RouterPath.GoodsCenter.PATH_MEAL_ORDER)
                .withString(BaseConstant.KEY_MEAL_ORDER_ID, result.id)
                .withParcelable(BaseConstant.KEY_ORDER_SCENIS , result.attractions[0]).navigation()
    }

    override fun onStart() {
        super.onStart()
        loadGoodDetailsData()
    }

    /**
     * 景点详情
     */
    private fun loadGoodDetailsData() {
        var map = mutableMapOf<String, String>()
        map.put("id", mScenicId)
        if (AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID).isNullOrEmpty()) {
            map.put("loginUid", "0")
        } else {
            map.put("loginUid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        }

        mBasePresenter.getScenicDetails(map)
    }

    /**
     * 获取景点详情成功
     */
    override fun onGetScenicDetailsSuccess(result: ScenicSpot) {
        mScenicSpot = result
        with(result) {
            mStarView.setCheckStarCount(starCount.toInt())
            mStarView.refreshView()
            mGoodName.text = title
            mSoldNumTv.text = getString(R.string.sold).plus(sellCount).plus(getString(R.string.per_num))
            mPriceTv.text = getString(R.string.rmb).plus(amount)
            mCostPriceTv.text = getString(R.string.rmb).plus(markerPrice)
            mShopIv.loadUrl(store.imgurl)
            mShopName.text = store.storeName

            mWebView.loadData(content, "text/html", "UTF-8")
            mWebView.getSettings().setJavaScriptEnabled(true);//启用js
            mWebView.getSettings().setBlockNetworkImage(false);//解决图片不显示

            imgurls?.let {
                var listResult = mutableListOf<String>()
                if (imgurls.contains(",")) {
                    var list = imgurls!!.split(",").toMutableList()
                    for (l in list) {
                        listResult.add(AppPrefsUtils.getString(BaseConstant.IMAGE_ADDRESS).plus(l))
                    }
                } else {
                    listResult.add(AppPrefsUtils.getString(BaseConstant.IMAGE_ADDRESS).plus(imgurls))
                }
                mBanner.setImages(listResult)
                mBanner.start()
            }

            mAttention = attention
            if (attention) {
                mCollectionTv.text = getString(R.string.follow_y)
                mCollectionIv.setImageDrawable(resources.getDrawable(R.drawable.heart2))
            } else {
                mCollectionTv.text = getString(R.string.follow)
                mCollectionIv.setImageDrawable(resources.getDrawable(R.drawable.heart))
            }
        }

    }

    /**
     * 获取套餐
     */
    private fun loadMealData() {
        var map = mutableMapOf<String, String>()
        map.put("id", mScenicId)
        mBasePresenter.getMealData(map)
    }

    /**
     * 获取套餐成功
     */
    override fun onGetMealSuccess(result: MutableList<Meal>) {
        mMealAdapter.setData(result)
    }

    /**
     * 关注、取消关注
     */
    private fun loadFollow() {
        var map = mutableMapOf<String, String>()
        map.put("attractionId", mScenicId)
        map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mBasePresenter.getFollowScenic(map)
    }

    override fun onFollowSuccess(result: Boolean) {
    }
}