package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/12/4.
 */
@Parcelize
class ScenicSpot(
        val id: String,
        val title: String,
        val imgurl: String,
        val imgurls: String,
        val sellCount: String,
        val starCount: String,
        val amount: String,
        val markerPrice: String,
        val content: String,
        val storeId: String,
        val address: String,
        val hot: Boolean,
        val attention: Boolean,
        val loginUid: String? = null,
        val store: Store
) : Parcelable