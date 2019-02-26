package com.yizhipin.usercenter.service.impl

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.FeeRecord
import com.yizhipin.base.data.response.RelevanceUser
import com.yizhipin.base.data.response.Store
import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.ext.convert
import com.yizhipin.base.ext.convertBoolean
import com.yizhipin.base.ext.convertPaging
import com.yizhipin.usercenter.data.repository.UserRepository
import com.yizhipin.usercenter.service.UserService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class UserServiceImpl @Inject constructor() : UserService {

    @Inject
    lateinit var mRepository: UserRepository

    override fun getCode(map: MutableMap<String, String>): Observable<Boolean> {

        return mRepository.getCode(map)
                .convertBoolean()
    }

    override fun resetPwd(map: MutableMap<String, String>): Observable<Boolean> {

        return mRepository.resetPwd(map)
                .convertBoolean()
    }

    override fun register(map: MutableMap<String, String>): Observable<UserInfo> {

        return mRepository.register(map)
                .convert()
    }

    override fun login(map: MutableMap<String, String>): Observable<UserInfo> {

        return mRepository.login(map)
                .convert()
    }

    override fun getUserInfo(map: MutableMap<String, String>): Observable<UserInfo> {
        return mRepository.getUserInfo(map)
                .convert()
    }
    override fun getRelevanceUser(map: MutableMap<String, String>): Observable<MutableList<RelevanceUser>> {
        return mRepository.getRelevanceUser(map)
                .convert()
    }
    override fun addRelevanceUser(map: MutableMap<String, String>): Observable<RelevanceUser> {
        return mRepository.addRelevanceUser(map)
                .convert()
    }
    override fun updateRelevanceUser(map: MutableMap<String, String>): Observable<RelevanceUser> {
        return mRepository.updateRelevanceUser(map)
                .convert()
    }
    override fun deleteRelevanceUser(map: MutableMap<String, String>): Observable<Boolean> {
        return mRepository.deleteRelevanceUser(map)
                .convertBoolean()
    }

    override fun loadFeeRecordList(map: MutableMap<String, String>): Observable<MutableList<FeeRecord>> {
        return mRepository.loadFeeRecordList(map)
                .convert()
    }

    override fun getInvitationList(map: MutableMap<String, String>): Observable<MutableList<UserInfo>> {
        return mRepository.getInvitationList(map)
                .convert()
    }
    override fun addInvitation(map: MutableMap<String, String>): Observable<UserInfo> {
        return mRepository.addInvitation(map)
                .convert()
    }

    override fun getIntegralList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<FeeRecord>>> {
        return mRepository.getIntegralList(map)
                .convertPaging()
    }

    override fun getUnreadNewCount(map: MutableMap<String, String>): Observable<Int> {
        return mRepository.getUnreadNewCount(map)
                .convert()
    }

    /**
     * 编辑用户资料
     */
    override fun editUserInfo(map: MutableMap<String, String>): Observable<UserInfo> {
        return mRepository.editUserInfo(map)
                .convert()
    }

    override fun getCartCount(map: MutableMap<String, String>): Observable<Int> {
        return mRepository.getCartCount(map).convert()
    }

    override fun getDefaultStore(map: MutableMap<String, String>): Observable<Store> {

        return mRepository.getDefaultStore(map).convert()
    }

    /**
     * 绑定手机号
     */
    override fun bindMobile(map: MutableMap<String, String>): Observable<Boolean> {

        return mRepository.bindMobile(map)
                .convertBoolean()
    }

    override fun setPayPwd(map: MutableMap<String, String>): Observable<Boolean> {

        return mRepository.setPayPwd(map)
                .convertBoolean()
    }

    override fun updatePayPwd(map: MutableMap<String, String>): Observable<Boolean> {

        return mRepository.updatePayPwd(map)
                .convertBoolean()
    }

    override fun resetPayPwd(map: MutableMap<String, String>): Observable<Boolean> {

        return mRepository.resetPayPwd(map)
                .convertBoolean()
    }
}