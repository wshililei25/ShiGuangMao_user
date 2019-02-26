package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/12/4.
 */
@Parcelize
class Store(
        val id: String,
        val uid: String,
        val storeName: String,
        val imgurl: String,
        val province: String,
        val city: String,
        val detail: String,
        val content: String,
        val createTime: String,
        val lng: String,
        val lat: String,
        val starCount: Int,
        val serviceCount: String,
        val tel: String,
        val attention: Boolean,
        val loginUid: String? = null,
        val hot: Boolean,
        val redMin: String,
        val redMax: String

) : Parcelable