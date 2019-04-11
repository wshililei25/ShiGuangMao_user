package com.yizhipin.usercenter.data.api

import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.protocol.BaseResp
import com.yizhipin.base.data.response.*
import io.reactivex.Observable
import retrofit2.http.*


/**
 * Created by ${XiLei} on 2018/7/27.
 */
interface UserApi {

    @POST(Api.GET_CODE) //获取验证码
    fun getCode(@Query("mobile") mobile: String): Observable<BaseResp<Boolean>>

    @POST(Api.COMPLAIN)
    fun complain(): Observable<BaseResp<Complain>>

    @POST(Api.RESET_PWD) //重置密码
    fun resetPwd(): Observable<BaseResp<Boolean>>

    @POST(Api.REGISTER) //注册
    fun register(): Observable<BaseResp<UserInfo>>

    @POST(Api.LOGIN) //登录
    fun login(): Observable<BaseResp<UserInfo>>

    @GET("${Api.EDIT_USER_INFO}${"/{id}"}")
    fun getUserInfo(@Path("id") id: String): Observable<BaseResp<UserInfo>>

    @GET(Api.RELEVANCE_USER) //获取关联用户
    fun getRelevanceUser(@Query("uid") uid: String): Observable<BaseResp<MutableList<RelevanceUser>>>

    @POST(Api.ADD_RELEVANCE_USER)
    fun addRelevanceUser(): Observable<BaseResp<RelevanceUser>>

    @PUT("${Api.ADD_RELEVANCE_USER}${"/{id}"}")
    fun updateRelevanceUser(@Path("id") id: String): Observable<BaseResp<RelevanceUser>>

    @DELETE("${Api.ADD_RELEVANCE_USER}${"/{id}"}")
    fun deleteRelevanceUser(@Path("id") uid: String): Observable<BaseResp<Boolean>>

    @GET(Api.UNREAD_NEWS_COUNT) //获取未读消息数
    fun getUnreadNewCount(@Query("uid") uid: String): Observable<BaseResp<Int>>

    @GET("${Api.IS_SHOW_RED}${"/{uid}"}")
    fun getIsShowRedPackage(@Path("uid") uid: String): Observable<BaseResp<Boolean>>

    @GET(Api.FEE_RECORD_LIST) //资金记录
    fun loadFeeRecordList(@Query("uid") uid: String): Observable<BaseResp<MutableList<FeeRecord>>>

    @GET(Api.INVITATION_LIST) //邀请人列表
    fun getInvitationList(@Query("uid") uid: String): Observable<BaseResp<MutableList<UserInfo>>>

    @GET(Api.INVITATION_ADD) //填写邀请码
    fun addInvitation(@Query("uid") uid: String, @Query("requestCode") requestCode: String): Observable<BaseResp<UserInfo>>

    @GET(Api.INTEGRAL_LIST) //积分记录
    fun getIntegralList(@Query("uid") uid: String, @Query("currentPage") currentPage: String): Observable<BasePagingResp<MutableList<FeeRecord>>>

    @PUT("${Api.EDIT_USER_INFO}${"/{id}"}")
    fun editUserInfo(@Path("id") id: String): Observable<BaseResp<UserInfo>>

    /**
     * 获取购物车数量
     */
    @PUT(Api.CART_COUNT)
    fun getCartCount(@Query("uid") uid: String): Observable<BaseResp<Int>>

    @PUT("${Api.BIND_MOBILE}${"/{id}"}")
    fun bindMobile(@Path("id") id: String): Observable<BaseResp<Boolean>>

    /**
     * 获取附近门店
     */
    @GET(Api.DEFAULT_STORE)
    fun getDefaultStore(@Query("lng") lng: String, @Query("lat") lat: String): Observable<BaseResp<Store>>

    /**
     * 设置支付密码
     */
    @POST(Api.SET_PAY_PWD)
    fun setPayPwd(): Observable<BaseResp<Boolean>>

    /**
     * 修改支付密码
     */
    @POST(Api.UPDATE_PAY_PWD)
    fun updatePayPwd(): Observable<BaseResp<Boolean>>

    /**
     * 重置支付密码
     */
    @POST(Api.RESET_PAY_PWD)
    fun resetPayPwd(): Observable<BaseResp<Boolean>>

    @GET(Api.OSS_SIGN)
    fun getOssSign(@Header("access-token") token: String, @Query("content") content: String): Observable<BaseResp<String>>

    @GET(Api.OSS_SIGN)
    fun getOssSignFile(@Header("access-token") token: String, @Query("content") content: String): Observable<BaseResp<String>>

    @GET(Api.IMAGE_ADDRESS)
    fun getOssAddress(): Observable<BaseResp<OssAddress>>
}