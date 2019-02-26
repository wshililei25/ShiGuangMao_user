package com.yizhipin.goods.ui.activity

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.yizhipin.base.common.BaseApplication.Companion.context
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Integral
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.goods.R
import com.yizhipin.goods.injection.component.DaggerGoodsComponent
import com.yizhipin.goods.injection.module.GoodsModule
import com.yizhipin.goods.presenter.IntegralPresenter
import com.yizhipin.goods.presenter.view.IntegralView
import kotlinx.android.synthetic.main.activity_integral_details.*

/**
 * Created by ${XiLei} on 2018/9/22.
 * 积分商城详情
 */
class IntegralDetailActivity : BaseMvpActivity<IntegralPresenter>(), IntegralView, View.OnClickListener {

    @Autowired(name = BaseConstant.KEY_INTEGRAL_ID)
    @JvmField
    var mIntegralId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_integral_details)

        initView()
        loadGoodDetailsData()
    }

    override fun injectComponent() {
        DaggerGoodsComponent.builder().activityComponent(mActivityComponent).goodsModule(GoodsModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initView() {

        mAmountTv.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        mAmountTv.paint.isAntiAlias = true

        mBackIv.onClick { finish() }
        mBuyBtn.onClick(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mBackIv -> finish()
        }
    }

    private fun loadGoodDetailsData() {
        var map = mutableMapOf<String, String>()
        map.put("id", mIntegralId)
        mBasePresenter.getIntegralDetail(map)
    }

    override fun onGetIntegralDetailSuccess(result: Integral) {
        with(result) {
            mGoodName.text = title
            mIntroductionTv.text = introduction
            mIntegralTv.text = score
            mAmountTv.text = context.getString(R.string.rmb) + markerScore
            mNumTv.text = "已兑换${markerScore}次"
            mIntegralBotTv.text = score

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
        }

    }

    override fun onGetIntegralListSuccess(result: BasePagingResp<MutableList<Integral>>) {
    }
}