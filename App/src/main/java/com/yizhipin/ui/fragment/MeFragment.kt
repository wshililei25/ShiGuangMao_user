package com.yizhipin.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.yizhipin.R
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.FeeRecord
import com.yizhipin.base.data.response.OssAddress
import com.yizhipin.base.data.response.Store
import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ext.setVisible
import com.yizhipin.base.ui.fragment.BaseMvpFragment
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.ordercender.ui.activity.OrderActivity
import com.yizhipin.ordercender.ui.activity.ShipAddressActivity
import com.yizhipin.paycenter.ui.activity.CashPledgeActivity
import com.yizhipin.provider.common.ProvideReqCode
import com.yizhipin.provider.common.afterLogin
import com.yizhipin.provider.common.isLogined
import com.yizhipin.provider.router.RouterPath
import com.yizhipin.shop.ui.activity.ShopActivity
import com.yizhipin.ui.activity.*
import com.yizhipin.usercenter.common.UserConstant
import com.yizhipin.usercenter.injection.component.DaggerMainComponent
import com.yizhipin.usercenter.injection.module.MianModule
import com.yizhipin.usercenter.presenter.UserInfoPresenter
import com.yizhipin.usercenter.presenter.view.UserInfoView
import com.yizhipin.usercenter.ui.activity.*
import kotlinx.android.synthetic.main.fragment_me.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult
import q.rorbin.badgeview.QBadgeView

/**
 * Created by ${XiLei} on 2018/8/19.
 */
class MeFragment : BaseMvpFragment<UserInfoPresenter>(), UserInfoView, View.OnClickListener {
    private lateinit var mQBadgeView: QBadgeView

    private lateinit var mUserInfo: UserInfo

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_me, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    override fun injectComponent() {
        DaggerMainComponent.builder().activityComponent(mActivityComponent).mianModule(MianModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    override fun onStart() {
        super.onStart()
        mStoreTv.text = AppPrefsUtils.getString(BaseConstant.KEY_SHOP_NAME)
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
        mFollowTv.onClick(this)
        mNoviceHelpv.onClick(this)
        mWithdrawTv.onClick(this)
        mRecommendationsTv.onClick(this)
        mNewIv.onClick(this)
        mShareMoneyTv.onClick(this)
    }

    private fun loadData() {
        if (isLogined()) {
            mGradeIv.visibility = View.VISIBLE
            mRelevanceTv.visibility = View.VISIBLE
            var map = mutableMapOf<String, String>()
            map.put("id", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
            mBasePresenter.getUserInfo(map)

            var mapCount = mutableMapOf<String, String>()
            mapCount.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
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
            mIntegralTv.text = score
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
            R.id.mFollowTv -> { //关注
                afterLogin {
                    startActivity<FollowActivity>()
                }
            }
            R.id.mRecommendationsTv -> {
                afterLogin {
                    startActivity<ComplainActivity>()
                }
            }
            R.id.mWithdrawTv -> {
                afterLogin {
                    ARouter.getInstance().build(RouterPath.PayCenter.PATH_PAY_WITHDRAW)
                            .withBoolean(BaseConstant.KEY_IS_CASH, false).navigation()
                }
            }
            R.id.mCustomerPhonev -> startActivity<CustomServiceActivity>()
            R.id.mNoviceHelpv -> startActivity<HelpActivity>()
            R.id.mGradeIv, R.id.mRelevanceTv -> {
                mUserInfo?.let {
                    startActivity<RelevanceUserActivity>()
                }
            }
            R.id.mNewIv -> {
                afterLogin {
                    startActivity<NewsActivity>()
                }
            }
            R.id.mShareMoneyTv -> {
                afterLogin {
                    startActivity<ShareActivity>(UserConstant.KEY_INCITATION_CODE to mUserInfo.requestCode)
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
            }
        }
    }

    override fun onGetDefaultStoreSuccess(result: Store) {
    }

    override fun onEditUserResult(result: UserInfo) {
    }

    override fun getFeeRecordListSuccess(result: MutableList<FeeRecord>) {
    }

    override fun onGetOssSignSuccess(result: String) {
    }

    override fun onGetOssSignFileSuccess(result: String) {
    }

    override fun onGetOssAddressSuccess(result: OssAddress) {
    }
}