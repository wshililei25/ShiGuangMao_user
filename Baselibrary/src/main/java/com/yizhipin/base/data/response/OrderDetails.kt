package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class OrderDetails(
        val amount: String,
        val amountCoupon: String,
        val couponAmount: String,
        val createTime: String,
        val earnestCoupon: String,
        val earnestMoney: String,
        val earnestOrdernum: String,
        val earnestPay: Boolean,
        val groupId: String,
        val id: String,
        val imgurl: String,
        val ordernum: String,
        val status: String,
        val storeId: String,
        val title: String,
        val type: String,
        val uid: String,
        val packages: MutableList<OrderMeal>
) : Parcelable


