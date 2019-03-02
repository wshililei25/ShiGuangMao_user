package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*
   收货地址
 */
@Parcelize
data class ShipAddress(
        val area: String,
        val city: String,
        val detail: String,
        val id: Int,
        var isDefault: Boolean,
        val mobile: String,
        val name: String,
        val province: String,
        val uid: Int
) : Parcelable
