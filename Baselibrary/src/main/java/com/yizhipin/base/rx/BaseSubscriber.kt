package com.yizhipin.base.rx

import android.util.Log
import com.yizhipin.base.presenter.view.BaseView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class BaseSubscriber<T>(val baseView: BaseView) : Observer<T> {
    override fun onSubscribe(d: Disposable) {
        Log.d("XiLei", "1111111")
    }

    override fun onNext(t: T) {
        Log.d("XiLei", "2222222")
        baseView.hideLoading()
    }

    override fun onError(e: Throwable) {
        Log.d("XiLei", "333333="+e.message+"========"+e.cause)
        baseView.hideLoading()
        if (e is BaseException) {
            Log.d("XiLei", "4444")
            baseView.onError(e.msg)
        } else if (e is DataNullException) {
            Log.d("XiLei", "555555")
            baseView.onDataIsNull()
        }
    }

    override fun onComplete() {
        Log.d("XiLei", "66666666")
        baseView.hideLoading()
    }

}