package com.yizhipin.goods.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.eightbitlab.rxbus.Bus
import com.yizhipin.base.data.response.DressNormItem
import com.yizhipin.base.data.response.TimeMarketNorm
import com.yizhipin.base.data.response.TimeMarketNormItem
import com.yizhipin.base.event.MarketSkuChangedEvent
import com.yizhipin.goods.R
import com.yizhipin.goods.common.GoodsConstant
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.layout_sku_view.view.*

/*
    单个SKU
 */
class MarketSkuView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : FrameLayout(context, attrs, defStyle) {
    private lateinit var mGoodsSku: TimeMarketNorm

    init {
        View.inflate(context, R.layout.layout_sku_view, this)
    }

    /*
        动态设置SKU数据
     */
    fun setSkuData(goodsSku: TimeMarketNorm) {
        mGoodsSku = goodsSku
        mSkuTitleTv.text = goodsSku.norm

        //FlowLayout设置数据
        mSkuContentView.adapter = object : TagAdapter<TimeMarketNormItem>(goodsSku.items) {
            override fun getView(parent: FlowLayout?, position: Int, t: TimeMarketNormItem?): View {
                val view = LayoutInflater.from(context).inflate(R.layout.layout_sku_item, parent, false) as TextView
                view.text = t!!.item
                return view
            }
        }

        mSkuContentView.adapter.setSelectedList(0)
        mSkuContentView.setOnTagClickListener { _, position, _ ->

            if (mSkuContentView.selectedList.size > 0) {
                Bus.send(MarketSkuChangedEvent(mGoodsSku.items[position] ))
            } else {
                mSkuContentView.adapter.setSelectedList(setOf(position))
            }
            true
        }
    }

    /*
        获取选中的SKU
     */
    fun getSkuInfo(): String {
        return mSkuTitleTv.text.toString() + GoodsConstant.SKU_SEPARATOR +
                (mGoodsSku.items[mSkuContentView.selectedList.first()] as DressNormItem).item
    }
}
