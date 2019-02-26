package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DressNorm(
        val id: Int,
        val clothId: Int,
        val norm: String,
        val items: List<DressNormItem>
) : Parcelable
