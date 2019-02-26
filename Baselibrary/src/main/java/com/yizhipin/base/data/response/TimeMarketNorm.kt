package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TimeMarketNorm(
        val id: Int,
        val marketId: Int,
        val norm: String,
        val items: MutableList<TimeMarketNormItem>
) : Parcelable
