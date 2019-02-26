package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DressNormItem(
        val id: Int,
        val imgurl: String,
        val inventory: Int,
        val item: String,
        val normId: Int,
        val price: Int
) : Parcelable
