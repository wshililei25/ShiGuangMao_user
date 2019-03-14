package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/10/4.
 */
@Parcelize
class OrderTeacher(
    val extraAmount: String,
    val id: String,
    val imgurl: String,
    val nickname: String,
    val orderId: String,
    val teacherAmount: String,
    val teacherId: String,
    val teacherType: String
) : Parcelable