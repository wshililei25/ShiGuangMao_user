package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/10/4.
 */
@Parcelize
class InteractionDetailsEva(
        val content: String,
        val evaId: String,
        val evaImgurl: String,
        val evaNickname: String,
        val evaTime: String,
        val evaUid: String,
        val id: String,
        val imgurl: String,
        val interactiveId: String,
        val nickname: String,
        val uid: String
) : Parcelable