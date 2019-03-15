package com.yizhipin.base.ui.dialog

import android.app.ActionBar.LayoutParams
import android.app.Dialog
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import com.eightbitlab.rxbus.Bus
import com.yizhipin.base.R
import com.yizhipin.base.event.ComplainTypeCheckedEvent
import com.yizhipin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yizhipin.base.ui.adapter.ComplainTypeAdapter
import com.yizhipin.usercenter.data.response.ComplainType
import kotlinx.android.synthetic.main.layout_dialog_type.*

/**
 * Created by ${XiLei} on 2017/2/10.
 */
class ComplainTypeDialog(context: Context) : Dialog(context, R.style.HB_Dialog) {

    private lateinit var mTypeAdapter: ComplainTypeAdapter

    init {
        this.window!!.setWindowAnimations(R.style.animinandout)
        initView(context)
    }

    private fun initView(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_type, null)
        setContentView(view)
        setCanceledOnTouchOutside(true)
        val window = window
        val params = window!!.attributes
        params.width = LayoutParams.MATCH_PARENT
        params.height = LayoutParams.WRAP_CONTENT
        params.gravity = Gravity.BOTTOM
        window.attributes = params

        var list = mutableListOf<ComplainType>()
        list.add(ComplainType("COMMON", "常规问题"))
        list.add(ComplainType("SERVICE", "服务问题"))
        list.add(ComplainType("TEACHER", "老师问题"))
        mRv.layoutManager = LinearLayoutManager(context!!)
        mTypeAdapter = ComplainTypeAdapter(context!!)
        mRv.adapter = mTypeAdapter
        mTypeAdapter.dataList = list

        mTypeAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<ComplainType> {
            override fun onItemClick(item: ComplainType, position: Int) {
                Bus.send(ComplainTypeCheckedEvent(item))
                dismiss()
            }

        })
    }

}
