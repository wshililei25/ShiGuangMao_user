package com.yizhipin.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.yizhipin.R
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.common.PhotographStatus
import com.yizhipin.base.common.TeacherStatus
import com.yizhipin.base.common.WechatAppID
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.*
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ext.setVisible
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.base.ui.fragment.BaseMvpFragment
import com.yizhipin.base.ui.web.WebViewActivity
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.base.utils.BasePrefsUtils
import com.yizhipin.base.widgets.BannerImageLoader
import com.yizhipin.goods.common.DressShopStatus
import com.yizhipin.goods.common.GoodsConstant
import com.yizhipin.goods.ui.activity.CameramanActivity
import com.yizhipin.goods.ui.activity.DressListActivity
import com.yizhipin.goods.ui.activity.StarActivity
import com.yizhipin.presenter.HomePresenter
import com.yizhipin.presenter.view.HomeView
import com.yizhipin.provider.common.ProvideReqCode
import com.yizhipin.provider.common.afterLogin
import com.yizhipin.provider.router.RouterPath
import com.yizhipin.shop.data.response.CategoryHome
import com.yizhipin.shop.ui.activity.ScenicActivity
import com.yizhipin.shop.ui.activity.ScenicDetailActivity
import com.yizhipin.shop.ui.activity.ShopActivity
import com.yizhipin.shop.ui.adapter.ScenicAdapter
import com.yizhipin.ui.activity.InformationActivity
import com.yizhipin.ui.adapter.CategoryHomeAdapter
import com.yizhipin.ui.adapter.RecommendHomeAdapter
import com.yizhipin.usercenter.injection.component.DaggerMainComponent
import com.yizhipin.usercenter.injection.module.MianModule
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.listener.OnBannerListener
import fr.quentinklein.slt.LocationTracker
import fr.quentinklein.slt.TrackerSettings
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult
import org.jetbrains.anko.support.v4.toast


/**
 * Created by ${XiLei} on 2018/8/19.
 */
class HomeFragment : BaseMvpFragment<HomePresenter>(), HomeView, View.OnClickListener {

    private lateinit var mHotGoodsAdapter: ScenicAdapter

