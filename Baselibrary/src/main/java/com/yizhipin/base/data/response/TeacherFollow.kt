package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/10/4.
 */
@Parcelize
class TeacherFollow(
        val id: String,
        val packageId: String,
        val uid: String,
        val attentionTime: String,
        val teacherInfo: Teacher
) : Parcelable