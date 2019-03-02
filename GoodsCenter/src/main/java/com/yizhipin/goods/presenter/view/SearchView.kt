package com.yizhipin.goods.presenter.view

import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Goods
import com.yizhipin.base.data.response.SearchKeyword
import com.yizhipin.base.presenter.view.BaseView

/**
 * Created by ${XiLei} on 2018/7/26.
 */
interface SearchView : BaseView {
    fun onGetSearchKeywordSuccess(result: MutableList<SearchKeyword>?)
    fun onGetGoodsListSuccess(result: BasePagingResp<MutableList<Goods>?>)
}