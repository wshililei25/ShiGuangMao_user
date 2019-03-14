package com.yizhipin.generalizecenter.ui.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eightbitlab.rxbus.Bus
import com.yizhipin.base.data.response.Interaction
import com.yizhipin.base.event.LikeEvent
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ext.setVisible
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.base.utils.DateUtils
import com.yizhipin.generalizecenter.R
import com.yizhipin.provider.common.afterLogin
import kotlinx.android.synthetic.main.layout_interation_item.view.*

/**
 * Created by ${XiLei} on 2018/8/19.
 */
class InteractionAdapter(var context: Context) : BaseRecyclerViewAdapter<Interaction, InteractionAdapter.ViewHolder>(context) {

    private lateinit var mInteractionImageAdapter: InteractionImageAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_interation_item, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val modle = dataList[position]

        with(modle) {
            holder.itemView.mContentTv.text = content
            holder.itemView.mDateTv.text = DateUtils.parseDate(releaseTime, DateUtils.FORMAT_SHORT).toString()
            holder.itemView.mLikeCountTv.text = "${zanCount}点赞"
            holder.itemView.mEvaCountTv.text = "${evaCount}评论"

            nickname?.let {
                holder.itemView.mNameTv.text = nickname
            }
            if (headImgurl.isNullOrEmpty()) {
                holder.itemView.mIv.setImageDrawable(context.resources.getDrawable(R.drawable.avatarw))
            } else {
                holder.itemView.mIv.loadUrl(headImgurl)
            }
            imgurl?.let {
                holder.itemView.mImageRv.setVisible(true)
                var listResult = mutableListOf<String>()
                if (imgurl.contains(",")) {
                    var list = imgurl!!.split(",").toMutableList()
                    for (l in list) {
                        listResult.add(l)
                    }
                } else {
                    listResult.add(imgurl)
                }

                holder.itemView.mImageRv.layoutManager = GridLayoutManager(context, 3)
                mInteractionImageAdapter = InteractionImageAdapter(context)
                holder.itemView.mImageRv.adapter = mInteractionImageAdapter
                mInteractionImageAdapter.setData(listResult)
            }

            holder.itemView.mLikeCountTv.isSelected = modle.zan
            holder.itemView.mLikeCountTv.setCompoundDrawables(getSortStatus(zan), null, null, null)
            holder.itemView.mLikeCountTv.onClick {
                afterLogin {
                    if (modle.zan) {
                        modle.zanCount = modle.zanCount - 1
                    } else {
                        modle.zanCount = modle.zanCount + 1
                    }
                    holder.itemView.mLikeCountTv.isSelected = !modle.zan
                    holder.itemView.mLikeCountTv.setCompoundDrawables(getSortStatus(!modle.zan), null, null, null)
                    modle.zan = !modle.zan
                    notifyDataSetChanged()
                    Bus.send(LikeEvent(modle.id))
                }
            }
        }


        /*   if (!modle.imgurls.isNullOrEmpty()) {
               holder.itemView.mImageRv.visibility = View.VISIBLE
               holder.itemView.mImageRv.layoutManager = GridLayoutManager(context, 3)
   //            mEvaluateImageAdapter = EvaluateImageAdapter(context)
   //            holder.itemView.mImageRv.adapter = mEvaluateImageAdapter
   //            val list = modle.imgurls.split(",").toMutableList()
   //            mEvaluateImageAdapter.setData(list)
           }
           if (modle.comments.isNotEmpty()) {
               holder.itemView.mReplyRv.visibility = View.VISIBLE
               holder.itemView.mReplyRv.layoutManager = LinearLayoutManager(context)
               mEvaluateReplyAdapter = EvaluateReplyAdapter(context)
               holder.itemView.mReplyRv.adapter = mEvaluateReplyAdapter
               mEvaluateReplyAdapter.setData(modle.comments)
           }

          */
    }

    private fun getSortStatus(isLike: Boolean): Drawable? {
        var drawable: Drawable? = null
        when (isLike) {
            true -> {
                drawable = ContextCompat.getDrawable(context, R.drawable.heart)!!
                drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            }
            false -> {
                drawable = ContextCompat.getDrawable(context, R.drawable.heart5)!!
                drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            }
        }
        return drawable
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}