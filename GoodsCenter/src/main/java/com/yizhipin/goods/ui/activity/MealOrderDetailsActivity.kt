package com.yizhipin.goods.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.common.PhotographStatus
import com.yizhipin.base.common.TeacherType
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.*
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ext.setVisible
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.goods.R
import com.yizhipin.goods.injection.component.DaggerGoodsComponent
import com.yizhipin.goods.injection.module.GoodsModule
import com.yizhipin.goods.presenter.SetMealDetailsPresenter
import com.yizhipin.goods.presenter.view.SetMealDetailsView
import com.yizhipin.goods.ui.adapter.AppointDressAdapter
import com.yizhipin.goods.ui.adapter.AppointFeatureSpotAdapter
import com.yizhipin.provider.common.ProvideReqCode
import com.yizhipin.provider.common.ProviderConstant
import com.yizhipin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_meal_order_details.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult

/**
 * Created by ${XiLei} on 2018/9/22.
 * 套餐订单详情
 */
@Route(path = RouterPath.GoodsCenter.PATH_ORDER_MEAL_DETAILS)
class MealOrderDetailsActivity : BaseMvpActivity<SetMealDetailsPresenter>(), SetMealDetailsView, View.OnClickListener {

    @Autowired(name = BaseConstant.KEY_MEAL_ORDER_ID)
    @JvmField
    var mOrderId: String = "" //订单id

