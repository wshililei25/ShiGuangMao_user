package com.yizhipin.usercenter.utils

import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.provider.common.ProviderConstant

/**
 *本地存储用户相关信息
 */
object UserPrefsUtils {

    fun putUserInfo(userInfo: UserInfo?) {
        AppPrefsUtils.putString(BaseConstant.KEY_SP_TOKEN, userInfo?.id.toString() ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_MOBILE, userInfo?.mobile ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_ICON, userInfo?.imgurl ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_NICKNAME, userInfo?.nickname ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_AMOUNT, userInfo?.amount.toString() ?: "0")
    }

    /**
     *  退出登录时清空存储
     */
    fun clearUserInfo() {
        AppPrefsUtils.remove(BaseConstant.KEY_SP_TOKEN)
        AppPrefsUtils.remove(ProviderConstant.KEY_SP_USER_MOBILE)
        AppPrefsUtils.remove(ProviderConstant.KEY_SP_USER_ICON)
        AppPrefsUtils.remove(ProviderConstant.KEY_SP_USER_NICKNAME)
        AppPrefsUtils.remove(ProviderConstant.KEY_AMOUNT)
    }
}
