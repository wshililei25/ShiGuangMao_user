package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DressStore(
    val attention: Boolean,
    val city: String,
    val content: String,
    val createTime: String,
    val detail: String,
    val hot: Boolean,
    val id: Int,
    val imgurl: String,
    val lat: Double,
    val lng: Double,
    val loginUid: String,
    val province: String,
    val redMax: Int,
    val redMin: Int,
    val serviceCount: Int,
    val starCount: Int,
    val storeName: String,
    val tel: String,
    val uid: Int
) : Parcelable