    private lateinit var mOrderDetails: OrderDetails
    private lateinit var mSetMealDetails: SetMealDetails
    private lateinit var mAppointDressAdapter: AppointDressAdapter
    private lateinit var mAppointFeatureSpotAdapter: AppointFeatureSpotAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_order_details)

        initView()
        loadOrderDetails()
    }

    override fun injectComponent() {
        DaggerGoodsComponent.builder().activityComponent(mActivityComponent).goodsModule(GoodsModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initView() {

        mUserNameIv.loadUrl(AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_ICON))
        mUserNameTv.text = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NICKNAME)

        //指定服装
        var linearLayoutManager = LinearLayoutManager(this!!)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mAppointDressRv.layoutManager = linearLayoutManager
        mAppointDressAdapter = AppointDressAdapter(this)
        mAppointDressRv.adapter = mAppointDressAdapter
        var list = ArrayList<AppiontDress>()
        var appointDress1 = AppiontDress()
        appointDress1.title = "指定服装1"
        list.add(appointDress1)
        var appointDress2 = AppiontDress()
        appointDress2.title = "指定服装2"
        list.add(appointDress2)
        mAppointDressAdapter.setData(list)

        //指定景点
        mAppointFeatureSpotRv.layoutManager = GridLayoutManager(this, 3)
        mAppointFeatureSpotAdapter = AppointFeatureSpotAdapter(this)
        mAppointFeatureSpotRv.adapter = mAppointFeatureSpotAdapter
        var list1 = ArrayList<AppiontSport>()
        var appiontSport1 = AppiontSport()
        appiontSport1.name = "指定景点1"
        list1.add(appiontSport1)
        var appiontSport2 = AppiontSport()
        appiontSport2.name = "指定景点2"
        list1.add(appiontSport2)
        mAppointFeatureSpotAdapter.setData(list1)

        mEditTv.onClick(this)
        mCameramanView.onClick(this)
        mDresserView.onClick(this)
        mBtn.onClick(this)
        mCustomBtn.onClick(this)
    }

    /**
     * 获取订单详情
     */
    private fun loadOrderDetails() {
        var map = mutableMapOf<String, String>()
        map.put("id", mOrderId)
        mBasePresenter.getOrderDetails(map)
    }

    override fun onOrderDetailsSuccess(result: OrderDetails) {
        with(result) {
            mOrderDetails = result
            mNumTv.text = title
            mMealPriceTv.text = getString(R.string.rmb).plus(amount)
            mShopNameTv.text = packages[0].shopName
            mRealityPriceTv.text = amount
            mOrderNumberTv.text = ordernum
            mCreateTimeTv.text = createTime
            mSubscriptionTimeTv.text = earnestPaytime
            mSubscriptionAmountTv.text = if (earnestPay) "¥ ${earnestMoney}" else "¥ 0.00"
            mBalancePaymentTv.text = if (null == payTime) "待付款" else payTime
            mGoodsIv.loadUrl(imgurl)

            payType?.let {
                mBtn.setVisible(false)
            }

            if (earnestPay) {
                mBtn.text = "支付尾款"
                mSubscriptionTv.setVisible(true)
                mSubscriptionTv.text = "已交定金¥ ${earnestMoney}"
            } else {
                mBtn.text = "交定金（¥1000）"
            }

            when (type) {
                PhotographStatus.DEAL_WEDDING -> mTypeTv.text = getString(R.string.veil_photography)
                PhotographStatus.DEAL_PHOTO -> mTypeTv.text = getString(R.string.describe_photography)
                PhotographStatus.DEAL_BABY -> mTypeTv.text = getString(R.string.baby_photography)
                PhotographStatus.DEAL_WEDDING -> mGoodNameTv.text = getString(R.string.veil_photography)
                PhotographStatus.DEAL_PHOTO -> mGoodNameTv.text = getString(R.string.describe_photography)
                PhotographStatus.DEAL_BABY -> mGoodNameTv.text = getString(R.string.baby_photography)
            }
        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mCustomBtn -> custom()
            R.id.mTakePayTv -> {
                mSetMealDetails?.let {
                    startActivity<BasicServicesListActivity>(BaseConstant.KEY_SHOP_ID to mSetMealDetails.storeId)
                }
            }

            R.id.mEditTv -> startActivityForResult<BrideInfoActivity>(1000, BaseConstant.KEY_MEAL_ORDER_ID to mOrderId)

            R.id.mCameramanView -> startActivityForResult<TeacherActivity>(1001
                    , BaseConstant.KEY_MEAL_ORDER_ID to mOrderId, BaseConstant.KEY_CAMERAMAN_TYPE to TeacherType.TEACHER_SHEYING)

            R.id.mDresserView -> startActivityForResult<TeacherActivity>(1002
                    , BaseConstant.KEY_MEAL_ORDER_ID to mOrderId, BaseConstant.KEY_CAMERAMAN_TYPE to TeacherType.TEACHER_HUAZHUANG)

            R.id.mBtn -> ARouter.getInstance().build(RouterPath.OrderCenter.PATH_ORDER_PAY)
                    .withString(BaseConstant.KEY_MEAL_ORDER_ID, mOrderId)
                    .withString(BaseConstant.KEY_PAY_AMOUNT, if (mOrderDetails.earnestPay) {
                        (mOrderDetails.amount.toDouble() - mOrderDetails.earnestMoney.toDouble()).toString()
                    } else "1000")
                    .withString(BaseConstant.KEY_PAY_FROM, getString(R.string.meal_destine))
                    .navigation()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            ProvideReqCode.CODE_RESULT_BRIDE_INFO -> { //添加用户信息返回
                val mBrideInfo = data!!.getParcelableExtra<BrideInfo>(BaseConstant.KEY_BRIDE_INFO)
                with(mBrideInfo) {
                    mBridegroomTv.text = bridegroom
                    mBrideTv.text = bride
                    mWeddingDayTv.text = weddingDate
                    mTimeTv.text = photoTime
                    mReceiveTv.text = recevice
                }
            }
            ProvideReqCode.CODE_RESULT_ADD_CAMERAMAN -> { //添加摄影师返回
                when (requestCode) {
                    1001 -> {
                        mCameramanTv.text = data?.getStringExtra(BaseConstant.KEY_ADD_CAMERAMAN)
                        mCameramanAmountTv.text = data?.getStringExtra(BaseConstant.KEY_ADD_CAMERAMAN_AMOUNT)
                        mCameramanIv.loadUrl(data?.getStringExtra(BaseConstant.KEY_ADD_CAMERAMAN_URL)!!)
                    }
                    1002 -> {
                        mDresserTv.text = data?.getStringExtra(BaseConstant.KEY_ADD_CAMERAMAN)
                        mDresserAmountTv.text = data?.getStringExtra(BaseConstant.KEY_ADD_CAMERAMAN_AMOUNT)
                        mDresserIv.loadUrl(data?.getStringExtra(BaseConstant.KEY_ADD_CAMERAMAN_URL)!!)
                    }
                }
            }
            ProvideReqCode.CODE_RESULT_DRESS_WOMEN -> { //添加女装返回
                mAppointDressAdapter.dataList.get(data!!.getIntExtra(BaseConstant.KEY_DRESS_POSITION, 0)).womenImage = data!!.getStringExtra(BaseConstant.KEY_DRESS_WOMEN_IMAGE)
                mAppointDressAdapter.notifyDataSetChanged()
            }
            ProvideReqCode.CODE_RESULT_DRESS_MAN -> { //添加男装返回
                mAppointDressAdapter.dataList.get(data!!.getIntExtra(BaseConstant.KEY_DRESS_POSITION, 0)).manImage = data!!.getStringExtra(BaseConstant.KEY_DRESS_MAN_IMAGE)
                mAppointDressAdapter.notifyDataSetChanged()
            }
            ProvideReqCode.CODE_RESULT_FEATURE -> { //添加景点返回
                mAppointFeatureSpotAdapter.dataList.get(data!!.getIntExtra(BaseConstant.KEY_DRESS_POSITION, 0)).name = data!!.getStringExtra(BaseConstant.KEY_SCENIC_NAME)
                mAppointFeatureSpotAdapter.dataList.get(data!!.getIntExtra(BaseConstant.KEY_DRESS_POSITION, 0)).amount = data!!.getStringExtra(BaseConstant.KEY_SCENIC_AMOUNT)
                mAppointFeatureSpotAdapter.dataList.get(data!!.getIntExtra(BaseConstant.KEY_DRESS_POSITION, 0)).image = data!!.getStringExtra(BaseConstant.KEY_SCENIC_IMAGE)
                mAppointFeatureSpotAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    override fun onOrderSuccess(result: OrderDetails) {
    }

    override fun onFollowSuccess(result: Boolean) {
    }

    override fun onGetBasicServicesSuccess(result: MutableList<BasicServices>) {
    }

    override fun onGetMealDetailsSuccess(result: SetMealDetails) {
    }
    override fun onGetEvaluateListSuccess(result: BasePagingResp<MutableList<Evaluate>>) {

    }
}