package com.yizhipin.shop.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.*
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.ui.adapter.EvaluateAdapter
import com.yizhipin.base.ui.adapter.MealAdapter
import com.yizhipin.base.ui.web.WebViewActivity
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.base.widgets.BannerImageLoader
import com.yizhipin.provider.common.ProvideReqCode
import com.yizhipin.provider.common.afterLogin
import com.yizhipin.provider.router.RouterPath
import com.yizhipin.shop.R
import com.yizhipin.shop.injection.component.DaggerShopComponent
import com.yizhipin.shop.injection.module.ShopModule
import com.yizhipin.shop.presenter.ShopDetailsPresenter
import com.yizhipin.shop.presenter.view.ShopDetailsView
import com.yizhipin.shop.ui.adapter.TimeTeacherAdapter
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.activity_shop_details.*
import org.jetbrains.anko.startActivity

/**
 * Created by ${XiLei} on 2018/9/22.
 * 门店详情
 */
@Route(path = RouterPath.ShopCenter.PATH_SHOP_DETAILS)
class ShopDetailActivity : BaseMvpActivity<ShopDetailsPresenter>(), ShopDetailsView, View.OnClickListener {

    @Autowired(name = BaseConstant.KEY_SHOP_ID)
    @JvmField
    var mShopId: String = "" //门店id

    private lateinit var mShopDetails: ShopDetails
    private var mAttention: Boolean = false //是否关注
    private lateinit var mMealAdapter: MealAdapter
    private lateinit var mTimeTeacherAdapter: TimeTeacherAdapter
    private lateinit var mEvaluateAdapter: EvaluateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_details)

        initView()
        initBanner()
        loadBannerData()
        loadHotMealData()
        loadTimeTeacherData()
        loadEvaluateData()
    }

    override fun injectComponent() {
        DaggerShopComponent.builder().activityComponent(mActivityComponent).shopModule(ShopModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initView() {

        //热门套餐
        mHotMealRv.layoutManager = LinearLayoutManager(this!!)
        mMealAdapter = MealAdapter(this)
        mHotMealRv.adapter = mMealAdapter
        //时光老师
        var layoutManager = LinearLayoutManager(this!!)
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL)
        mTimeTeacherRv.layoutManager = layoutManager
        mTimeTeacherAdapter = TimeTeacherAdapter(this)
        mTimeTeacherRv.adapter = mTimeTeacherAdapter
        //最新评价
        mEvaluateRv.layoutManager = LinearLayoutManager(this!!)
        mEvaluateAdapter = EvaluateAdapter(this)
        mEvaluateRv.adapter = mEvaluateAdapter

        mBackIv.onClick { finish() }
        mCollectionView.onClick(this)
        mBtn.onClick(this)
        mEvaluateMoreTv.onClick(this)
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

    private fun loadBannerData() {
        var map = mutableMapOf<String, String>()
        map.put("storeId", mShopId)
        mBasePresenter.getBanner(map)
    }

    override fun onGetBannerSuccess(result: MutableList<Banner>) {

        if (result == null || result.size <= 0) return

        var list = arrayListOf<String>()
        for (data in result) {
            list.add(AppPrefsUtils.getString(BaseConstant.IMAGE_ADDRESS).plus(data.imgurl))
        }
        //设置图片集合
        mBanner.setImages(list)
        //banner设置方法全部调用完毕时最后调用
        mBanner.start()
        mBanner.setOnBannerListener(object : OnBannerListener {
            override fun OnBannerClick(position: Int) {
                if (result != null && result.size > 0 && !result[position].url.isNullOrEmpty()) {
                    startActivity<WebViewActivity>(WebViewActivity.EXTRA_URL to result[position].url)
                }

            }

        })
    }

    /**
     * 获取热门套餐
     */
    private fun loadHotMealData() {
        var map = mutableMapOf<String, String>()
        map.put("storeId", mShopId)
        mBasePresenter.getHotMealData(map)
    }

    /**
     * 获取热门套餐成功
     */
    override fun onGetHotMealSuccess(result: MutableList<Meal>) {
        if (result.size > 3) mMealAdapter.setData(result.subList(0, 3)) else mMealAdapter.setData(result)

    }

    /**
     * 获取时光老师
     */
    private fun loadTimeTeacherData() {
        var map = mutableMapOf<String, String>()
        map.put("storeId", mShopId)
        mBasePresenter.getTimeTeacherData(map)
    }

    /**
     * 获取时光老师成功
     */
    override fun onGetTimeTeacherSuccess(result: MutableList<Teacher>) {
        mTimeTeacherAdapter.setData(result)
    }

    /**
     * 获取最新评价
     */
    private fun loadEvaluateData() {

        var map = mutableMapOf<String, String>()
        map.put("currentPage", "1")
        map.put("packageId", "") //套餐的评价
        map.put("storeId", mShopId) //店铺的评价
        mBasePresenter.getEvaluateList(map)
    }

    /**
     * 获取最新评价成功
     */
    override fun onGetEvaluateListSuccess(result: BasePagingResp<MutableList<Evaluate>>) {
        mEvaluateAdapter.setData(result.data)
    }

    override fun onClick(v: View) {
        when (v.id) {

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
                var intent = Intent()
                intent.putExtra(BaseConstant.KEY_SHOP_ID, mShopDetails.id)
                intent.putExtra(BaseConstant.KEY_SHOP_NAME, mShopDetails.storeName)
                setResult(ProvideReqCode.CODE_RESULT_SHOP, intent)
                finish()
            }
            R.id.mEvaluateMoreTv -> {
                ARouter.getInstance().build(RouterPath.GoodsCenter.PATH_EVALUATE)
                        .withString(BaseConstant.KEY_SHOP_ID, mShopId)
                        .navigation()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        loadGoodDetailsData()
    }

    /**
     * 门店详情
     */
    private fun loadGoodDetailsData() {
        var map = mutableMapOf<String, String>()
        map.put("id", mShopId)
        map.put("loginUid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mBasePresenter.getShopDetails(map)
    }

    /**
     * 获取门店详情成功
     */
    override fun onGetShopDetailsSuccess(result: ShopDetails) {
        mShopDetails = result
        with(result) {
            mStarView.setCheckStarCount(starCount)
            mStarView.refreshView()
            mGoodName.text = storeName
            mNumTv.text = getString(R.string.service_customer).plus(serviceCount).plus(getString(R.string.per_num))
            mAddressTv.text = province.plus(city).plus(detail)
            mIntroduceTv.text = content

            imgurl?.let {
                var list = mutableListOf<String>()
                if (imgurl.contains(",")) {
                    list = imgurl!!.split(",").toMutableList()
                    for (l in list) {
                        list.add(AppPrefsUtils.getString(BaseConstant.IMAGE_ADDRESS).plus(l))
                    }
                } else {
                    list.add(AppPrefsUtils.getString(BaseConstant.IMAGE_ADDRESS).plus(imgurl))
                }
                mBanner.setImages(list)
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
     * 关注、取消关注
     */
    private fun loadFollow() {
        var map = mutableMapOf<String, String>()
        map.put("storeId", mShopId)
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
}