package com.yizhipin.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yizhipin.R
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.StoreFollow
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.provider.common.ProvideReqCode
import kotlinx.android.synthetic.main.layout_shop_item.view.*

class ShopFollowAdapter(var context: Context) : BaseRecyclerViewAdapter<StoreFollow, ShopFollowAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_shop_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]

        with(model.store) {
            holder.itemView.mGoodsIv.loadUrl(imgurl)
            holder.itemView.mGoodsNameTv.text = storeName
            holder.itemView.mAddressTv.text = province.plus(city).plus(detail)
            holder.itemView.mNumTv.text = "服务顾客：${serviceCount}人次"
            holder.itemView.mStarView.setCheckStarCount(starCount)
        }

     /*   holder.itemView.mSelectTv.onClick {
            var intent = Intent()
            intent.putExtra(BaseConstant.KEY_SHOP_ID, model.id.toString())
            intent.putExtra(BaseConstant.KEY_SHOP_NAME, model.storeName)
            (context as Activity).setResult(ProvideReqCode.CODE_RESULT_SHOP, intent)
            (context as Activity).finish()
        }*/

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)


}
