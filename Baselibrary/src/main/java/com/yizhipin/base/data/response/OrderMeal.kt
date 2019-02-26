package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/10/4.
 */
@Parcelize
class OrderMeal(
    val id: String,
    val orderId: String,
    val packageAmount: String,
    val packageId: String,
    val packageImgurl: String,
    val packagePhotoType: String,
    val packageTitle: String,
    val packageType: String,
    val shopId: String,
    val shopName: String
) : Parcelable