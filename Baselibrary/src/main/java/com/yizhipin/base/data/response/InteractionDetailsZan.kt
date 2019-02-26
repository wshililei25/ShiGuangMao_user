package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/10/4.
 */
@Parcelize
class InteractionDetailsZan(
        val id: String,
        val imgurl: String,
        val interactiveId: String,
        val nickname: String,
        val uid: String
) : Parcelable