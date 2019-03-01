package com.yizhipin.base.rx

import android.util.Log
import com.yizhipin.base.common.BaseResultCode
import com.yizhipin.base.data.protocol.BaseResp
import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * Created by ${XiLei} on 2018/8/5.
 * 无分页数据时使用
 */
class BaseFunc<T> : Function<BaseResp<T>, Observable<T>> {
    override fun apply(t: BaseResp<T>): Observable<T> {
        Log.d("XiLei", "aaaaa")
        if (!t.code.equals(BaseResultCode.SUCCESS)) {
            Log.d("XiLei", "bbbbb")
            Log.d("XiLei", "t.data=" + t.data)
            return Observable.error(BaseException(t.code, t.msg, t.data))
        }

        if (t.data == null) {
            Log.d("XiLei", "cccccc")
            return Observable.error(DataNullException())
        }
        Log.d("XiLei", "dddddd")
        return Observable.just(t.data)
    }
}