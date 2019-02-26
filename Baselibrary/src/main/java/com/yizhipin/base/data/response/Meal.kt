package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/10/4.
 */
@Parcelize
class Meal(
        val attention: Boolean,
        val clothCount: String,
        val createTime: String,
        val evaStar: String,
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
        val type: String,
        val content: String
) : Parcelable