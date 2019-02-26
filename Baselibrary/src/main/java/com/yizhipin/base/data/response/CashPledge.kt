package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CashPledge(
        val total: String,
        val available: String
) : Parcelable
