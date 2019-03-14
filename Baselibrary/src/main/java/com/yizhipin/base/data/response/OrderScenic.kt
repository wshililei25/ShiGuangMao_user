package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/10/4.
 */
@Parcelize
class OrderScenic(
    val attractionsAmount: String,
    val attractionsId: String,
    val attractionsImgurl: String,
    val attractionsTitle: String,
    val id: String,
    val orderId: String
) : Parcelable