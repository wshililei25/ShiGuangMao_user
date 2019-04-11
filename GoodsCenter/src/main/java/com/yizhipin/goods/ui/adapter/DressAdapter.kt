package com.yizhipin.goods.ui.adapter


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yizhipin.base.data.response.Dress
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.goods.R
import kotlinx.android.synthetic.main.layout_dress_item.view.*

class DressAdapter(val context: Context, val mDressShopStatus: String) : BaseRecyclerViewAdapter<Dress, DressAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_dress_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        with(model) {
            holder.itemView.mGoodsNameTv.text = title
            if (mDressShopStatus == "0") { //销售区
                holder.itemView.mAmountTv.text = context.getString(R.string.rmb) + amount.toString()
            } else { //共享区
                holder.itemView.mAmountTv.text = context.getString(R.string.rent) + "  " + context.getString(R.string.rmb) + amount.toString()
                holder.itemView.mAmountRightTv.text = context.getString(R.string.clap) + "  " + context.getString(R.string.rmb) + paiAmount.toString()
            }

            if (!model.imgurl.isNullOrEmpty()) {
                holder.itemView.mGoodsIv.loadUrl(imgurl!!)
            }
        }


    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
