package com.yizhipin.base.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnDismissListener
import com.bigkoo.alertview.OnItemClickListener
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yizhipin.base.common.BaseApplication
import com.yizhipin.base.injection.component.ActivityComponent
import com.yizhipin.base.injection.component.DaggerActivityComponent
import com.yizhipin.base.injection.moudule.ActivityModule
import com.yizhipin.base.injection.moudule.LifecycleProviderModule
import com.yizhipin.base.presenter.BasePresenter
import com.yizhipin.base.presenter.view.BaseView
import com.yizhipin.base.utils.DateUtils
import com.yizhipin.base.widgets.ProgressLoading
import org.devio.takephoto.app.TakePhoto
import org.devio.takephoto.app.TakePhotoImpl
import org.devio.takephoto.compress.CompressConfig
import org.devio.takephoto.model.InvokeParam
import org.devio.takephoto.model.TContextWrap
import org.devio.takephoto.model.TResult
import org.devio.takephoto.model.TakePhotoOptions
import org.devio.takephoto.permission.InvokeListener
import org.devio.takephoto.permission.PermissionManager
import org.devio.takephoto.permission.TakePhotoInvocationHandler
import org.jetbrains.anko.toast
import java.io.File
import javax.inject.Inject

/*
    存在选择图片的Activity基础封装
 */
abstract class BaseTakePhotoActivity<T : BasePresenter<*>> : BaseActivity(), BaseView, TakePhoto.TakeResultListener, InvokeListener {

    @Inject
    lateinit var mPresenter: T

    private var mTakePhoto: TakePhoto? = null
    private lateinit var mTempFile: File
    lateinit var mActivityComponent: ActivityComponent
    private lateinit var mLoadingDialog: ProgressLoading

    private var maxSize = 102400
    private var width = 800
    private var height = 800
    private var enableRawFile = 102400
    private var mInvokeParam: InvokeParam? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        getTakePhoto().onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        initActivityInjection()
        injectComponent()
        mLoadingDialog = ProgressLoading.create(this)
        ARouter.getInstance().inject(this)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        getTakePhoto().onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    fun getTakePhoto(): TakePhoto {
        if (mTakePhoto == null) {
            mTakePhoto = TakePhotoInvocationHandler.of(this).bind(TakePhotoImpl(this, this)) as TakePhoto
        }
        return mTakePhoto!!
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionManager.handlePermissionsResult(this, type, mInvokeParam, this)
    }

    override fun invoke(invokeParam: InvokeParam?): PermissionManager.TPermissionType {
        val type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam!!.method)
        if (PermissionManager.TPermissionType.WAIT == type) {
            this.mInvokeParam = invokeParam
        }
        return type
    }

    private fun initTakePhoto() {
        var config = CompressConfig.Builder().setMaxSize(maxSize)
                .setMaxPixel(if (width >= height) width else height)
                .enableReserveRaw(false) //是否保存压缩后的图片
                .create()
        mTakePhoto!!.onEnableCompress(config, false) //压缩图片

        val builder = TakePhotoOptions.Builder()
        mTakePhoto!!.setTakePhotoOptions(builder.create())
    }

    /*
        Dagger注册
     */
    protected abstract fun injectComponent()

    /*
        初始化Activity Component
     */
    private fun initActivityInjection() {
        mActivityComponent = DaggerActivityComponent.builder().appComponent((application as BaseApplication).mAppComponent)
                .activityModule(ActivityModule(this))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()

    }

    /*
        显示加载框，默认实现
     */
    override fun showLoading() {
        mLoadingDialog.showLoading()
    }

    /*
        隐藏加载框，默认实现
     */
    override fun hideLoading() {
        mLoadingDialog.hideLoading()
    }

    /*
        错误信息提示，默认实现
     */
    override fun onError(text: String) {
        toast(text)
    }

    /**
     * 弹出选择框，默认实现  可根据实际情况，自行修改
     *
     * 单张图片选取
     */
    protected fun showAlertView() {
        AlertView("选择图片", "", "取消", null, arrayOf("拍照", "相册"), this,
                AlertView.Style.ActionSheet, OnItemClickListener { o, position ->

            RxPermissions(this).request(android.Manifest.permission.CAMERA
                    , android.Manifest.permission.READ_EXTERNAL_STORAGE
                    , android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe({ granted ->
                        if (granted) {
                            initTakePhoto()
                            when (position) {
                                0 -> {
                                    createTempFile()
                                    mTakePhoto!!.onPickFromCapture(Uri.fromFile(mTempFile))
                                }
                                1 -> mTakePhoto!!.onPickFromGallery()
                            }
                        } else {
                            Log.d("XiLei", "请开启相机、存储权限")
                        }
                    })
        }

        ).show()
    }

    /**
     * 弹出选择框，默认实现  可根据实际情况，自行修改
     *
     * 多张图片选取
     */
    protected fun showAlertViewMore() {
        AlertView("选择图片", "", "取消", null, arrayOf("拍照", "相册"), this,
                AlertView.Style.ActionSheet, OnItemClickListener { o, position ->
            when (position) {
                0 -> {
                    createTempFile()
                    mTakePhoto!!.onPickFromCapture(Uri.fromFile(mTempFile))
                }
                1 -> {
                    mTakePhoto!!.onPickMultiple(6)

                }
            }
        }

        ).setOnDismissListener(object : OnDismissListener {
            override fun onDismiss(o: Any?) {

            }

        }).show()
    }

    /*
        获取图片，成功回调
     */
    override fun takeSuccess(result: TResult?) {
        Log.d("TakePhoto", result?.image?.originalPath)
        Log.d("TakePhoto", result?.image?.compressPath)
    }

    /*
        获取图片，取消回调
     */
    override fun takeCancel() {
    }

    /*
        获取图片，失败回调
     */
    override fun takeFail(result: TResult?, msg: String?) {
        Log.e("takePhoto", msg)
    }

    /**
     *  新建临时文件
     */
    fun createTempFile() {
        val tempFileName = "${DateUtils.curTime}.png"
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            this.mTempFile = File(Environment.getExternalStorageDirectory(), tempFileName)
            return
        }

        this.mTempFile = File(filesDir, tempFileName)
    }

}
