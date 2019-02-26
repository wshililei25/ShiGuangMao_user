package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CloudDisk(
        val createTime: String,
        val folder: String,
        val id: String,
        val pid: String,
        val type: String,
        val uid: String

) : Parcelable
