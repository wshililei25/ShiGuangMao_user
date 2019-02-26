package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class AliPayResultTrade(
    val app_id: String,
    val auth_app_id: String,
    val charset: String,
    val code: String,
    val msg: String,
    val out_trade_no: String,
    val seller_id: String,
    val timestamp: String,
    val total_amount: String,
    val trade_no: String
) : Parcelable
