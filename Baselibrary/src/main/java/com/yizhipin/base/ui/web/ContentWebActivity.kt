package com.yizhipin.base.ui.web

import android.os.Bundle
import com.yizhipin.base.R
import com.yizhipin.base.ui.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_content_web.*

/**
 * Created by ${XiLei} on 2018/9/22.
 */
class ContentWebActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_web)

        initView()

    }

    private fun initView() {
        var content = "<p>亲爱的顾客：</p><p>您好！每张婚纱照简单却十分不平凡，一张婚纱照在一家成熟的婚纱摄影店，需要经过、选套系、定档期、试服装 、拍摄当天、选片、校稿、取件的流程。中间需要至少经过12个工作人员服务，28天左右，是在服务行业当中产品线服务周期最长的品类，时光旅景婚纱摄影在过去多年的世纪经营中，率先简化了多个婚纱照过程，未来将会致力于让婚纱照更简单</p><p>特别提醒：</p><p>1、在拍摄或化妆过程中觉得不喜欢，请立即提出您的看法，与工作人员进行有效的沟通</p><p>2、拍摄棚内有较多高级电子感应设备，为维护器材设备及确保拍摄品质，严禁新人使用摄像、录像等设备</p><p>3、如遇新人拍摄当天身体不适，或外景天气状况不佳，本公司有权随时停止拍摄或变更拍摄流程。</p><p>4、如新郎/新娘，皮肤比较敏感或已过敏，也可自行携带个人专用隔离底妆或彩妆等。</p><p>（化妆前询问专业老师可否使用）</p><p><br/></p><p>一颗愉快的心，是记录美好爱情的最佳时刻<br/></p><p><br/></p><p>时光旅景董事长：弓鹏</p>"

        mWebView.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null)
    }

}