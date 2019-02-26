package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Star(
        val stores: MutableList<Store>,
        val storeManagers: MutableList<Store>,
        val teachers: MutableList<Cameraman>
) : Parcelable
