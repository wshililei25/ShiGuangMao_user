package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/10/4.
 */
@Parcelize
class Evaluate(
        val content: String,
        val evaTime: String,
        val id: String,
        val imgurl: String,
        val orderId: String,
        val packageId: String,
        val starCount: String,
        val storeId: String,
        val uid: String,
        val webUser: UserInfo
) : Parcelable