package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 评论回复
 */
@Parcelize
data class EvaluateImage(
        val image: String
) : Parcelable
