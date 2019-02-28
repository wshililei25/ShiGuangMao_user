package com.shiguangmao.sgm.wxapi


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.common.WechatAppID
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.paycenter.R

/**
 * 微信支付回调页面
 * 所在包名必须和applicationId一致，否则无法获取回调
 */
class WXPayEntryActivity : Activity(), IWXAPIEventHandler {

    private lateinit var mIWXAPI: IWXAPI

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pay_result)

        mIWXAPI = WXAPIFactory.createWXAPI(this, null)
        when (AppPrefsUtils.getString(BaseConstant.KEY_SHOP_NAME)) {
            "临汾店" -> mIWXAPI.registerApp(WechatAppID.LINFEN)
            "三亚店" -> mIWXAPI.registerApp(WechatAppID.SANYA)
        }
        mIWXAPI.handleIntent(intent, this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        mIWXAPI.handleIntent(intent, this)
    }

    override fun onReq(req: BaseReq) {
        Log.d("XiLei", "sddsdsdsdsds = $req")
    }

    override fun onResp(resp: BaseResp) {
        Log.d("XiLei", "resp = " + resp.toString())
        Log.d("XiLei", "onPayFinish, errCode = " + resp.errCode)

        if (resp.type == ConstantsAPI.COMMAND_PAY_BY_WX) {

            when (resp.errCode) {
                0 -> finish()
                -1 -> showAlert(this@WXPayEntryActivity, getString(R.string.pay_failed))
                -2 -> showAlert(this@WXPayEntryActivity, getString(R.string.pay_cancel))
            }
        }
    }

    private fun showAlert(ctx: Context, info: String) {
        showAlert(ctx, info, null)
    }

    private fun showAlert(ctx: Context, info: String, onDismiss: DialogInterface.OnDismissListener?) {
        AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton(R.string.confirm, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        finish()
                    }
                })
                .setOnDismissListener(onDismiss)
                .show()
    }
}