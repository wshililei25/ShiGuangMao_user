package com.yizhipin.goods.ui.adapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eightbitlab.rxbus.Bus
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.common.TeacherType
import com.yizhipin.base.data.response.Teacher
import com.yizhipin.base.event.CameramanCheckedEvent
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ext.setVisible
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.goods.R
import com.yizhipin.goods.ui.activity.TeacherDetailActivity
import kotlinx.android.synthetic.main.layout_cameraman_item.view.*
import org.jetbrains.anko.startActivity


class TeacherAdapter(val context: Context, var mOrderId: String) : BaseRecyclerViewAdapter<Teacher, TeacherAdapter.ViewHolder>(context) {

    private var mSelectedPos = -1//保存当前选中的position
    private lateinit var mTeacherImageAdapter: TeacherImageAdapter

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

            holder.itemView.mContentTv.text = selfIntroduction
            holder.itemView.mPriceTv.text = "¥${webUser.photoAmount}/套服装"
            holder.itemView.mAmountTv.text = "¥${webUser.extraAmount}"

            when (teacherType) {
                TeacherType.TEACHER_SHEYING -> holder.itemView.mNameTv.text = webUser.nickname.plus(" | 摄影师")
                TeacherType.TEACHER_HUAZHUANG -> holder.itemView.mNameTv.text = webUser.nickname.plus(" | 化妆师")
            }

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

        if (dataList.size <= 0) {
            holder.itemView.mShopCb.setChecked(mSelectedPos == position)
        } else {
            holder.itemView.mShopCb.setChecked(mSelectedPos == position)
        }

        holder.itemView.mShopCb.onClick {

            if (mSelectedPos != position) {//当前选中的position和上次选中不是同一个position 执行
                holder.itemView.mShopCb.setChecked(true);
                if (mSelectedPos != -1) {//判断是否有效 -1是初始值 即无效 第二个参数是Object 随便传个int 这里只是起个标志位
                    notifyItemChanged(mSelectedPos, 0);
                }
                mSelectedPos = position
            }

            Bus.send(CameramanCheckedEvent(model))
        }

        mTeacherImageAdapter.setOnItemClickListener(object : OnItemClickListener<String> {
            override fun onItemClick(item: String, position: Int) {
                context.startActivity<TeacherDetailActivity>(BaseConstant.KEY_TEACHER_ID to model.id
                        , BaseConstant.KEY_TEACHER_USER_ID to model.uid, BaseConstant.KEY_IS_DESTINE to if (mOrderId.isNullOrBlank()) true else false)
            }
        })
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}
