package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/8/18.
 */
@Parcelize
class RedPacket(
        val amount: Double,
        val fromUid: Int,
        val getTime: String,
        val id: Int,
        val reason: String,
        val shopId: Int,
        val uid: Int
) : Parcelable