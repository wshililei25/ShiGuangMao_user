package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TimeMarketNormItem(
        val id: String,
        val imgurl: String,
        val inventory: String,
        val item: String,
        val normId: String,
        val price: String
) : Parcelable
