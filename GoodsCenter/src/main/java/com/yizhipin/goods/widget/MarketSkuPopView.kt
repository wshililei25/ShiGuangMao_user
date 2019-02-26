package com.yizhipin.goods.widget

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.PopupWindow
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.yizhipin.base.data.response.TimeSuperMarket
import com.yizhipin.base.event.DressBuyEvent
import com.yizhipin.base.event.SkuChangedEvent
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ext.onClick
import com.yizhipin.goods.R
import com.yizhipin.goods.common.GoodsConstant
import kotlinx.android.synthetic.main.layout_sku_pop.view.*

/*
    商品SKU弹层
 */
class MarketSkuPopView(context: Activity) : PopupWindow(context), View.OnClickListener {
    //根视图
    private val mRootView: View
    private val mContext: Context
    private val mSkuViewList = arrayListOf<MarketSkuView>()

    private lateinit var mDressDetails: TimeSuperMarket
    private var mInventory = 0 //库存

    init {
        mContext = context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mRootView = inflater.inflate(R.layout.layout_sku_pop, null)

        initView()
        initObserve()

        // 设置SelectPicPopupWindow的View
        this.contentView = mRootView
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.width = LayoutParams.MATCH_PARENT
        // 设置SelectPicPopupWindow弹出窗体的高
        this.height = LayoutParams.MATCH_PARENT
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.isFocusable = true
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.animationStyle = R.style.AnimBottom
        background.alpha = 150
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mRootView.setOnTouchListener { _, event ->
            val height = mRootView.findViewById<View>(R.id.mPopView).top
            val y = event.y.toInt()
            if (event.action == MotionEvent.ACTION_UP) {
                if (y < height) {
                    dismiss()
                }
            }
            true
        }
    }

    private fun initView() {
        mRootView.mAddCartBtn.onClick {
            Bus.send(DressBuyEvent(mRootView.mNormTv.text.toString(),mInventory))
            dismiss()
        }
    }

    /*
        设置商品图标
     */
    fun setGoodsIcon(text: String) {

    }

    /*
       设置商品名称
    */
    fun setGoodsName(text: String) {

    }

    /*
        设置商品价格
     */
    fun setGoodsPrice(text: String) {

    }

    /*
        设置商品SKU
     */
    fun setSkuData(details: TimeSuperMarket) {
        mDressDetails = details
        mRootView.mGoodsIconIv.loadUrl(mDressDetails.imgurl)
        mRootView.mNameTv.text = mDressDetails.title
        mRootView.mPriceTv.text = "¥" + mDressDetails.amount
        setGoodsNum(mDressDetails.norms[0].items[0].inventory.toInt())

        var str = ""
        for (goodSku in details.norms) {
            str += goodSku.items[0].item + GoodsConstant.SKU_SEPARATOR

            val skuView = MarketSkuView(mContext)
            skuView.setSkuData(goodSku)
            mSkuViewList.add(skuView)
            mRootView.mSkuView.addView(skuView)
        }
        mRootView.mNormTv.text = str.take(str.length - 1)//刪除最后一个分隔
    }

    /*
        获取选中的SKU
     */
    fun getSelectSku(): String {
        var skuInfo = ""
        for (skuView in mSkuViewList) {
            skuInfo += skuView.getSkuInfo().split(GoodsConstant.SKU_SEPARATOR)[1] + GoodsConstant.SKU_SEPARATOR
        }
        return skuInfo.take(skuInfo.length - 1)//刪除最后一个分隔
    }

    /*
        获取商品数量
     */
//    fun getSelectCount() = mRootView.mSkuCountBtn.number

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mAddCartBtn -> {
                dismiss()
            }
        }
    }

    /**
     * 监听SKU变化
     */
    private fun initObserve() {
        Bus.observe<SkuChangedEvent>()
                .subscribe { t: SkuChangedEvent ->
                    run {
                        mRootView.mNormTv.text = getSelectSku()
                        setGoodsNum(t.dressNormItem.inventory)
                    }

                }
                .registerInBus(this)
    }

    /*
      设置库存
   */
    fun setGoodsNum(text: Int) {
        mInventory = text
        mRootView.mNumTv.text = "库存  " + text + "件"
    }
}
