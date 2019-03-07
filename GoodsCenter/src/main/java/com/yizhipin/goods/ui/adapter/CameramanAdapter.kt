package com.yizhipin.goods.ui.adapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eightbitlab.rxbus.Bus
import com.yizhipin.base.data.response.Cameraman
import com.yizhipin.base.event.CameramanCheckedEvent
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ext.setVisible
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.goods.R
import kotlinx.android.synthetic.main.layout_cameraman_item.view.*

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
            if(null != works && works.size>0){
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

        /*
         //是否选中
         holder.itemView.mShopCb.isChecked = model.isSelected
         holder.itemView.mShopTv.text = model.shopName

         //当一级选中时让所有二级全部选中
         for (item in mCameramanImageAdapter.dataList) {
             item.isSelected = holder.itemView.mShopCb.isChecked
         }
         Bus.send(UpdateTotalPriceEvent())

         //选中按钮事件
         holder.itemView.mShopCb.onClick {
             model.isSelected = holder.itemView.mShopCb.isChecked

             //当一级选中时让所有二级全部选中
             for (item in mCameramanImageAdapter.dataList) {
                 item.isSelected = holder.itemView.mShopCb.isChecked
             }
             mCameramanImageAdapter.notifyDataSetChanged()
             //当所有的一级全部选中时发送事件让最外边的全部CheckBox选中
             val isAllChecked = dataList.all { it.isSelected }
             Bus.send(CartAllCheckedEvent(isAllChecked))
         }

         Bus.observe<CartCheckedEvent>()
                 .subscribe { t: CartCheckedEvent ->
                     run {
                         //当所有的二级选中时让对应的一级选中
                         holder.itemView.mShopCb.isChecked = t.isAllChecked
                         model.isSelected = holder.itemView.mShopCb.isChecked
                         //当所有的一级全部选中时发送事件让最外边的全部CheckBox选中
                         val isAllChecked = dataList.all { it.isSelected }
                         Bus.send(CartAllCheckedEvent(isAllChecked))

                     }
                 }.registerInBus(context)

         Bus.observe<CartDeleteEvent>()
                 .subscribe { t: CartDeleteEvent ->
                     run {
                         //当所有的二级全部移除时让对应的一级移除
                         if (dataList.get(position).carts.size <= 0) {
                             dataList.removeAt(position)
                             notifyDataSetChanged()
                             Bus.send(CartDeleteAllEvent())
                         }

                     }
                 }.registerInBus(context)*/

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}
