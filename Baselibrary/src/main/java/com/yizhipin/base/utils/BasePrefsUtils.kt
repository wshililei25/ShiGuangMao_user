package com.yizhipin.base.utils

import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.OssAddress

/**
 *本地存储阿里云存储信息
 */
object BasePrefsUtils {

    fun putOssInfo(userInfo: OssAddress?) {
        AppPrefsUtils.putString(BaseConstant.IMAGE_ADDRESS, userInfo?.domain ?: "")
    }
}
