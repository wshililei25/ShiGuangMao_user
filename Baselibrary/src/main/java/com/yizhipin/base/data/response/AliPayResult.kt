package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class AliPayResult(
        val alipay_trade_app_pay_response: AliPayResultTrade,
        val sign: String,
        val sign_type: String
) : Parcelable
