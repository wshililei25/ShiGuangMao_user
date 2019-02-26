package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DressCategory(
        val id: Int,
        val name: String,
        val sex: Int,
        val type: Int
) : Parcelable
