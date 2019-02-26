package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/12/4.
 */
@Parcelize
class ShopDetails(
        val attention: Boolean,
        val city: String,
        val content: String,
        val createTime: String,
        val detail: String,
        val hot: Boolean,
        val id: String,
        val imgurl: String,
        val lat: String,
        val lng: String,
        val loginUid: String,
        val province: String,
        val redMax: String,
        val redMin: String,
        val serviceCount: String,
        val starCount: Int,
        val storeName: String,
        val tel: String,
        val uid: String
) : Parcelable