package com.yizhipin.goods.ui.adapter

import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.AppiontSport
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.goods.R
import com.yizhipin.provider.router.RouterPath
import kotlinx.android.synthetic.main.layout_appoint_feature_spot_item.view.*

/**
 * Created by ${XiLei} on 2018/8/19.
 * 指定景点
 */
class AppointFeatureSpotAdapter(var context: Context) : BaseRecyclerViewAdapter<AppiontSport, AppointFeatureSpotAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_appoint_feature_spot_item, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val modle = dataList[position]

        with(modle) {
            holder.itemView.mAmountTv.text = amount
            holder.itemView.mNameTv.text = name

            if(image.isNullOrEmpty()){
                holder.itemView.mIv.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.add))
            }else{
                holder.itemView.mIv.loadUrl(image)
            }
        }

        holder.itemView.mIv.onClick {
            ARouter.getInstance().build(RouterPath.ShopCenter.PATH_SHOP)
                    .withInt(BaseConstant.KEY_DRESS_POSITION, position).navigation(context as Activity, 1002)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}