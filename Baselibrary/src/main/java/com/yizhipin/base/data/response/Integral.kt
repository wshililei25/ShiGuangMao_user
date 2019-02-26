package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/10/4.
 */
@Parcelize
class Integral(
        val content: String,
        val exchangeCount: String,
        val id: String,
        val imgurl: String,
        val imgurls: String,
        val Stringroduction: String,
        val markerScore: String,
        val postage: Double,
        val score: String,
        val storeId: String,
        val storeName: String,
        val introduction: String,
        val title: String
) : Parcelable