package com.yizhipin.goods.ui.adapter


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yizhipin.base.data.response.CloudDisk
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.goods.R
import kotlinx.android.synthetic.main.layout_cloud_disk_item.view.*

class CloudDiskAdapter(val context: Context) : BaseRecyclerViewAdapter<CloudDisk, CloudDiskAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_cloud_disk_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        with(model) {
            holder.itemView.mNameTv.text = folder

            /*  if (!model.imgurl.isNullOrEmpty()) {
                  holder.itemView.mGoodsIv.loadUrl(imgurl!!)
              }*/
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
