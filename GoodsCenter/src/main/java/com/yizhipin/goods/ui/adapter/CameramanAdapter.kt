package com.yizhipin.goods.ui.adapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eightbitlab.rxbus.Bus
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.Cameraman
import com.yizhipin.base.event.CameramanCheckedEvent
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ext.setVisible
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.goods.R
import com.yizhipin.goods.ui.activity.TeacherDetailActivity
import kotlinx.android.synthetic.main.layout_cameraman_item.view.*
import org.jetbrains.anko.startActivity

class CameramanAdapter(val context: Context, var mOrderId: String) : BaseRecyclerViewAdapter<Cameraman, CameramanAdapter.ViewHolder>(context) {

    private lateinit var mCameramanImageAdapter: CameramanImageAdapter
    private var mMap = mapOf<Int, Boolean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_cameraman_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]

        if (!mOrderId.isNullOrBlank()) {
            holder.itemView.mShopCb.setVisible(true)
        }

        with(model) {
            holder.itemView.mIv.loadUrl(webUser.imgurl)
            holder.itemView.mNumTv.text = webUser.credit.toString()
            holder.itemView.mNameTv.text = webUser.nickname.plus(" | ").plus(teacherType)
            holder.itemView.mContentTv.text = selfIntroduction
            holder.itemView.mPriceTv.text = "¥${webUser.photoAmount}/套服装"
            holder.itemView.mAmountTv.text = "¥${webUser.extraAmount}"

            var listResult = mutableListOf<String>()
            if (null != works && works.size > 0) {
                if (works[0].imgurls.contains(",")) {
                    var list = works[0].imgurls!!.split(",").toMutableList()
                    for (l in list) {
                        listResult.add(l)
                    }
                } else {
                    listResult.add(works[0].imgurls)
                }
            }
            holder.itemView.mRv.layoutManager = GridLayoutManager(context!!, 3)
            mCameramanImageAdapter = CameramanImageAdapter(context!!)
            mCameramanImageAdapter.setData(listResult)
            holder.itemView.mRv.adapter = mCameramanImageAdapter
        }

        var mutableMap = mMap.toMutableMap()
        for ((index) in dataList.withIndex()) {
            mutableMap.put(index, false)
        }

        holder.itemView.mShopCb.onClick {

            for ((index) in dataList.withIndex()) {
                mutableMap.put(index, false)
            }
            holder.itemView.mShopCb.isChecked = true
            mutableMap.put(position, true)
            notifyDataSetChanged()
            Bus.send(CameramanCheckedEvent(model))
        }

        holder.itemView.mRv.onClick {
            context.startActivity<TeacherDetailActivity>(BaseConstant.KEY_CAMERAMAN_ID to model.id)
        }

        mCameramanImageAdapter.setOnItemClickListener(object : OnItemClickListener<String> {
            override fun onItemClick(item: String, position: Int) {
                context.startActivity<TeacherDetailActivity>(BaseConstant.KEY_CAMERAMAN_ID to model.id)
            }
        })
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}
