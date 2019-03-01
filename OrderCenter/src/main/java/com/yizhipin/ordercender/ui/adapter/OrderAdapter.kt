package com.yizhipin.ordercender.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yizhipin.base.common.PhotographStatus
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.ordercender.R
import com.yizhipin.ordercender.data.response.Order
import kotlinx.android.synthetic.main.layout_order_item.view.*

class OrderAdapter(val context: Context) : BaseRecyclerViewAdapter<Order, OrderAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_order_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]

        with(model) {
            holder.itemView.mPriceTv.text = "¥ ${amount}"
            holder.itemView.mContentTv.text = title
            holder.itemView.mDateTv.text = createTime
            holder.itemView.mGoodsIv.loadUrl(imgurl)

            when (type) {
                PhotographStatus.DEAL_WEDDING -> holder.itemView.mGoodsNameTv.text = context.getString(R.string.veil_photography)
                PhotographStatus.DEAL_PHOTO -> holder.itemView.mGoodsNameTv.text = context.getString(R.string.describe_photography)
                PhotographStatus.DEAL_BABY -> holder.itemView.mGoodsNameTv.text = context.getString(R.string.baby_photography)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
