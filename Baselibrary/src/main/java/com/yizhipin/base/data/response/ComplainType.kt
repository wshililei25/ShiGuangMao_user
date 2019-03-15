package com.yizhipin.usercenter.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/8/18.
 */
@Parcelize
class ComplainType(
        val type: String,
        val name: String
) : Parcelable