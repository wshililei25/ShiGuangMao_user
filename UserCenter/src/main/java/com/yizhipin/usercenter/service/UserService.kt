package com.yizhipin.usercenter.service

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.*
import io.reactivex.Observable


/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface UserService {
    fun getDefaultStore(map: MutableMap<String, String>): Observable<Store>
    fun getCode(map: MutableMap<String, String>): Observable<Boolean>
    fun resetPwd(map: MutableMap<String, String>): Observable<Boolean>
    fun register(map: MutableMap<String, String>): Observable<UserInfo>
    fun login(map: MutableMap<String, String>): Observable<UserInfo>
    fun getUserInfo(map: MutableMap<String, String>): Observable<UserInfo>
    fun loadFeeRecordList(map: MutableMap<String, String>): Observable<MutableList<FeeRecord>>
    fun getIntegralList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<FeeRecord>>>
    fun getUnreadNewCount(map: MutableMap<String, String>): Observable<Int>
    fun editUserInfo(map: MutableMap<String, String>): Observable<UserInfo>
    fun bindMobile(map: MutableMap<String, String>): Observable<Boolean>
    fun getCartCount(map: MutableMap<String, String>): Observable<Int>
    fun setPayPwd(map: MutableMap<String, String>): Observable<Boolean>
    fun updatePayPwd(map: MutableMap<String, String>): Observable<Boolean>
    fun resetPayPwd(map: MutableMap<String, String>): Observable<Boolean>
    fun getInvitationList(map: MutableMap<String, String>): Observable<MutableList<UserInfo>>
    fun getRelevanceUser(map: MutableMap<String, String>): Observable<MutableList<RelevanceUser>>
    fun addRelevanceUser(map: MutableMap<String, String>): Observable<RelevanceUser>
    fun deleteRelevanceUser(map: MutableMap<String, String>): Observable<Boolean>
    fun updateRelevanceUser(map: MutableMap<String, String>): Observable<RelevanceUser>
    fun addInvitation(map: MutableMap<String, String>): Observable<UserInfo>
    fun getOssSign(map: MutableMap<String, String>): Observable<String>
    fun getOssSignFile(map: MutableMap<String, String>): Observable<String>
    fun getOssAddress(): Observable<OssAddress>
}
