package com.yizhipin.base.rx

import com.yizhipin.base.presenter.view.BaseView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


/**
 * Created by ${XiLei} on 2018/7/26.
 */
open class BaseSubscriber<T>(val baseView: BaseView) : Observer<T> {
    override fun onSubscribe(d: Disposable) {
    }

    override fun onNext(t: T) {
        baseView.hideLoading()
    }

    /**
     * 当不会再有新的 onNext() 发出时，需要触发 onCompleted() 方法作为标志
     */
    override fun onComplete() {
        baseView.hideLoading()
    }

    override fun onError(e: Throwable) {
        baseView.hideLoading()
        if (e is BaseException) {
            baseView.onError(e.msg)
        } else if (e is DataNullException) {
            baseView.onDataIsNull()
        }
    }

}