package com.yizhipin.ordercender.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.ShipAddress
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.base.widgets.AddressPickerView
import com.yizhipin.ordercender.R
import com.yizhipin.ordercender.common.OrderConstant
import com.yizhipin.ordercender.injection.component.DaggerShipAddressComponent
import com.yizhipin.ordercender.injection.module.ShipAddressModule
import com.yizhipin.ordercender.presenter.EditShipAddressPresenter
import com.yizhipin.ordercender.presenter.view.EditShipAddressView
import kotlinx.android.synthetic.main.activity_edit_address.*
import org.jetbrains.anko.toast

/**
 * Created by ${XiLei} on 2018/9/24.
 */
class ShipAddressEditActivity : BaseMvpActivity<EditShipAddressPresenter>(), EditShipAddressView, View.OnClickListener {

    @Autowired(name = OrderConstant.KEY_SHIP_ADDRESS) //注解接收其他页面的传参
    @JvmField
    var mAddress: ShipAddress? = null

    private var mProvice = ""
    private var mCity = ""
    private var mDistrict = ""
    private var mPopupWindow: PopupWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_address)

        initView()
        initData()
    }

    private fun initView() {
        mSaveBtn.onClick(this)
        mCustomBtn.onClick(this)
        mCityAddressView.onClick(this)
    }

    private fun initData() {
        mAddress?.let {
            mProvice = it.province
            mCity = it.city
            mDistrict = it.area
            mShipNameEt.setText(it.name)
            mShipMobileEt.setText(it.mobile)
            mCityAddressEt.setText(it.province + it.city + it.area)
            mShipAddressEt.setText(it.detail)
            mShipNameEt.setSelection(mShipNameEt.text.toString().length)
        }
    }

    override fun injectComponent() {
        DaggerShipAddressComponent.builder().activityComponent(mActivityComponent).shipAddressModule(ShipAddressModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mCustomBtn -> custom()
            R.id.mCityAddressView -> {
                mPopupWindow = PopupWindow(this)
                val rootView = LayoutInflater.from(this).inflate(R.layout.pop_address_picker, null, false)
                val addressView = rootView.findViewById<AddressPickerView>(R.id.apvAddress)
                addressView.setOnAddressPickerSure(object : AddressPickerView.OnAddressPickerSureListener {
                    override fun onSureClick(provice: String?, city: String?, district: String?, provinceCode: String?, cityCode: String?, districtCode: String?) {
                        mCityAddressEt.setText(provice + city + district)
                        mProvice = provice!!
                        mCity = city!!
                        mDistrict = district!!
                        mPopupWindow!!.dismiss()
                    }
                })
                mPopupWindow!!.setContentView(rootView)
                mPopupWindow!!.setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                mPopupWindow!!.setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                mPopupWindow!!.isOutsideTouchable = true
                mPopupWindow!!.isTouchable = true
                mPopupWindow!!.showAsDropDown(line)
            }

            R.id.mSaveBtn -> {
                if (mShipNameEt.text.isNullOrEmpty() || mShipMobileEt.text.isNullOrEmpty() || mShipAddressEt.text.isNullOrEmpty()) {
                    toast("内容输入不能为空")
                    return@onClick
                }

                var map = mutableMapOf<String, String>()
                map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))

                if (mAddress == null) {
                    map.put("name", mShipNameEt.text.toString())
                    map.put("mobile", mShipMobileEt.text.toString())
                    map.put("province", mProvice)
                    map.put("city", mCity)
                    map.put("area", mDistrict)
                    map.put("detail", mShipAddressEt.text.toString())
                    if (intent.getIntExtra("size", 0) == 0) {
                        map.put("isDefault", "true")
                    } else {
                        map.put("isDefault", "false")
                    }
                    mBasePresenter.addShipAddress(map)
                } else {
                    map.put("id", mAddress!!.id.toString())
                    map.put("province", mProvice)
                    map.put("city", mCity)
                    map.put("area", mDistrict)
                    map.put("detail", mShipAddressEt.text.toString())
                    map.put("name", mShipNameEt.text.toString())
                    map.put("mobile", mShipMobileEt.text.toString())
                    map.put("isDefault", mAddress!!.isDefault.toString())

                    mBasePresenter.editShipAddress(map)
                }
            }
        }
    }

    override fun onAddShipAddressResult(result: ShipAddress) {
        finish()
    }

    override fun onEditShipAddressResult(result: ShipAddress) {
        finish()
    }

    override fun onBackPressed() {
        if (null != mPopupWindow && mPopupWindow!!.isShowing) {
            mPopupWindow!!.dismiss()
        } else {
            finish()
        }
    }
}