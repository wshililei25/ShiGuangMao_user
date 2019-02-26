package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/10/4.
 */
@Parcelize
class Comment(
        val content: String,
        val evaId: Int,
        val evaImgurl: String,
        val evaNickname: String,
        val evaTime: String,
        val evaUid: Int,
        val id: Int,
        val imgurl: String,
        val interactiveId: Int,
        val nickname: String,
        val uid: Int
) : Parcelable