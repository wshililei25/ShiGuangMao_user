package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/10/4.
 */
@Parcelize
class InteractionDetails(
        val barType: String,
        val content: String,
        val evaCount: String,
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
        val zan: Boolean,
        val zanCount: String,
        val zans: MutableList<InteractionDetailsZan>,
        val evas: MutableList<InteractionDetailsEva>,
        val infos: MutableList<EvaluateMeal>
) : Parcelable