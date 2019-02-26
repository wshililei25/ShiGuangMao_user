package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CloudDiskImage(
    val bucketName: String,
    val fileName: String,
    val fileSize: String,
    val folderId: String,
    val id: String,
    val key: String,
    val lastUpdateTime: String,
    val note: String
) : Parcelable
