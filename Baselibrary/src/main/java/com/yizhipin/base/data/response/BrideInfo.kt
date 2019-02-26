package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BrideInfo(
    val bride: String,
    val bridegroom: String,
    val duan: String?=null,
    val id: String,
    val orderId: String,
    val photoTime: String,
    val recevice: String,
    val weddingDate: String
) : Parcelable


