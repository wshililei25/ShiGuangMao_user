package com.yizhipin.generalizecenter.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yizhipin.base.data.response.InteractionDetailsEva
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.generalizecenter.R
import kotlinx.android.synthetic.main.layout_interation_details_eva_item.view.*

/**
 * Created by ${XiLei} on 2018/8/19.
 */
class InteractionDetailsEvaAdapter(var context: Context) : BaseRecyclerViewAdapter<InteractionDetailsEva, InteractionDetailsEvaAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_interation_details_eva_item, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val modle = dataList[position]
        with(modle) {
            holder.itemView.mNameTv.text = nickname
            holder.itemView.mContentTv.text = content
            imgurl?.let{
                holder.itemView.mIv.loadUrl(imgurl)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}