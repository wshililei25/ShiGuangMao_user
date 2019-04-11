package com.yizhipin.usercenter.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.FeeRecord
import com.yizhipin.base.data.response.OssAddress
import com.yizhipin.base.data.response.Store
import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseTakePhotoActivity
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.provider.router.RouterPath
import com.yizhipin.usercenter.R
import com.yizhipin.usercenter.injection.component.DaggerUserComponent
import com.yizhipin.usercenter.injection.module.UserModule
import com.yizhipin.usercenter.presenter.UserInfoPresenter
import com.yizhipin.usercenter.presenter.view.UserInfoView
import com.yizhipin.usercenter.ui.adapter.FeeRecordAdapter
import kotlinx.android.synthetic.main.activity_wallet.*

/**
 * Created by ${XiLei} on 2018/9/24.
 *
 * 余额
 */
@Route(path = RouterPath.UserCenter.BALANCE)
class WalletActivity : BaseTakePhotoActivity<UserInfoPresenter>(), UserInfoView, View.OnClickListener {

    private lateinit var mFeeRecordAdapter: FeeRecordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        initView()
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    private fun initView() {
        mRechargeTv.onClick(this)
        mWithdrawTv.onClick(this)
        mCustomBtn.onClick(this)

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mFeeRecordAdapter = FeeRecordAdapter(this)
        mRecyclerView.adapter = mFeeRecordAdapter
    }

    override fun onStart() {
        super.onStart()
        loadBalance()
        loadFeeRecordList()
    }

    private fun loadBalance() {
        var map = mutableMapOf<String, String>()
        map.put("id", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mPresenter.getUserInfo(map)
    }

    /**
     * 获取用户信息成功
     */
    override fun getUserResult(result: UserInfo) {
        mAmountTv.text = getString(R.string.rmb).plus(result.amount)
    }

    /**
     * 资金记录
     */
    private fun loadFeeRecordList() {
        var map = mutableMapOf<String, String>()
        map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mPresenter.loadFeeRecordList(map)
    }

    /**
     * 获取资金记录成功
     */
    override fun getFeeRecordListSuccess(result: MutableList<FeeRecord>) {
        mFeeRecordAdapter.setData(result)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mCustomBtn -> custom()
            R.id.mRechargeTv -> ARouter.getInstance().build(RouterPath.PayCenter.PATH_PAY_RECHARGE).navigation()
            R.id.mWithdrawTv -> ARouter.getInstance().build(RouterPath.PayCenter.PATH_PAY_WITHDRAW)
                    .withBoolean(BaseConstant.KEY_IS_CASH, false).navigation()
        }
    }

    override fun getUnReadNewCount(result: Int) {
    }

    override fun onEditUserResult(result: UserInfo) {
    }

    override fun onGetDefaultStoreSuccess(result: Store) {
    }

    override fun onGetOssSignSuccess(result: String) {
    }

    override fun onGetOssSignFileSuccess(result: String) {
    }

    override fun onGetOssAddressSuccess(result: OssAddress) {
    }
}