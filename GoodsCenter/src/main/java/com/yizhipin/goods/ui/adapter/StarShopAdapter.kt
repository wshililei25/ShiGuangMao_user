package com.yizhipin.goods.ui.adapter


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yizhipin.base.data.response.Store
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.goods.R
import kotlinx.android.synthetic.main.layout_star_shop_item.view.*

class StarShopAdapter(val context: Context) : BaseRecyclerViewAdapter<Store, StarShopAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_star_shop_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        with(model) {
            holder.itemView.mShopNameTv.text = storeName
            holder.itemView.mAddressTv.text = province.plus(city).plus(detail)
            holder.itemView.mSoldNumTv.text = "服务顾客：${serviceCount}人次"
            holder.itemView.mStarView.setCheckStarCount(starCount)
            holder.itemView.mStarView.refreshView()

            if (!model.imgurl.isNullOrEmpty()) {
                holder.itemView.mIv.loadUrl(imgurl!!)
            }
        }


    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
