package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 优惠券
 */
@Parcelize
data class Coupon(
        val amount: Int,
        val endTime: String,
        val getTime: String,
        val id: Int,
        val minAmount: Int,
        val uid: Int,
        val useType: String,
        val used: Boolean,
        val valid: Boolean
) : Parcelable
