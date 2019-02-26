package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TimeSuperMarket(
    val amount: String,
    val attention: Boolean,
    val catagory: String,
    val hot: Boolean,
    val id: String,
    val imgurl: String,
    val imgurls: String,
//    val loginUid: String,
    val markerPrice: String,
    val postage: String,
    val recommend: Boolean,
    val sellCount: String,
    val sendtype: String,
    val starCount: String,
    val storeId: String,
    val content: String,
    val title: String,
    val store: Store,
    val norms: MutableList<TimeMarketNorm>
) : Parcelable
