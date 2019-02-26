package com.yizhipin.base.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yizhipin.base.R
import com.yizhipin.base.data.response.BasicServices
import kotlinx.android.synthetic.main.layout_basic_services_item.view.*

/**
 * 基础服务
 */
class BasicServicesAdapter(var context: Context) : BaseRecyclerViewAdapter<BasicServices, BasicServicesAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_basic_services_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]

        with(model) {
            holder.itemView.mNameTv.text = serviceName
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)


}
