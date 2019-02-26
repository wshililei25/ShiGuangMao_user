package com.yizhipin.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yizhipin.R
import com.yizhipin.base.data.response.News
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_information_item.view.*

class InformationAdapter(val context: Context) : BaseRecyclerViewAdapter<News, InformationAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_information_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]

        with(model) {

            holder.itemView.mGoodsNameTv.text = title
            holder.itemView.mTimeTv.text = createTime
            holder.itemView.mGoodsIv.loadUrl(imgurl)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}
