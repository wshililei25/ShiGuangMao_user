package com.yizhipin.base.ui.dialog

import android.app.ActionBar.LayoutParams
import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import com.yizhipin.base.R
import com.yizhipin.base.ext.onClick
import kotlinx.android.synthetic.main.layout_dialog_red.*

/**
 * Created by ${XiLei} on 2017/2/10.
 */
class RedPackageDialog(context: Context) : Dialog(context, R.style.HB_Dialog) {

    init {
        this.window!!.setWindowAnimations(R.style.animinandout)
        initView(context)
    }

    private fun initView(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_red, null)
        setContentView(view)
        setCanceledOnTouchOutside(true)
        val window = window
        val params = window!!.attributes
        params.width = LayoutParams.WRAP_CONTENT
        params.height = LayoutParams.WRAP_CONTENT
        params.gravity = Gravity.CENTER
        window.attributes = params

        mView.onClick {
            dismiss()
        }
    }

}
