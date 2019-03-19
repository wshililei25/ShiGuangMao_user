package com.yizhipin.paycenter.ui.activity

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.ui.activity.BaseActivity
import com.yizhipin.paycenter.R
import com.yizhipin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_withdraw.*

/**
 * Created by ${XiLei} on 2018/9/24.
 *
 * 提现、退押金
 */
@Route(path = RouterPath.PayCenter.PATH_PAY_WITHDRAW)
class WithdrawActivity : BaseActivity(), View.OnClickListener {

    @Autowired(name = BaseConstant.KEY_IS_CASH)
    @JvmField
    var mIsCash: Boolean = false //是提现还是退押金

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdraw)

        initView()
    }

    private fun initView() {
        if (mIsCash) {
            mHeaderBar.getTiTleTv().text = getString(R.string.reback_recharge)
            mNameTv.text = getString(R.string.reback_amount)
            mAllTv.text = getString(R.string.reback_all)
            mChannelTv.text = getString(R.string.reback_channel)
        }
    }


    override fun onClick(v: View) {
        when (v.id) {
//            R.id.mRechargeTv ->
        }
    }


}