package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/10/4.
 */
@Parcelize
class EvaluateMeal(
    val id: String,
    val imgurl: String,
    val marketPrice: String,
    val price: String,
    val sellCount: String,
    val starCount: String,
    val title: String
) : Parcelable