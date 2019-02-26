package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class AppiontDress(): Parcelable {
    var title: String = ""
    var womenImage: String = ""
    var manImage: String = ""
}
