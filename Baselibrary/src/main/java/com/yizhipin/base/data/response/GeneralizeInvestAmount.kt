package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GeneralizeInvestAmount(
        val month: String,
        val total: String
) : Parcelable
