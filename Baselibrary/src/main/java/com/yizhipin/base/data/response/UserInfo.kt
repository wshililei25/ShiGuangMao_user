package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/8/18.
 * 用户实体类
 */
@Parcelize
class UserInfo(
        val alipay: String,
        val amount: Double,
        val authCode: String? = null,
        val cardAfter: String,
        val cardBefore: String,
        val credit: String,
        val deposit: String,
        val deviceToken: String,
        val deviceType: String,
        val extraAmount: String,
        val freezeAmount: String,
        val hot: Boolean,
        val id: String,
        val idCard: String,
        val imgurl: String,
        val lastLogStringime: String,
        val level: String,
        val mobile: String,
        val nickname: String,
        val openid: String,
        val password: String,
        val photoAmount: String,
        val pid: String,
        val position: String,
        val qqid: String,
        val realName: String,
        val redPrompt: String,
        val registerTime: String,
        val relatedUser: String,
        val requestCode: String,
        val score: String,
        val shopId: String,
        val storeName: String,
        val teacherType: String,
        val token: String? = null,
        val totalAmount: String,
        val totalDeposit: String,
        val type: String,
        val weiboid: String,
        val work: Boolean
) : Parcelable