package com.yizhipin.shop.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yizhipin.base.data.response.Evaluate
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.shop.R
import kotlinx.android.synthetic.main.layout_evaluate_item.view.*

class EvaluateAdapter(var context: Context) : BaseRecyclerViewAdapter<Evaluate, EvaluateAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_evaluate_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]

        with(model) {
            holder.itemView.mGoodsIv.loadUrl(webUser.imgurl)
            holder.itemView.mNameTv.text = webUser.nickname
            holder.itemView.mTimeTv.text = evaTime
            holder.itemView.mContentTv.text = content
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)


}