    private var mLongitude: Double = 0.00
    private var mLatitude: Double = 0.00
    private lateinit var mIWXAPI: IWXAPI

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_home, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLocation()
        initOssAddress()
        initView()
        initHotGoodsView()
        initBanner()
        initCategoryRv()
        initRecommendRv()
    }

    override fun injectComponent() {
        DaggerMainComponent.builder().activityComponent(mActivityComponent).mianModule(MianModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initView() {
        mMoreTv.onClick(this)
        mStoreTv.onClick(this)
    }

    private fun initHotGoodsView() {
        mGoodsRv.layoutManager = LinearLayoutManager(activity!!)
        mHotGoodsAdapter = ScenicAdapter(activity!!)
        mGoodsRv.adapter = mHotGoodsAdapter
        mHotGoodsAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<ScenicSpot> {
            override fun onItemClick(item: ScenicSpot, position: Int) {
                startActivity<ScenicDetailActivity>(BaseConstant.KEY_SCENIC_ID to item.id.toString())
            }
        })
    }

    private fun initBanner() {
        //设置图片加载器
        mHomeBanner.setImageLoader(BannerImageLoader())
        //设置banner动画效果
        mHomeBanner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        mHomeBanner.isAutoPlay(true)
        //设置轮播时间
        mHomeBanner.setDelayTime(2000);
        //设置指示器位置（当banner模式中有指示器时）
        mHomeBanner.setIndicatorGravity(BannerConfig.RIGHT);
    }

    private fun initCategoryRv() {
        var dataList = mutableListOf(CategoryHome(R.drawable.mainicon1, getString(R.string.veil_photography)),
                CategoryHome(R.drawable.mainicon2, getString(R.string.describe_photography)),
                CategoryHome(R.drawable.mainicon3, getString(R.string.baby_photography)),
                CategoryHome(R.drawable.mainicon4, getString(R.string.formal_place)),
                CategoryHome(R.drawable.mainicon5, getString(R.string.time_cloud)),
                CategoryHome(R.drawable.mainicon6, getString(R.string.time_supermarket)),
                CategoryHome(R.drawable.mainicon7, getString(R.string.with_pat)),
                CategoryHome(R.drawable.mainicon8, getString(R.string.integral_mall)))

        mCategoryRv.layoutManager = GridLayoutManager(activity, 4)
        val categoryHomeAdapter = CategoryHomeAdapter(activity!!)
        categoryHomeAdapter.setData(dataList)
        mCategoryRv.adapter = categoryHomeAdapter
        categoryHomeAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<CategoryHome> {
            override fun onItemClick(item: CategoryHome, position: Int) {
//                Bus.send(HomeIntentEvent(position))

                when (position) {
                    0 -> ARouter.getInstance().build(RouterPath.GoodsCenter.PATH_MEAL).withString(BaseConstant.KEY_PHOTOGRAPH, PhotographStatus.DEAL_WEDDING).navigation()
                    1 -> ARouter.getInstance().build(RouterPath.GoodsCenter.PATH_MEAL).withString(BaseConstant.KEY_PHOTOGRAPH, PhotographStatus.DEAL_PHOTO).navigation()
                    2 -> ARouter.getInstance().build(RouterPath.GoodsCenter.PATH_MEAL).withString(BaseConstant.KEY_PHOTOGRAPH, PhotographStatus.DEAL_BABY).navigation()
                    3 -> ARouter.getInstance().build(RouterPath.GoodsCenter.PATH_GOODS_DRESSSHOP).navigation()
                    4 -> {
                        afterLogin { ARouter.getInstance().build(RouterPath.GoodsCenter.PATH_CLOUD_DISK).navigation() }
                    }
                    5 -> ARouter.getInstance().build(RouterPath.GoodsCenter.PATH_TIME_SUPERMARKET).navigation()
                    6 -> toast(getString(R.string.please_look_forward_to))
                    7 -> {
                        afterLogin { ARouter.getInstance().build(RouterPath.GoodsCenter.PATH_INTEGRAL).navigation() }
                    }
                }
            }
        })
    }

    private fun initRecommendRv() {
        var dataList = mutableListOf(
                CategoryHome(R.drawable.mainphoto1, getString(R.string.hot_shoot)),
                CategoryHome(R.drawable.mainphoto2, getString(R.string.majordomo_recommend)),
                CategoryHome(R.drawable.mainphoto3, getString(R.string.list_of_star)),
                CategoryHome(R.drawable.mainphoto4, getString(R.string.formal_recommned)))

        mRecommendRv.layoutManager = GridLayoutManager(activity, 2)
        val categoryHomeAdapter = RecommendHomeAdapter(activity!!)
        categoryHomeAdapter.setData(dataList)
        mRecommendRv.adapter = categoryHomeAdapter
        categoryHomeAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<CategoryHome> {
            override fun onItemClick(item: CategoryHome, position: Int) {
                when (position) {
                    0 -> startActivity<ScenicActivity>()
                    1 -> startActivity<CameramanActivity>(BaseConstant.KEY_CAMERAMAN_TYPE to TeacherStatus.TEACHER_SHEYING)
                    2 -> startActivity<StarActivity>()
                    3 -> startActivity<DressListActivity>(GoodsConstant.KEY_DRESS_SHOP_STATUS to DressShopStatus.DRESS_SHOP_SHARE, BaseConstant.KEY_DRESS to 0)
                }
            }
        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mMoreTv -> startActivity<InformationActivity>()
            R.id.mStoreTv -> startActivityForResult<ShopActivity>(ProvideReqCode.CODE_REQ_SHOP)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            ProvideReqCode.CODE_RESULT_SHOP -> {
                mStoreTv.text = data!!.getStringExtra(BaseConstant.KEY_SHOP_NAME)
                loadGoodsData(data!!.getStringExtra(BaseConstant.KEY_SHOP_ID))
                AppPrefsUtils.putString(BaseConstant.KEY_SHOP_ID, data!!.getStringExtra(BaseConstant.KEY_SHOP_ID))
                AppPrefsUtils.putString(BaseConstant.KEY_SHOP_NAME, data!!.getStringExtra(BaseConstant.KEY_SHOP_NAME))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        loadUnReadNewCount()
        loadNewsData()
        loadBannerData()
    }

    private fun loadNewsData() {
        var map = mutableMapOf<String, String>()
        map.put("currentPage", "1")
        map.put("type", "0")
        mBasePresenter.getNews(map)
    }

    override fun onGetNewsSuccess(result: BasePagingResp<MutableList<News>>) {
        mNewsTv.text = result.data.get(0).content
    }

    private fun loadBannerData() {
        mBasePresenter.getBanner()
    }

    override fun onGetBannerSuccess(result: MutableList<Banner>) {
        var list = arrayListOf<String>()
        for (data in result) {
            list.add(AppPrefsUtils.getString(BaseConstant.IMAGE_ADDRESS).plus(data.imgurl))
        }
        //设置图片集合
        mHomeBanner.setImages(list)
        //banner设置方法全部调用完毕时最后调用
        mHomeBanner.start()
        mHomeBanner.setOnBannerListener(object : OnBannerListener {
            override fun OnBannerClick(position: Int) {
                if (!result[position].url.isNullOrEmpty()) {
                    startActivity<WebViewActivity>(WebViewActivity.EXTRA_URL to result[position].url)
                }

            }

        })
    }

    private fun loadUnReadNewCount() {
        var mapCount = mutableMapOf<String, String>()
        mapCount.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN))
        mBasePresenter.getUnreadNewCount(mapCount)
    }

    /**
     * 获取未读消息数成功
     */
    override fun getUnReadNewCount(result: Int) {
        if (result > 0) {
            mNewCountIv.setVisible(true)
        }
    }

    /**
     * 获取图片地址(阿里云存储)
     */
    private fun initOssAddress() {
        mBasePresenter.getOssAddress()
    }

    /**
     * 获取图片地址成功
     */
    override fun onGetOssAddressSuccess(result: OssAddress) {
        BasePrefsUtils.putOssInfo(result)
    }

    /**
     * 获取经纬度
     */
    @SuppressLint("MissingPermission")
    private fun initLocation() {

        //允许GPS、WiFi、基站定位，设置超时时间5秒
        val trackerSettings = TrackerSettings()
        trackerSettings.setUseGPS(true).setUseNetwork(true).setUsePassive(true).timeout = 5000
        val locationTracker = object : LocationTracker(activity!!, trackerSettings) {
            override fun onLocationFound(location: Location) {
                //定位成功时回调
                if (location != null) {
                    mLongitude = location.longitude
                    mLatitude = location.latitude
                    Log.d("XiLei", "经纬度：" + location.longitude + "," + location.latitude)
                    loadDefaultStore(location.longitude, location.latitude)
                }
            }

            override fun onTimeout() {
                //定位超时回调
                Log.d("XiLei", "定位超时")
            }
        }
        locationTracker.startListening()
    }

    /**
     * 获取附近门店
     */
    private fun loadDefaultStore(longitude: Double, latitude: Double) {

        var map = mutableMapOf<String, String>()
        map.put("lng", longitude.toString())
        map.put("lat", latitude.toString())
        mBasePresenter.getDefaultStore(map)
    }

    /**
     * 获取附近门店成功
     */
    override fun onGetDefaultStoreSuccess(result: Store) {
        AppPrefsUtils.putString(BaseConstant.KEY_SHOP_ID, result.id)
        AppPrefsUtils.putString(BaseConstant.KEY_SHOP_NAME, result.storeName)
        mStoreTv.text = result.storeName
        loadGoodsData(result.id.toString())
    }

    /**
     * 获取热门景点
     */
    private fun loadGoodsData(storeId: String) {
        var map = mutableMapOf<String, String>()
        map.put("storeId", storeId)
        map.put("hot", "true")
        mBasePresenter.getGoodsList(map)
    }

    /**
     * 获取热门景点成功
     */
    override fun onGetGoodsListSuccess(result: MutableList<ScenicSpot>) {
        mHotGoodsAdapter.setData(result)
    }
}