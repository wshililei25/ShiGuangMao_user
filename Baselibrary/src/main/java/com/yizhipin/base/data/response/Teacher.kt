package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/10/4.
 */
@Parcelize
class Teacher(
        val applyType: String,
        val area: String,
        val attention: Boolean,
        val city: String,
        val detail: String,
        val device: String,
        val emergencyContact: String,
        val emergencyMobile: String,
        val id: String,
        val loginUid: String,
        val pay: Boolean,
        val payAmount: String,
        val provice: String,
        val selfIntroduction: String,
        val status: String,
        val storeId: String,
        val teacherType: String,
        val uid: String,
        val webUser: TeacherInfo
) : Parcelable