package com.yizhipin.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.yizhipin.R
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.*
import com.yizhipin.base.event.SelectShopEvent
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ext.setVisible
import com.yizhipin.base.ui.fragment.BaseMvpFragment
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.generalizecenter.ui.activity.InteractionActivity
import com.yizhipin.presenter.HomePresenter
import com.yizhipin.presenter.view.HomeView
import com.yizhipin.provider.common.ProvideReqCode
import com.yizhipin.provider.common.afterLogin
import com.yizhipin.provider.router.RouterPath
import com.yizhipin.shop.ui.activity.ShopActivity
import com.yizhipin.usercenter.injection.component.DaggerMainComponent
import com.yizhipin.usercenter.injection.module.MianModule
import fr.quentinklein.slt.LocationTracker
import fr.quentinklein.slt.TrackerSettings
import kotlinx.android.synthetic.main.fragment_find.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult


/**
 * Created by ${XiLei} on 2018/8/19.
 */
class FindFragment : BaseMvpFragment<HomePresenter>(), HomeView, View.OnClickListener {

    private var mLongitude: Double = 0.00
    private var mLatitude: Double = 0.00

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_find, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLocation()
        initView()
        initObserve()
    }

    override fun injectComponent() {
        DaggerMainComponent.builder().activityComponent(mActivityComponent).mianModule(MianModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initView() {
        mInteractionTv.onClick(this)
        mIntegralMallTv.onClick(this)
        mStoreTv.onClick(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mInteractionTv -> startActivity<InteractionActivity>()
            R.id.mIntegralMallTv -> afterLogin { ARouter.getInstance().build(RouterPath.GoodsCenter.PATH_INTEGRAL).navigation() }
            R.id.mStoreTv -> startActivityForResult<ShopActivity>(ProvideReqCode.CODE_REQ_SHOP)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            ProvideReqCode.CODE_RESULT_SHOP -> {
                mStoreTv.text = data!!.getStringExtra(BaseConstant.KEY_SHOP_NAME)
                AppPrefsUtils.putString(BaseConstant.KEY_SHOP_ID, data!!.getStringExtra(BaseConstant.KEY_SHOP_ID))
                AppPrefsUtils.putString(BaseConstant.KEY_SHOP_NAME, data!!.getStringExtra(BaseConstant.KEY_SHOP_NAME))
                Bus.send(SelectShopEvent(data!!.getStringExtra(BaseConstant.KEY_SHOP_NAME)))
            }
        }
    }

    private fun initObserve() {
        Bus.observe<SelectShopEvent>()
                .subscribe { t: SelectShopEvent ->
                    run {
                        mStoreTv.text = t.name
                    }
                }.registerInBus(this)
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        loadUnReadNewCount()
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
     * 获取图片地址成功
     */
    override fun onGetOssAddressSuccess(result: OssAddress) {
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
                    Log.d("2", "经纬度：" + location.longitude + "," + location.latitude)
                    loadDefaultStore(location.longitude, location.latitude)
                }
            }

            override fun onTimeout() {
                //定位超时回调
                Log.d("2", "定位超时")
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
        mStoreTv.text = result.storeName
    }

    override fun onGetNewsSuccess(result: BasePagingResp<MutableList<News>>) {
    }

    override fun onGetBannerSuccess(result: MutableList<Banner>) {
    }

    override fun onGetGoodsListSuccess(result: MutableList<ScenicSpot>) {
    }
}