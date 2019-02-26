package com.yizhipin.goods.ui.adapter


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yizhipin.base.data.response.CloudDiskImage
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.goods.R
import kotlinx.android.synthetic.main.layout_cloud_disk_image_item.view.*

class CloudDiskImageAdapter(val context: Context) : BaseRecyclerViewAdapter<CloudDiskImage, CloudDiskImageAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_cloud_disk_image_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        with(model) {
            holder.itemView.mNameTv.text = key
            holder.itemView.mRemarkTv.text = note

            holder.itemView.mGoodsIv.loadUrl(key!!)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
