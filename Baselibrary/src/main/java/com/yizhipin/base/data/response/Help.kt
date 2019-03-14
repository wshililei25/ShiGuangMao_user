package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/12/4.
 */
@Parcelize
class Help(
        val id: String,
        val title: String,
        val content: String,
        val type: String
) : Parcelable