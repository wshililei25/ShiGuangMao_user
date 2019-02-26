package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/8/18.
 */
@Parcelize
class RelevanceUser(
        val id: String,
        val createTime: String,
        val imgurl: String,
        val nickname: String,
        val relatedUid: String,
        val uid: String
) : Parcelable