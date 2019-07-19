package com.yizhipin.base.data.net

import com.yizhipin.base.common.BaseConstant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by ${XiLei} on 2018/7/27.
 */
class RetrofitFactoryGet {

    private val mRetrofit: Retrofit
    private val mInterceptor: Interceptor

    init {

        mInterceptor = Interceptor { chain ->
            val request = chain.request()
                    .newBuilder()
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("charset", "utf-8")
                    .get()
                    .build()
            chain.proceed(request)
        }

        mRetrofit = Retrofit.Builder()
                .baseUrl(BaseConstant.SERVICE_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())//数据转换器，用来将HTTP请求返回结果由F类型转换为T类型，或者将HTTP请求体F转换为T
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//请求适配器，用于将Retrofit2.Call<T>对象转换为Observable<T>类型
                .client(initClient())
                .build()
    }

    private fun initClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(mInterceptor)
                .addInterceptor(initLogInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()
    }

    private fun initLogInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    fun <T> create(service: Class<T>): T {
        return mRetrofit.create(service)
    }
}