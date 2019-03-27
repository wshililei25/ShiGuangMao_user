package com.yizhipin.ui.adapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yizhipin.R
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.TeacherFollow
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ext.setVisible
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.goods.ui.activity.TeacherDetailActivity
import com.yizhipin.goods.ui.adapter.TeacherImageAdapter
import kotlinx.android.synthetic.main.layout_cameraman_item.view.*
import org.jetbrains.anko.startActivity

class TeacherFollowAdapter(val context: Context, var mOrderId: String) : BaseRecyclerViewAdapter<TeacherFollow, TeacherFollowAdapter.ViewHolder>(context) {

    private lateinit var mTeacherImageAdapter: TeacherImageAdapter
    private var mMap = mapOf<Int, Boolean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_cameraman_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]

        holder.itemView.mShopCb.setVisible(false)

        with(model.teacherInfo) {
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
            mTeacherImageAdapter = TeacherImageAdapter(context!!)
            mTeacherImageAdapter.setData(listResult)
            holder.itemView.mRv.adapter = mTeacherImageAdapter
        }

        var mutableMap = mMap.toMutableMap()
        for ((index) in dataList.withIndex()) {
            mutableMap.put(index, false)
        }

        /*   holder.itemView.mShopCb.onClick {

               for ((index) in dataList.withIndex()) {
                   mutableMap.put(index, false)
               }
               holder.itemView.mShopCb.isChecked = true
               mutableMap.put(position, true)
               notifyDataSetChanged()
               Bus.send(CameramanCheckedEvent(model))
           }*/

        mTeacherImageAdapter.setOnItemClickListener(object : OnItemClickListener<String> {
            override fun onItemClick(item: String, position: Int) {
                context.startActivity<TeacherDetailActivity>(BaseConstant.KEY_TEACHER_ID to model.teacherInfo.id
                        , BaseConstant.KEY_TEACHER_USER_ID to model.teacherInfo.uid)
            }
        })
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}
