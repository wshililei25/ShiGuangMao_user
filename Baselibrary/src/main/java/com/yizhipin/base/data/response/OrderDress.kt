package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/10/4.
 */
@Parcelize
class OrderDress(
    val back: Boolean,
    val clothesAmount: String,
    val clothesId: String,
    val clothesImgurl: String,
    val clothesTitle: String,
    val id: String,
    val orderId: String,
    val sex: Int,
    val success: Boolean
) : Parcelable