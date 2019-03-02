package com.yizhipin.goods.service

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.*
import io.reactivex.Observable


/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface CategoryService {

    fun getCategoryAll(): Observable<MutableList<Category>?>
    fun getSearchKeyword(): Observable<MutableList<SearchKeyword>?>
    fun getCategorySecond(map: MutableMap<String, String>): Observable<MutableList<CategorySecond>?>
    fun getGoodsList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Goods>?>>
    fun getSearchGoodsList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Goods>?>>
    fun getShopGoodsList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<Goods>?>>
    fun getCollectGoodsList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<CollectGoods>?>>
    fun getCollectShopList(map: MutableMap<String, String>): Observable<BasePagingResp<MutableList<CollectShop>?>>
}
