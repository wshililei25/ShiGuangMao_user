package com.yizhipin.goods.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eightbitlab.rxbus.Bus
import com.yizhipin.base.event.CameramanCheckedEvent
import com.yizhipin.base.event.ImageLookEvent
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.goods.R
import kotlinx.android.synthetic.main.layout_teacher_works_image_item.view.*

class WorksImageAdapter(val context: Context,val itemPosition:Int) : BaseRecyclerViewAdapter<String, WorksImageAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_teacher_works_image_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]

        holder.itemView.mIv.loadUrl(model)
        holder.itemView.mIv.onClick {
            Bus.send(ImageLookEvent(itemPosition,position))
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}
