package com.yizhipin.base.ui.dialog

import android.app.ActionBar.LayoutParams
import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import com.yizhipin.base.R
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.web.ContentWebActivity
import kotlinx.android.synthetic.main.layout_dialog_custom.*
import org.jetbrains.anko.startActivity

/**
 * Created by ${XiLei} on 2017/2/10.
 */
class CustomDialog(context: Context) : Dialog(context, R.style.HB_Dialog) {

    init {
        this.window!!.setWindowAnimations(R.style.animinandout)
        initView(context)
    }

    private fun initView(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_custom, null)
        setContentView(view)
        setCanceledOnTouchOutside(true)
        val window = window
        val params = window!!.attributes
        params.width = LayoutParams.MATCH_PARENT
        params.height = LayoutParams.WRAP_CONTENT
        params.gravity = Gravity.CENTER
        window.attributes = params

        mCloseIv.onClick {
            dismiss()
        }
        mBg.onClick {
            context.startActivity<ContentWebActivity>()
        }
    }

}
