package com.yizhipin.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.yizhipin.R
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.News
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.presenter.InformationDetailsPresenter
import com.yizhipin.presenter.view.InformationDetailsView
import com.yizhipin.usercenter.injection.component.DaggerMainComponent
import com.yizhipin.usercenter.injection.module.MianModule
import kotlinx.android.synthetic.main.activity_information_details.*

/**
 * Created by ${XiLei} on 2018/9/25.
 * 资讯详情
 */
class InformationDetailsActivity : BaseMvpActivity<InformationDetailsPresenter>(), InformationDetailsView {

    @Autowired(name = BaseConstant.KEY_INFORMATION_ID)
    @JvmField
    var mId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information_details)

        initView()
        loadData()
    }

    private fun initView() {
        mCustomBtn.onClick {
            custom()
        }
    }

    private fun loadData() {
        var map = mutableMapOf<String, String>()
        map.put("id", mId)
        mBasePresenter.getInformationDetails(map)
    }

    override fun injectComponent() {
        DaggerMainComponent.builder().activityComponent(mActivityComponent).mianModule(MianModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    override fun onGetInformationDetailsSuccess(result: News) {
        with(result) {
            mNameTv.text = title
            mDateTv.text = createTime
            mContenTv.text = content
            mIv.loadUrl(imgurl)
        }
    }

}