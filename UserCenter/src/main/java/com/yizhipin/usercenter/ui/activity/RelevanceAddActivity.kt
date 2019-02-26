package com.yizhipin.usercenter.ui.activity

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.RelevanceUser
import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.ext.enable
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.usercenter.R
import com.yizhipin.usercenter.injection.component.DaggerUserComponent
import com.yizhipin.usercenter.injection.module.UserModule
import com.yizhipin.usercenter.presenter.RelevancePresenter
import com.yizhipin.usercenter.presenter.view.RelevanceView
import kotlinx.android.synthetic.main.activity_relevance_add.*


/**
 * Created by ${XiLei} on 2018/8/5.
 * 添加关联用户
 */
class RelevanceAddActivity : BaseMvpActivity<RelevancePresenter>(), RelevanceView, View.OnClickListener {

    @Autowired(name = BaseConstant.KEY_IS_ADD) //注解接收上个页面的传参
    @JvmField
    var mIsAdd: Boolean = false

    @Autowired(name = BaseConstant.KEY_RELEVANCE_ID) //注解接收上个页面的传参
    @JvmField
    var mRelevanceId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relevance_add)

        initView()
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initView() {
        mBtn.onClick(this)
        mBtn.enable(mEt, { isBtnEnable() })
    }

    private fun isBtnEnable(): Boolean {
        return mEt.text.isNullOrEmpty().not()
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.mBtn -> {
                if (mIsAdd) {
                    var map = mutableMapOf<String, String>()
                    map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN))
                    map.put("mobile", mEt.text.toString())
                    mBasePresenter.addRelevanceUser(map)
                } else {
                    var map = mutableMapOf<String, String>()
                    map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN))
                    map.put("id", mRelevanceId)
                    map.put("mobile", mEt.text.toString())
                    mBasePresenter.updateRelevanceUser(map)
                }

            }
        }
    }

    override fun addRelevanceUserSuccess(result: RelevanceUser) {
        finish()
    }

    override fun getRelevanceUserSuccess(result: MutableList<RelevanceUser>) {
    }

    override fun getUserSuccess(result: UserInfo) {
    }

    override fun deleteRelevanceUserSuccess(result: Boolean) {
    }
}