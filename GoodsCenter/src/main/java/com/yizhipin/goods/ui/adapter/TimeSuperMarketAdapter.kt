package com.yizhipin.goods.ui.adapter


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yizhipin.base.data.response.TimeSuperMarket
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.goods.R
import kotlinx.android.synthetic.main.layout_super_market_item.view.*

class TimeSuperMarketAdapter(val context: Context) : BaseRecyclerViewAdapter<TimeSuperMarket, TimeSuperMarketAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_super_market_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        with(model) {
            holder.itemView.mGoodsNameTv.text = title
            holder.itemView.mAmountTv.text = context.getString(R.string.rmb) + amount.toString()
            if (!model.imgurl.isNullOrEmpty()) {
                holder.itemView.mGoodsIv.loadUrl(imgurl!!)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
