package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/10/4.
 */
@Parcelize
class StoreFollow(
        val id: String,
        val storeId: String,
        val uid: String,
        val attentionTime: String,
        val store: Store
) : Parcelable