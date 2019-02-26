package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CameramanWorks(
    val address: String,
    val id: String,
    val imgurls: String,
    val uid: String
) : Parcelable
