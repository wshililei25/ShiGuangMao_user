package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GeneralizeCollectMax(
        val id: String,
        val name: String,
        val imgurl: String,
        val date: String,
        val amount: String,
        val type: String

) : Parcelable
