package com.yizhipin.goods.ui.activity

import android.graphics.Paint
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.DressDetails
import com.yizhipin.base.event.DressBuyEvent
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ext.setVisible
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.base.widgets.BannerImageLoader
import com.yizhipin.goods.R
import com.yizhipin.goods.common.GoodsConstant
import com.yizhipin.goods.injection.component.DaggerGoodsComponent
import com.yizhipin.goods.injection.module.GoodsModule
import com.yizhipin.goods.presenter.DressDetailPresenter
import com.yizhipin.goods.presenter.view.DressDetailView
import com.yizhipin.goods.widget.GoodsSkuPopView
import com.yizhipin.provider.common.afterLogin
import com.yizhipin.provider.router.RouterPath
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.activity_dress_details.*
import q.rorbin.badgeview.QBadgeView

/**
 * Created by ${XiLei} on 2018/9/22.
 * 服装详情
 */
class DressDetailActivity : BaseMvpActivity<DressDetailPresenter>(), DressDetailView, View.OnClickListener {

    @Autowired(name = GoodsConstant.KEY_DRESS_ID)
    @JvmField
    var mDressId: String = ""

    @Autowired(name = BaseConstant.KEY_IS_BUY)
    @JvmField
    var mIsBuy: Boolean = false  //是服装购买还是服装租借

    private var mAttention: Boolean = false //是否关注
    private lateinit var mDressDetails: DressDetails
    private lateinit var mGoodsSkuPopView: GoodsSkuPopView
    private lateinit var mQBadgeView: QBadgeView

    //SKU弹层出场动画
    private lateinit var mAnimationStart: Animation
    //SKU弹层退场动画
    private lateinit var mAnimationEnd: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dress_details)

        initView()
        initBanner()
        initSkuPop()
        initAnim()
        initObserve()
        loadGoodDetailsData()
    }

    override fun injectComponent() {
        DaggerGoodsComponent.builder().activityComponent(mActivityComponent).goodsModule(GoodsModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initView() {

        if (mIsBuy) {
            mBuyBtn.setVisible(true)
        } else {
            mRentBtn.setVisible(true)
            mTakePhotoBtn.setVisible(true)
        }

        mQBadgeView = QBadgeView(this)
        mCostPriceTv.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        mCostPriceTv.paint.isAntiAlias = true

        mBackIv.onClick { finish() }
        mShopView.onClick(this)
        mAddCartBtn.onClick(this)
        mCollectionView.onClick(this)
        mBuyBtn.onClick(this)
        mRentBtn.onClick(this)
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

    private fun initSkuPop() {
        mGoodsSkuPopView = GoodsSkuPopView(this)
        mGoodsSkuPopView.setOnDismissListener {
            contentView.startAnimation(mAnimationEnd)
        }
    }

    /**
     * 初始化缩放动画
     */
    private fun initAnim() {
        mAnimationStart = ScaleAnimation(
                1f, 0.95f, 1f, 0.95f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        mAnimationStart.duration = 500
        mAnimationStart.fillAfter = true

        mAnimationEnd = ScaleAnimation(
                0.95f, 1f, 0.95f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        mAnimationEnd.duration = 500
        mAnimationEnd.fillAfter = true
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mShopView -> ARouter.getInstance().build(RouterPath.ShopCenter.PATH_SHOP_DETAILS).withString(BaseConstant.KEY_SHOP_ID, mDressDetails.store.id.toString()).navigation()
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
                    loadFollowDress()
                }
            }
            R.id.mBuyBtn, R.id.mRentBtn -> {
                afterLogin {
                    mGoodsSkuPopView.showAtLocation(contentView, Gravity.BOTTOM, 0, 0)
                    contentView.startAnimation(mAnimationStart)
                }
            }

        }
    }

    /**
     * 服装详情
     */
    private fun loadGoodDetailsData() {
        var map = mutableMapOf<String, String>()
        map.put("id", mDressId)
        map.put("loginUid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mBasePresenter.getGoodsDetail(map)
    }

    /**
     * 获取服装详情成功
     */
    override fun onGetGoodsDetailSuccess(result: DressDetails) {
        mDressDetails = result
        with(result) {
            mStarView.setCheckStarCount(starCount)
            mStarView.refreshView()
            mGoodName.text = title
            mSoldNumTv.text = getString(R.string.sold) + sellCount.toString()
            mPriceTv.text = getString(R.string.rmb) + amount
            mCostPriceTv.text = getString(R.string.rmb) + markerPrice

            store?.let {
                mShopName.text = store.storeName
                mShopIv.loadUrl(store.imgurl)
            }

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

            mGoodsSkuPopView.setSkuData(result)
            /*mGoodsSkuPopView.setGoodsIcon(imgurl)
            mGoodsSkuPopView.setGoodsName(title)
            mGoodsSkuPopView.setGoodsPrice(amount.toString())
            mGoodsSkuPopView.setGoodsNum(norms.get(0).items.get(0).inventory)*/
        }

    }

    /**
     * 关注、取消关注
     */
    private fun loadFollowDress() {
        var map = mutableMapOf<String, String>()
        map.put("clothesId", mDressId.toString())
        map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mBasePresenter.followDress(map)
    }

    override fun onFollowDressSuccess(result: DressDetails) {
    }

    private fun initObserve() {
        Bus.observe<DressBuyEvent>()
                .subscribe { t: DressBuyEvent ->
                    run {
                        ARouter.getInstance().build(RouterPath.OrderCenter.PATH_ORDER_CONFIRM)
                                .withBoolean(BaseConstant.KEY_IS_BUY, mIsBuy)
                                .withString(BaseConstant.KEY_DRESS_ID, mDressDetails.id.toString())
                                .withString(BaseConstant.KEY_DRESS_NORMS, t.norms)
                                .withInt(BaseConstant.KEY_DRESS_INVENTORY, t.inventory)
                                .navigation()
                    }

                }
                .registerInBus(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}