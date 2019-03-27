package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeacherWorks(
        val id: String,
        val uid: String,
        val address: String,
        val imgurls: String
) : Parcelable
