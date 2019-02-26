package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DressDetails(
        val amount: Int,
        val attention: Boolean,
        val catagory: Int,
        val hot: Boolean,
        val id: Int,
        val imgurl: String,
        val imgurls: String,
        val loginUid: String? = null,
        val markerPrice: Int,
        val paiAmount: Int,
        val content: String,
        val postage: Int,
        val sellCount: Int,
        val sellType: Int,
        val sendtype: String,
        val sex: Int,
        val starCount: Int,
        val storeId: Int,
        val title: String,
        val store: DressStore,
        val norms: List<DressNorm>,
        var buyCount: Int //购买数量
) : Parcelable
