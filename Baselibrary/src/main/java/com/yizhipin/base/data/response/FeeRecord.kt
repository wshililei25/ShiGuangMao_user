package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/8/18.
 */
@Parcelize
class FeeRecord(
        val amount: Double,
        val createTime: String,
        val externalId: Int,
        val id: Int,
        val income: Boolean,
        val remark: String,
        val title: String,
        val type: String,
        val uid: Int
) : Parcelable