package com.yizhipin.usercenter.ui.activity

import android.os.Bundle
import android.view.View
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.Complain
import com.yizhipin.base.event.ComplainTypeCheckedEvent
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.ui.dialog.ComplainTypeDialog
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.usercenter.R
import com.yizhipin.usercenter.injection.component.DaggerUserComponent
import com.yizhipin.usercenter.injection.module.UserModule
import com.yizhipin.usercenter.presenter.ComplainPresenter
import com.yizhipin.usercenter.presenter.ComplainView
import kotlinx.android.synthetic.main.activity_complain.*
import org.jetbrains.anko.toast

/**
 * Created by ${XiLei} on 2018/8/5.
 * 投诉建议
 */
class ComplainActivity : BaseMvpActivity<ComplainPresenter>(), ComplainView, View.OnClickListener {

    private var mType = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complain)
        initView()
        initObserve()
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initView() {
        mTypeView.onClick(this)
        mBtn.onClick(this)
        mCustomBtn.onClick(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mCustomBtn -> custom()
            R.id.mTypeView -> {
                var customDialog = ComplainTypeDialog(this)
                customDialog.show()
            }

            R.id.mBtn -> {
                if (mTypeEt.text.toString().isNullOrEmpty() || mNameEt.text.toString().isNullOrEmpty() || mMobileEt.text.toString().isNullOrEmpty()
                        || mContentEt.text.toString().isNullOrEmpty()) {
                    toast("输入内容不完整")
                    return
                }

                var map = mutableMapOf<String, String>()
                map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
                map.put("questionType", mTypeEt.text.toString())
                map.put("name", mNameEt.text.toString())
                map.put("mobile", mMobileEt.text.toString())
                map.put("content", mContentEt.text.toString())
                mBasePresenter.complain(map)
            }
        }
    }

    override fun onComplainViewSuccess(result: Complain) {
        toast("提交成功")
        finish()
    }

    private fun initObserve() {
        Bus.observe<ComplainTypeCheckedEvent>()
                .subscribe { t: ComplainTypeCheckedEvent ->
                    run {
                        mType = t.complainType.type
                        mTypeEt.setText(t.complainType.name)
                    }
                }.registerInBus(this)
    }

}