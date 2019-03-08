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
import com.yizhipin.base.data.response.FeeRecord
import com.yizhipin.base.data.response.Store
import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.event.SelectShopEvent
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ext.setVisible
import com.yizhipin.base.ui.fragment.BaseMvpFragment
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.ordercender.ui.activity.OrderActivity
import com.yizhipin.ordercender.ui.activity.ShipAddressActivity
import com.yizhipin.paycenter.ui.activity.CashPledgeActivity
import com.yizhipin.provider.common.ProvideReqCode
import com.yizhipin.provider.common.ProviderConstant
import com.yizhipin.provider.common.afterLogin
import com.yizhipin.provider.common.isLogined
import com.yizhipin.provider.router.RouterPath
import com.yizhipin.shop.ui.activity.ShopActivity
import com.yizhipin.ui.activity.CustomServiceActivity
import com.yizhipin.ui.activity.SettingActivity
import com.yizhipin.usercenter.common.UserConstant
import com.yizhipin.usercenter.injection.component.DaggerMainComponent
import com.yizhipin.usercenter.injection.module.MianModule
import com.yizhipin.usercenter.presenter.UserInfoPresenter
import com.yizhipin.usercenter.presenter.view.UserInfoView
import com.yizhipin.usercenter.ui.activity.*
import fr.quentinklein.slt.LocationTracker
import fr.quentinklein.slt.TrackerSettings
import kotlinx.android.synthetic.main.fragment_me.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult
import q.rorbin.badgeview.QBadgeView

/**
 * Created by ${XiLei} on 2018/8/19.
 */
class MeFragment : BaseMvpFragment<UserInfoPresenter>(), UserInfoView, View.OnClickListener {
    private lateinit var mQBadgeView: QBadgeView

    private var mLongitude: Double = 0.00
    private var mLatitude: Double = 0.00
    private lateinit var mUserInfo: UserInfo

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_me, null)
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

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun initView() {
        mQBadgeView = QBadgeView(activity)
        mStoreTv.onClick(this)
        mUserIconIv.onClick(this)
        mUserNameTv.onClick(this)
        mSettingTv.onClick(this)
        mAddressTv.onClick(this)
        mAllOrderTv.onClick(this)
        mRedPaperTv.onClick(this)
        mBalanceView.onClick(this)
        mIntegralView.onClick(this)
        mCouponTv.onClick(this)
        mCustomerPhonev.onClick(this)
        mInvitationCodeView.onClick(this)
        mGradeIv.onClick(this)
        mRelevanceTv.onClick(this)
        mForegiftTv.onClick(this)
    }

    private fun loadData() {
        if (isLogined()) {
            mUserIconIv.loadUrl(AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_ICON))
            mGradeIv.visibility = View.VISIBLE
            mRelevanceTv.visibility = View.VISIBLE
            var map = mutableMapOf<String, String>()
            map.put("id", AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN))
            mBasePresenter.getUserInfo(map)

            var mapCount = mutableMapOf<String, String>()
            mapCount.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN))
            mBasePresenter.getUnreadNewCount(mapCount)

        } else {
            mUserIconIv.setImageResource(R.drawable.avatarw)
            mUserNameTv.text = activity!!.getString(R.string.login)
            mBalanceTv.text = "0.00"
            mIntegralTv.text = "0"
            mInvitationCodeTv.text = ""
            mGradeIv.visibility = View.GONE
            mRelevanceTv.visibility = View.GONE
        }
    }

    /**
     *  获取用户信息成功
     */
    override fun getUserResult(result: UserInfo) {
        mUserInfo = result
        with(result) {
            mUserNameTv.text = if (result.nickname.isNullOrEmpty()) getString(R.string.app_name) else nickname
            mBalanceTv.text = amount.toString()
            mIntegralTv.text = score.toString()
            mInvitationCodeTv.text = requestCode
            mUserIconIv.loadUrl(imgurl)
        }
    }

    /**
     * 获取未读消息数成功
     */
    override fun getUnReadNewCount(result: Int) {
        if (result > 0) {
            mNewCountIv.setVisible(true)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mStoreTv -> startActivityForResult<ShopActivity>(ProvideReqCode.CODE_REQ_SHOP)
            R.id.mUserIconIv, R.id.mUserNameTv -> {
                afterLogin {
                    startActivity<UserInfoActivity>(UserConstant.KEY_TO_USERINFO to true)
                }
            }
            R.id.mInvitationCodeView -> { //邀请码
                afterLogin {
                    startActivity<InvitationActivity>(UserConstant.KEY_INCITATION_CODE to mUserInfo.requestCode)
                }
            }
            R.id.mBalanceView -> { //余额
                afterLogin {
                    startActivity<WalletActivity>()
                }
            }
            R.id.mIntegralView -> { //积分
                afterLogin {
                    startActivity<IntegralActivity>()
                }
            }
            R.id.mSettingTv -> {
                startActivity<SettingActivity>()
            }
            R.id.mAddressTv -> { //收货地址
                afterLogin {
                    startActivity<ShipAddressActivity>()
                }
            }
            R.id.mAllOrderTv -> { //订单
                afterLogin {
                    startActivity<OrderActivity>()
                }
            }
            R.id.mForegiftTv -> { //押金
                afterLogin {
                    startActivity<CashPledgeActivity>()
                }
            }
            R.id.mCouponTv -> { //我的优惠券
                afterLogin {
                    ARouter.getInstance().build(RouterPath.OrderCenter.PATH_ORDER_COUPON).navigation()
                }
            }
            R.id.mRedPaperTv -> { //现金红包
                afterLogin {
                    ARouter.getInstance().build(RouterPath.OrderCenter.PATH_ORDER_RED).navigation()
                }
            }
            R.id.mCustomerPhonev -> startActivity<CustomServiceActivity>()
            R.id.mGradeIv, R.id.mRelevanceTv -> {
                mUserInfo?.let {
                    startActivity<RelevanceUserActivity>()
                }
            }
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

    override fun onEditUserResult(result: UserInfo) {
    }

    override fun getFeeRecordListSuccess(result: MutableList<FeeRecord>) {
    }
    override fun onGetOssSignSuccess(result: String) {
    }
}