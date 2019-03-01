package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BuyResult(
        val payInfo: String
) : Parcelable
