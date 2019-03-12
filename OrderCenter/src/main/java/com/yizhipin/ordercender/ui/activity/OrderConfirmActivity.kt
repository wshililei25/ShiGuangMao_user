package com.yizhipin.ordercender.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.DressDetails
import com.yizhipin.base.data.response.ShipAddress
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ext.setVisible
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.base.utils.StringUtils
import com.yizhipin.base.widgets.DefaultTextWatcher
import com.yizhipin.base.widgets.NumberButton
import com.yizhipin.ordercender.R
import com.yizhipin.ordercender.event.SelectAddressEvent
import com.yizhipin.ordercender.injection.component.DaggerOrderComponent
import com.yizhipin.ordercender.injection.module.OrderModule
import com.yizhipin.ordercender.presenter.OrderConfirmPresenter
import com.yizhipin.ordercender.presenter.view.OrderConfirmView
import com.yizhipin.ordercender.ui.adapter.OrderConfirmAdapter
import com.yizhipin.provider.common.ProvideReqCode
import com.yizhipin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_order_confirm.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by ${XiLei} on 2018/9/24.
 * 订单确认(服装购买、服装租借)
 */

@Route(path = RouterPath.OrderCenter.PATH_ORDER_CONFIRM)
class OrderConfirmActivity : BaseMvpActivity<OrderConfirmPresenter>(), OrderConfirmView, View.OnClickListener {

    @Autowired(name = BaseConstant.KEY_IS_BUY) //注解接收上个页面的传参
    @JvmField
    var mIsBuy: Boolean = false  //是服装购买还是服装租借

    @Autowired(name = BaseConstant.KEY_DRESS_ID) //注解接收上个页面的传参
    @JvmField
    var mDressId: String = "" //服装id

    @Autowired(name = BaseConstant.KEY_DRESS_NORMS) //注解接收上个页面的传参
    @JvmField
    var mDressNorms: String = ""

    @Autowired(name = BaseConstant.KEY_DRESS_INVENTORY) //注解接收上个页面的传参
    @JvmField
    var mInventory = 0

    private lateinit var mDressDetails: DressDetails

    private var mShipAddress: ShipAddress? = null
    private lateinit var mOrderConfirmAdapter: OrderConfirmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirm)

        initView()
        loadData()
        loadGoodDetailsData()
        initObserve()
    }

    private fun initView() {

        mGoodsCountBtn.setBuyMax(mInventory)
        if (mIsBuy) mTypeTv.text = getString(R.string.dress_buy) else mTypeTv.text = getString(R.string.dress_hire)

        mBtn.onClick(this)
        mShipView.onClick(this)
        mSelectShipTv.onClick(this)

        mGoodsCountBtn.setOnWarnListener(object : NumberButton.OnWarnListener {
            override fun onWarningForBuyMax(max: Int) {
                toast("不能大于库存")
            }

            override fun onWarningForInventory(inventory: Int) {

            }

        })
        mGoodsCountBtn.getEditText().addTextChangedListener(object : DefaultTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //修复Bug，删除为空时异常
                if (s.isNullOrEmpty().not()) {
                    mDressDetails.buyCount = s.toString().toInt()
                    mRealityPriceTv.text = (mDressDetails.amount!! * mDressDetails.buyCount + mDressDetails.postage).toString()
                }

            }
        })
    }

    override fun injectComponent() {
        DaggerOrderComponent.builder().activityComponent(mActivityComponent).orderModule(OrderModule()).build().inject(this)
        mBasePresenter.mView = this
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

    override fun onGetGoodsDetailSuccess(result: DressDetails) {
        with(result) {
            mDressDetails = result

            mPriceTv.text = getString(R.string.rmb).plus(amount.toString())
            mGoodsNameTv.text = title
            mNormsTv.text = mDressNorms
            mPostageTv.text = getString(R.string.rmb).plus(postage)
            mRealityPriceTv.text = "${amount + postage}"

            store?.let {
                mGoodsIv.loadUrl(store.imgurl)
                mShopTv.text = store.storeName
            }
        }
    }

    /**
     * 获取默认地址
     */
    private fun loadData() {

        var map = mutableMapOf<String, String>()
        map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mBasePresenter.getDefaultAddress(map)
    }

    /**
     * 获取默认地址成功
     */
    override fun onGetDefaultAddressSuccess(result: ShipAddress) {

        updateAddressView(result)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mSelectShipTv, R.id.mShipView -> {
                startActivity<ShipAddressActivity>()
            }
            R.id.mBtn -> {
                if (null == mShipAddress) {
                    toast("请选择收货地址")
                    return
                }

                startActivity<PayConfirmActivity>(BaseConstant.KEY_PAY_FROM to if (mIsBuy) {
                    getString(R.string.dress_buy)
                } else {
                    getString(R.string.dress_hire)
                }
                        , BaseConstant.KEY_DRESS_ID to mDressId
                        , BaseConstant.KEY_PAY_AMOUNT to mRealityPriceTv.text.toString()
                        , BaseConstant.KEY_PAY_NUM to mGoodsCountBtn.editText.text.toString()
                        , BaseConstant.KEY_ADDRESS_ID to mShipAddress!!.id.toString()
                )
            }
        }
    }

    /**
     * 选择地址回调
     */
    private fun initObserve() {
        Bus.observe<SelectAddressEvent>()
                .subscribe { t: SelectAddressEvent ->
                    run {
                        updateAddressView(t.address)
                    }
                }.registerInBus(this)
    }

    private fun updateAddressView(result: ShipAddress) {

        mShipAddress = result
        if (result == null) {
            mSelectShipTv.setVisible(true)
            mShipView.setVisible(false)
        } else {
            mSelectShipTv.setVisible(false)
            mShipView.setVisible(true)

            mShipNameTv.text = result.name
            mShipMobileTv.text = StringUtils.setMobileStar(result.mobile)
            mShipAddressTv.text = result.province + result.city + result.area + result.detail
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            ProvideReqCode.CODE_RESULT_PAY -> finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}