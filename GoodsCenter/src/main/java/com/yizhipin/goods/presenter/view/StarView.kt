package com.yizhipin.goods.presenter.view

import com.yizhipin.base.data.response.Star
import com.yizhipin.base.presenter.view.BaseView

interface StarView : BaseView {

    fun onGetStarSuccess(result: Star)
}
