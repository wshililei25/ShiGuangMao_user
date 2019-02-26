package com.yizhipin.ordercender.ui.adapter


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yizhipin.base.data.response.RedPacket
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.ordercender.R
import kotlinx.android.synthetic.main.layout_fee_red_item.view.*

class RedPacketAdapter(var context: Context) : BaseRecyclerViewAdapter<RedPacket, RedPacketAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_fee_red_item, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]

        with(model) {
            holder.itemView.mNameTv.text = reason
            holder.itemView.mDateTv.text = getTime
            holder.itemView.mAmountTv.text = "+$amount"

        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
