package com.yizhipin.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yizhipin.R
import com.yizhipin.base.data.response.MealFollow
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_meal_item.view.*

class MealFollowAdapter(var context: Context) : BaseRecyclerViewAdapter<MealFollow, MealFollowAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_meal_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]

        holder.itemView.mCostPriceTv.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        holder.itemView.mCostPriceTv.paint.isAntiAlias = true

        with(model.photoPackage) {
            holder.itemView.mGoodsIv.loadUrl(imgurl)
            holder.itemView.mNameTv.text = title
            holder.itemView.mPriceTv.text = context.getString(R.string.rmb).plus(price)
            holder.itemView.mCostPriceTv.text = context.getString(R.string.rmb).plus(marketPrice)
            holder.itemView.mNumTv.text = "已售${sellCount}次"
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)


}
