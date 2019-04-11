package com.yizhipin.usercenter.data.repository

import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.net.RetrofitFactoryDelete
import com.yizhipin.base.data.net.RetrofitFactoryGet
import com.yizhipin.base.data.net.RetrofitFactoryPost
import com.yizhipin.base.data.net.RetrofitFactoryPut
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.protocol.BaseResp
import com.yizhipin.base.data.response.*
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.usercenter.data.api.UserApi
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/27.
 */
class UserRepository @Inject constructor() {

    fun getCode(map: MutableMap<String, String>): Observable<BaseResp<Boolean>> {
        return RetrofitFactoryGet().create(UserApi::class.java).getCode(map["mobile"]!!)
    }
    fun complain(map: MutableMap<String, String>): Observable<BaseResp<Complain>> {
        return RetrofitFactoryPost(map).create(UserApi::class.java).complain()
    }

    fun resetPwd(map: MutableMap<String, String>): Observable<BaseResp<Boolean>> {

        return RetrofitFactoryPost(map).create(UserApi::class.java)
                .resetPwd()
    }

    fun register(map: MutableMap<String, String>): Observable<BaseResp<UserInfo>> {

        return RetrofitFactoryPost(map).create(UserApi::class.java)
                .register()
    }

    fun login(map: MutableMap<String, String>): Observable<BaseResp<UserInfo>> {

        return RetrofitFactoryPost(map).create(UserApi::class.java)
                .login()
    }

    fun getUserInfo(map: MutableMap<String, String>): Observable<BaseResp<UserInfo>> {
        return RetrofitFactoryGet().create(UserApi::class.java)
                .getUserInfo(map["id"]!!)
    }

    fun getRelevanceUser(map: MutableMap<String, String>): Observable<BaseResp<MutableList<RelevanceUser>>> {
        return RetrofitFactoryGet().create(UserApi::class.java)
                .getRelevanceUser(map["uid"]!!)
    }

    fun addRelevanceUser(map: MutableMap<String, String>): Observable<BaseResp<RelevanceUser>> {
        return RetrofitFactoryPost(map).create(UserApi::class.java)
                .addRelevanceUser()
    }

    fun updateRelevanceUser(map: MutableMap<String, String>): Observable<BaseResp<RelevanceUser>> {
        return RetrofitFactoryPut(map).create(UserApi::class.java)
                .updateRelevanceUser(map["id"]!!)
    }

    fun deleteRelevanceUser(map: MutableMap<String, String>): Observable<BaseResp<Boolean>> {
        return RetrofitFactoryDelete(map).create(UserApi::class.java)
                .deleteRelevanceUser(map["id"]!!)
    }

    fun loadFeeRecordList(map: MutableMap<String, String>): Observable<BaseResp<MutableList<FeeRecord>>> {
        return RetrofitFactoryGet().create(UserApi::class.java)
                .loadFeeRecordList(map["uid"]!!)
    }

    fun getInvitationList(map: MutableMap<String, String>): Observable<BaseResp<MutableList<UserInfo>>> {
        return RetrofitFactoryGet().create(UserApi::class.java)
                .getInvitationList(map["uid"]!!)
    }

    fun addInvitation(map: MutableMap<String, String>): Observable<BaseResp<UserInfo>> {
        return RetrofitFactoryGet().create(UserApi::class.java)
                .addInvitation(map["uid"]!!, map["requestCode"]!!)
    }

    fun getIntegralList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<FeeRecord>>> {
        return RetrofitFactoryGet().create(UserApi::class.java)
                .getIntegralList(map["uid"]!!, map["currentPage"]!!)
    }

    fun getUnreadNewCount(map: MutableMap<String, String>): Observable<BaseResp<Int>> {
        return RetrofitFactoryGet().create(UserApi::class.java)
                .getUnreadNewCount(map["uid"]!!)
    }

    /**
     * 编辑用户信息
     */
    fun editUserInfo(map: MutableMap<String, String>): Observable<BaseResp<UserInfo>> {
        return RetrofitFactoryPut(map).create(UserApi::class.java).editUserInfo(AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
    }

    fun getCartCount(map: MutableMap<String, String>): Observable<BaseResp<Int>> {
        return RetrofitFactoryGet().create(UserApi::class.java).getCartCount(map["uid"]!!)
    }

    fun getDefaultStore(map: MutableMap<String, String>): Observable<BaseResp<Store>> {
        return RetrofitFactoryGet().create(UserApi::class.java)
                .getDefaultStore(map["lng"]!!, map["lat"]!!)
    }

    /**
     * 绑定手机号
     */
    fun bindMobile(map: MutableMap<String, String>): Observable<BaseResp<Boolean>> {
        return RetrofitFactoryPut(map).create(UserApi::class.java)
                .bindMobile(AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
    }

    fun setPayPwd(map: MutableMap<String, String>): Observable<BaseResp<Boolean>> {
        return RetrofitFactoryPost(map).create(UserApi::class.java)
                .setPayPwd()
    }

    fun updatePayPwd(map: MutableMap<String, String>): Observable<BaseResp<Boolean>> {
        return RetrofitFactoryPost(map).create(UserApi::class.java)
                .updatePayPwd()
    }

    fun resetPayPwd(map: MutableMap<String, String>): Observable<BaseResp<Boolean>> {
        return RetrofitFactoryPost(map).create(UserApi::class.java)
                .resetPayPwd()
    }

    fun getOssSign(map: MutableMap<String, String>): Observable<BaseResp<String>> {
        return RetrofitFactoryGet().create(UserApi::class.java).getOssSign(map["access-token"]!!,map["content"]!!)
    }

    fun getOssSignFile(map: MutableMap<String, String>): Observable<BaseResp<String>> {
        return RetrofitFactoryGet().create(UserApi::class.java).getOssSignFile(map["access-token"]!!,map["content"]!!)
    }

    fun getOssAddress(): Observable<BaseResp<OssAddress>> {
        return RetrofitFactoryGet().create(UserApi::class.java).getOssAddress()
    }
}