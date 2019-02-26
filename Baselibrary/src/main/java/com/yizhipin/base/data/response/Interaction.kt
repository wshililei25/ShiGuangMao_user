package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/10/4.
 */
@Parcelize
class Interaction(
        val barType: String,
        val content: String,
        var evaCount: Int,
        val evas: String,
        val externalId: String,
        val headImgurl: String,
        val id: String,
        val imgurl: String,
        val loginUid: String,
        val nickname: String,
        val releaseTime: String,
        val type: String,
        val uid: String,
        val videoUrl: String,
        var zan: Boolean,
        var zanCount: Int,
        val zans: String,
        val infos: MutableList<EvaluateMeal>
) : Parcelable