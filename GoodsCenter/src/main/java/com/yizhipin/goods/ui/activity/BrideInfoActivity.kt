package com.yizhipin.goods.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.codbking.widget.DatePickDialog
import com.codbking.widget.OnSureLisener
import com.codbking.widget.bean.DateType
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.BrideInfo
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.utils.DateUtils
import com.yizhipin.goods.R
import com.yizhipin.goods.injection.component.DaggerGoodsComponent
import com.yizhipin.goods.injection.module.GoodsModule
import com.yizhipin.goods.presenter.BrideInfoPresenter
import com.yizhipin.goods.presenter.view.BrideInfoView
import com.yizhipin.provider.common.ProvideReqCode
import kotlinx.android.synthetic.main.activity_bride_info.*
import org.jetbrains.anko.toast
import java.util.*

/**
 * Created by ${XiLei} on 2018/9/24.
 * 新人信息编辑
 */
class BrideInfoActivity : BaseMvpActivity<BrideInfoPresenter>(), BrideInfoView, View.OnClickListener {

    @Autowired(name = BaseConstant.KEY_MEAL_ORDER_ID)
    @JvmField
    var mOrderId: String = "" //订单id

    private lateinit var mDialog: DatePickDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bride_info)

        initView()
        initDialog()
    }

    private fun initView() {
        mSaveBtn.onClick(this)
        mShootTimeView.onClick(this)
        mWeddingDayView.onClick(this)
    }

    private fun initDialog() {
        mDialog = DatePickDialog(this)
        //设置上下年分限制
        mDialog.setYearLimt(5)
        //设置标题
//            mDialog.setTitle("选择时间")
        //设置类型
        mDialog.setType(DateType.TYPE_YMD)
        //设置消息体的显示格式，日期格式
        mDialog.setMessageFormat(DateUtils.FORMAT_SHORT_CN)
        //设置选择回调
        mDialog.setOnChangeLisener(null)
    }

    override fun injectComponent() {
        DaggerGoodsComponent.builder().activityComponent(mActivityComponent).goodsModule(GoodsModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.mShootTimeView -> {
                //设置点击确定按钮回调
                mDialog.setOnSureLisener(object : OnSureLisener {
                    override fun onSure(d: Date) {
                        mShootTimeTv.text = DateUtils.format(d, DateUtils.FORMAT_SHORT)
                    }

                })
                mDialog.show()
            }

            R.id.mWeddingDayView -> {
                //设置点击确定按钮回调
                mDialog.setOnSureLisener(object : OnSureLisener {
                    override fun onSure(d: Date) {
                        mWeddingDayTv.text = DateUtils.format(d, DateUtils.FORMAT_SHORT)
                    }

                })
                mDialog.show()
            }

            R.id.mSaveBtn -> {
                if (mShipNameEt.text.isNullOrEmpty() || mShipMobileEt.text.isNullOrEmpty() || mShootTimeTv.text.isNullOrEmpty()) {
                    toast("内容输入不能为空")
                    return@onClick
                }

                var map = mutableMapOf<String, String>()
                map.put("orderId", mOrderId)
                map.put("bridegroom", mShipNameEt.text.toString())
                map.put("bride", mShipMobileEt.text.toString())
                map.put("photoTime", mShootTimeTv.text.toString())
                map.put("weddingDate", mWeddingDayTv.text.toString())
                map.put("recevice", mShopReceiveTv.text.toString())
                map.put("duan", "0")
                mBasePresenter.addBrideInfo(map)
            }
        }
    }

    override fun onAddBrideInfoSuccess(result: BrideInfo) {
        var intent = Intent()
        intent.putExtra(BaseConstant.KEY_BRIDE_INFO, result)
        setResult(ProvideReqCode.CODE_RESULT_BRIDE_INFO, intent)
        finish()
    }

}