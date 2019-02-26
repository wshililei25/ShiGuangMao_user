package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddCameraman(
        val extraAmount: String,
        val id: String,
        val imgurl: String? = null,
        val nickname: String? = null,
        val orderId: String,
        val teacherAmount: String,
        val teacherId: String,
        val teacherType: String
) : Parcelable
