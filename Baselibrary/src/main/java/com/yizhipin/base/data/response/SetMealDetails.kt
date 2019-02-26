package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SetMealDetails(
        val attention: Boolean,
        val clothCount: String,
        val createTime: String,
        val evaStar: Int,
        val filmCount: String,
        val frontMoney: String,
        val id: String,
        val imgurl: String,
        val imgurls: String,
        val loginUid: String,
        val marketPrice: String,
        val packagePhotoType: String,
        val packageType: String,
        val price: String,
        val rucheCount: String,
        val sellCount: String,
        val storeId: String,
        val storeImgurl: String,
        val storeName: String,
        val tel: String,
        val title: String,
        val content: String,
        val type: String
) : Parcelable


