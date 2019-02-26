package com.yizhipin.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ${XiLei} on 2018/8/18.
 */
@Parcelize
class OssAddress(val accessKeyId: String,
                 val accessKeySecret: String,
                 val bucketName: String,
                 val domain: String,
                 val endpoint: String
                 ) : Parcelable