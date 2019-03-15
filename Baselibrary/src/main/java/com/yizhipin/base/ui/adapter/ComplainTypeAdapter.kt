package com.yizhipin.base.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yizhipin.base.R
import com.yizhipin.usercenter.data.response.ComplainType
import kotlinx.android.synthetic.main.layout_basic_services_item.view.*

class ComplainTypeAdapter(var context: Context) : BaseRecyclerViewAdapter<ComplainType, ComplainTypeAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_complain_type_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]

        holder.itemView.mNameTv.text = model.name

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
