package com.yizhipin.usercenter.ui.adapter


import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yizhipin.base.data.response.FeeRecord
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.usercenter.R
import kotlinx.android.synthetic.main.layout_fee_record_item.view.*

class FeeRecordAdapter(var context: Context) : BaseRecyclerViewAdapter<FeeRecord, FeeRecordAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_fee_record_item, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]

        with(model) {
            holder.itemView.mNameTv.text = title
            holder.itemView.mDateTv.text = createTime
            if (income) {
                holder.itemView.mAmountTv.text = "+$amount"
                holder.itemView.mAmountTv.setTextColor(ContextCompat.getColor(context, R.color.yRed))
            } else {
                holder.itemView.mAmountTv.text = "-$amount"
                holder.itemView.mAmountTv.setTextColor(ContextCompat.getColor(context, R.color.yBlue))
            }

        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
