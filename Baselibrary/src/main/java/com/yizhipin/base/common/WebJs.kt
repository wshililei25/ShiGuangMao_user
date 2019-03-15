package com.yizhipin.base.common

/**
 * Created by ${XiLei} on 2018/7/27.
 */
class WebJs {
    companion object {

        const val js = "<script type=\"text/javascript\">" +
                "var imgs = document.getElementsByTagName('img');" + // 找到img标签
                "for(var i = 0; i<imgs.length; i++){" +  // 逐个改变
                "imgs[i].style.width = '100%';" +  // 宽度改为100%
                "imgs[i].style.height = 'auto';" +
                "}" +
                "</script>"
    }
}