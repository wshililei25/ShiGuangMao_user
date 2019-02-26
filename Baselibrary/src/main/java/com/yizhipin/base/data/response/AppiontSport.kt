package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class AppiontSport(): Parcelable {
    var image: String = ""
    var amount: String = ""
    var name: String = ""
}
