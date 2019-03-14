package com.yizhipin.goods.ui.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.BasicServices
import com.yizhipin.base.data.response.Evaluate
import com.yizhipin.base.data.response.OrderDetails
import com.yizhipin.base.data.response.SetMealDetails
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.ui.adapter.BasicServicesAdapter
import com.yizhipin.base.ui.adapter.EvaluateAdapter
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.base.widgets.BannerImageLoader
import com.yizhipin.goods.R
import com.yizhipin.goods.injection.component.DaggerGoodsComponent
import com.yizhipin.goods.injection.module.GoodsModule
import com.yizhipin.goods.presenter.SetMealDetailsPresenter
import com.yizhipin.goods.presenter.view.SetMealDetailsView
import com.yizhipin.provider.common.afterLogin
import com.yizhipin.provider.router.RouterPath
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.activity_meal_details.*
import org.jetbrains.anko.startActivity

/**
 * Created by ${XiLei} on 2018/9/22.
 * 套餐详情
 */
class SetMealDetailActivity : BaseMvpActivity<SetMealDetailsPresenter>(), SetMealDetailsView, View.OnClickListener {

    @Autowired(name = BaseConstant.KEY_MEAL_ID)
    @JvmField
    var mMealId: String = "" //套餐id

    private lateinit var mSetMealDetails: SetMealDetails
    private var mAttention: Boolean = false //是否关注
    private lateinit var mBasicServicesAdapter: BasicServicesAdapter
    private lateinit var mEvaluateAdapter: EvaluateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_details)

        initView()
        initBanner()
        loadEvaluateData()
    }

    override fun injectComponent() {
        DaggerGoodsComponent.builder().activityComponent(mActivityComponent).goodsModule(GoodsModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initView() {

        //基础服务
        mBasicServicesRv.layoutManager = GridLayoutManager(this, 3)
        mBasicServicesAdapter = BasicServicesAdapter(this)
        mBasicServicesRv.adapter = mBasicServicesAdapter
        //最新评价
        mEvaluateRv.layoutManager = LinearLayoutManager(this!!)
        mEvaluateAdapter = EvaluateAdapter(this)
        mEvaluateRv.adapter = mEvaluateAdapter

        mBackIv.onClick { finish() }
        mCollectionView.onClick(this)
        mBtn.onClick(this)
        mTakePayTv.onClick(this)
        mShopView.onClick(this)
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

    /**
     * 获取最新评价
     */
    private fun loadEvaluateData() {
        var map = mutableMapOf<String, String>()
        map.put("packageId", mMealId)
        mBasePresenter.getEvaluateData(map)
    }

    /**
     * 获取最新评价成功
     */
    override fun onGetEvaluateSuccess(result: MutableList<Evaluate>) {
        mEvaluateAdapter.setData(result)
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.mShopView -> ARouter.getInstance().build(RouterPath.ShopCenter.PATH_SHOP_DETAILS).withString(BaseConstant.KEY_SHOP_ID, mSetMealDetails.storeId).navigation()
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

            R.id.mTakePayTv -> {
                mSetMealDetails?.let {
                    startActivity<BasicServicesListActivity>(BaseConstant.KEY_SHOP_ID to mSetMealDetails.storeId)
                }
            }

            R.id.mBtn -> {
                afterLogin {
                    var map = mutableMapOf<String, String>()
                    map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
                    map.put("packageId", mMealId)
                    mBasePresenter.order(map)
                }
            }

        }
    }

    /**
     * 下单成功
     */
    override fun onOrderSuccess(result: OrderDetails) {
        startActivity<MealOrderConfirmActivity>(BaseConstant.KEY_MEAL_ORDER_ID to result.id)
    }

    override fun onStart() {
        super.onStart()
        loadGoodDetailsData()
    }

    /**
     * 套餐详情
     */
    private fun loadGoodDetailsData() {
        var map = mutableMapOf<String, String>()
        map.put("id", mMealId)
        map.put("loginUid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mBasePresenter.getMealDetails(map)
    }

    /**
     * 获取套餐详情成功
     */
    override fun onGetMealDetailsSuccess(result: SetMealDetails) {
        mSetMealDetails = result
        with(result) {
            mStarView.setCheckStarCount(evaStar)
            mStarView.refreshView()
            mGoodName.text = title
            mNumTv.text = getString(R.string.service_customer).plus(sellCount).plus(getString(R.string.per_num))
            mPriceTv.text = getString(R.string.rmb).plus(price)
            mCostPriceTv.text = getString(R.string.rmb).plus(marketPrice)
            mShopIv.loadUrl(storeImgurl)
            mShopNameTv.text = storeName
            mDressTv.text = "${clothCount}套"
            mNegativeTv.text = "${filmCount}张"
            mRenCeTv.text = "${rucheCount}张"

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
            loadBasicServicesData(storeId)
        }
    }

    /**
     * 获取基础服务
     */
    private fun loadBasicServicesData(storeId: String) {
        var map = mutableMapOf<String, String>()
        map.put("storeId", storeId)
        mBasePresenter.getBasicServicesData(map)
    }

    /**
     * 获取基础服务成功
     */
    override fun onGetBasicServicesSuccess(result: MutableList<BasicServices>) {

        mBasicServicesAdapter.setData(result)
    }

    /**
     * 关注、取消关注
     */
    private fun loadFollow() {
        var map = mutableMapOf<String, String>()
        map.put("packageId", mMealId)
        map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mBasePresenter.getFollow(map)
    }

    override fun onFollowSuccess(result: Boolean) {
        loadGoodDetailsData()
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    override fun onOrderDetailsSuccess(result: OrderDetails) {
    }
}