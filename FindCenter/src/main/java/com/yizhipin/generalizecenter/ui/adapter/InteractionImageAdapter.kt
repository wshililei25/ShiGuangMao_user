package com.yizhipin.generalizecenter.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.generalizecenter.R
import kotlinx.android.synthetic.main.layout_interation_image_item.view.*

/**
 * Created by ${XiLei} on 2018/8/19.
 */
class InteractionImageAdapter(var context: Context) : BaseRecyclerViewAdapter<String, InteractionImageAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_interation_image_item, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val modle = dataList[position]

        holder.itemView.mIv.loadUrl(modle)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}