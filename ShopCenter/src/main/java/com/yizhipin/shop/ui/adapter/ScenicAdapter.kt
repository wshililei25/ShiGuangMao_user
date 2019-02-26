package com.yizhipin.shop.ui.adapter


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yizhipin.base.data.response.ScenicSpot
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ext.setVisible
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.shop.R
import kotlinx.android.synthetic.main.layout_goods_hot_item.view.*

/**
 * 景点
 */
class ScenicAdapter(var context: Context) : BaseRecyclerViewAdapter<ScenicSpot, ScenicAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_goods_hot_item, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        with(model) {
            holder.itemView.mGoodsNameTv.text = title
            holder.itemView.mPriceTv.text = context.getString(R.string.rmb).plus(amount)
            if (markerPrice.toDouble() > 0) {
                holder.itemView.mOriginalPriceTv.setVisible(true)
                holder.itemView.mOriginalPriceTv.text = context.getString(R.string.rmb).plus(markerPrice)
            } else {
                holder.itemView.mOriginalPriceTv.setVisible(false)
            }

            holder.itemView.mGoodsIv.loadUrl(imgurl)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
