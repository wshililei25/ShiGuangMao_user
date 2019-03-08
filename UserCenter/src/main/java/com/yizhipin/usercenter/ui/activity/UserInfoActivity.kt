package com.yizhipin.usercenter.ui.activity

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import com.alibaba.sdk.android.oss.*
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback
import com.alibaba.sdk.android.oss.common.OSSLog
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider
import com.alibaba.sdk.android.oss.common.auth.OSSCustomSignerCredentialProvider
import com.alibaba.sdk.android.oss.model.PutObjectRequest
import com.alibaba.sdk.android.oss.model.PutObjectResult
import com.jph.takephoto.model.TResult
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.response.FeeRecord
import com.yizhipin.base.data.response.Store
import com.yizhipin.base.data.response.UserInfo
import com.yizhipin.base.ext.enable
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseTakePhotoActivity
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.base.utils.UploadUtil
import com.yizhipin.usercenter.R
import com.yizhipin.usercenter.injection.component.DaggerUserComponent
import com.yizhipin.usercenter.injection.module.UserModule
import com.yizhipin.usercenter.presenter.UserInfoPresenter
import com.yizhipin.usercenter.presenter.view.UserInfoView
import com.yizhipin.usercenter.utils.UserPrefsUtils
import kotlinx.android.synthetic.main.activity_user_info.*
import org.jetbrains.anko.toast
import java.io.File
import java.text.SimpleDateFormat

/**
 * Created by ${XiLei} on 2018/7/26.
 * 基本信息
 */
class UserInfoActivity : BaseTakePhotoActivity<UserInfoPresenter>(), UserInfoView, View.OnClickListener, UploadUtil.OnUploadProcessListener {

    private var mRemoteFileUrl: String = ""
    private var mOssSign: String = ""

    private lateinit var oss: OSS
//    private lateinit var mMyOSSUtils: MyOSSUtils
    private lateinit var mOSSCredentialProvider: OSSCredentialProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        initView()

//        mMyOSSUtils = MyOSSUtils.getInstance()
      /*  var handler = object : Handler() {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                initOss()
                initOss(this@UserInfoActivity, "http://oss-cn-hangzhou.aliyuncs.com"
                        , "LTAI1WzOdcWDGWNl", "X2AvzuKH8Zs2YGcLwweXG34POMdXa6"
                        , "", mOSSCredentialProvider)
            }
        }*/

        Thread(object :Runnable{
            override fun run() {
                initOss()
                initOss(this@UserInfoActivity, "http://oss-cn-hangzhou.aliyuncs.com"
                        , "LTAI1WzOdcWDGWNl", "X2AvzuKH8Zs2YGcLwweXG34POMdXa6"
                        , "", mOSSCredentialProvider)
            }

        }).start()
    }

    private fun initView() {

        mBackIv.onClick(this)
        mUserIconView.onClick(this)
        mConfirmBtn.onClick(this)
        mConfirmBtn.enable(mNickEt, { isBtnEnable() })
    }

    private fun initOss() {
        Log.d("XiLei", "111111111")
        mOSSCredentialProvider = object : OSSCustomSignerCredentialProvider() {

            override fun signContent(content: String): String {
                // 您需要在这里依照OSS规定的签名算法，实现加签一串字符内容，并把得到的签名传拼接上AccessKeyId后返回
                // 一般实现是，将字符内容post到您的业务服务器，然后返回签名
                // 如果因为某种原因加签失败，描述error信息后，返回nil
                // 以下是用本地算法进行的演示
                Log.d("XiLei", "content:$content")
//                createSingleOssAk(content)

                var map = mutableMapOf<String, String>()
                map.put("content", content)
                mPresenter.getOssSign(map)

                return mOssSign//"OSS " + AccessKeyId + ":" + OSSUtils.base64(hmac - sha1(SecretKeyId, content));
            }
        }
    }

    override fun onGetOssSignSuccess(result: String) {
        mOssSign = result
    }

    override fun onStart() {
        super.onStart()
        initData()
    }

    private fun initData() {
        var map = mutableMapOf<String, String>()
        map.put("id", AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN))
        mPresenter.getUserInfo(map)
    }

    /*
        Dagger注册
     */
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.mRightTv, R.id.mBackIv -> finish()

            R.id.mUserIconView -> showAlertView()

            R.id.mConfirmBtn -> {

                var map = mutableMapOf<String, String>()
                map.put("nickname", mNickEt.text.toString())
                map.put("imgurl", mRemoteFileUrl)
                mPresenter.editUserInfo(map)
            }

        }
    }

    /**
     * 获取图片成功回调
     */
    override fun takeSuccess(result: TResult?) {
        if (!mPresenter.checkNetWork()) {
            return
        }
        val localFileUrl = result?.image?.compressPath

        Log.d("XiLei", "localFileUrl=" + localFileUrl);
        OssUploadImg("timecats-yunpan", File(localFileUrl).getName(), localFileUrl!!)

        /* var mMyOSSUtils = MyOSSUtils.getInstance()
         mMyOSSUtils.upImage(this, object : MyOSSUtils.OssUpCallback {
             override fun successImg(img_url: String?) {
                 Log.d("XiLei", "successImg");
             }

             override fun successVideo(video_url: String?) {
                 Log.d("XiLei", "video_url");
             }

             override fun inProgress(progress: Long, zong: Long) {
                 Log.d("XiLei", "progress=" + progress);
             }

         }, "aaa", localFileUrl)*/

        /* val fileKey = "avatarFile"
         val uploadUtil = UploadUtil.getInstance()
         uploadUtil.setOnUploadProcessListener(this@UserInfoActivity) //设置监听器监听上传状态

         showLoading()
         val filepath = File(localFileUrl)
         uploadUtil.uploadFile(filepath, fileKey, BaseConstant.SERVICE_ADDRESS + "file/img", HashMap<String, String>())*/
    }

    /**
     * 上传图片成功
     */
    override fun onUploadDone(responseCode: Int, message: String) {
        runOnUiThread {
            hideLoading()
            toast(R.string.upload_success)
            mRemoteFileUrl = message
            mUserIconIv.loadUrl(mRemoteFileUrl)
        }
    }

    override fun onUploadProcess(uploadSize: Int) {
    }

    override fun initUpload(fileSize: Int) {
    }

    /**
     * 获取用户信息成功
     */
    override fun getUserResult(result: UserInfo) {

        mRemoteFileUrl = result.imgurl
        mNickEt.setText(result.nickname)
        mNickEt.setSelection(result.nickname.length)
        /*if (result.mobile != "") {
            mMobileEt.setText(result.mobile)
            mMobileIv.visibility = View.GONE
            mMobileView.isEnabled = false
        }*/
        /*   if (result.weixin != "") {
               mWeChatEt.setText(result.mobile)
               mWeChatIv.visibility = View.GONE
               mWeChatView.isEnabled = false
           }*/
        if (result.imgurl != "") {
            mUserIconIv.loadUrl(result.imgurl)
        }

    }

    /**
     * 编辑用户资料成功
     */
    override fun onEditUserResult(result: UserInfo) {
        UserPrefsUtils.putUserInfo(result)
        finish()
    }

    private fun isBtnEnable(): Boolean {
        return mConfirmBtn.text.isNullOrEmpty().not()
    }

    override fun onGetDefaultStoreSuccess(result: Store) {
    }

    override fun getUnReadNewCount(result: Int) {
    }

    override fun getFeeRecordListSuccess(result: MutableList<FeeRecord>) {
    }

    fun initOss(context: Context, endpoint: String, AccessKeyId: String, SecretKeyId: String, SecurityToken: String, credentialProvider: OSSCredentialProvider) {
        // 在移动端建议使用STS方式初始化OSSClient。
        // 更多信息可查看sample 中 sts 使用方式(https://github.com/aliyun/aliyun-oss-android-sdk/tree/master/app/src/main/java/com/alibaba/sdk/android/oss/app)
        //        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(
        //                AccessKeyId, SecretKeyId, SecurityToken);
        //        自签名模式


        //该配置类如果不设置，会有默认配置，具体可看该类
        val conf = ClientConfiguration()
        conf.connectionTimeout = 15 * 1000 // 连接超时，默认15秒
        conf.socketTimeout = 15 * 1000 // socket超时，默认15秒
        conf.maxConcurrentRequest = 5 // 最大并发请求数，默认5个
        conf.maxErrorRetry = 2 // 失败后最大重试次数，默认2次
        //开启可以在控制台看到日志，并且会支持写入手机sd卡中的一份日志文件位置在SDCard_path\OSSLog\logs.csv  默认不开启
        //日志会记录oss操作行为中的请求数据，返回数据，异常信息
        //例如requestId,response header等
        //android_version：5.1  android版本
        //mobile_model：XT1085  android手机型号
        //network_state：connected  网络状况
        //network_type：WIFI 网络连接类型
        //具体的操作行为信息:
        //[2017-09-05 16:54:52] - Encounter local execpiton: //java.lang.IllegalArgumentException: The bucket name is invalid.
        //A bucket name must:
        //1) be comprised of lower-case characters, numbers or dash(-);
        //2) start with lower case or numbers;
        //3) be between 3-63 characters long.
        //------>end of log
        OSSLog.enableLog()
        oss = OSSClient(context, endpoint, credentialProvider)
    }


    fun OssUploadImg(bucketName: String, objectKey: String, uploadimage: String) {
        // 构造上传请求
        val put = PutObjectRequest(bucketName, objectKey, uploadimage)
        // 异步上传时可以设置进度回调
        put.progressCallback = OSSProgressCallback { request, currentSize, totalSize ->
            Log.d("XiLei", "currentSize: $currentSize totalSize: $totalSize")
            //                uploadimgFlag = false;
        }
        val task = oss.asyncPutObject(put, object : OSSCompletedCallback<PutObjectRequest, PutObjectResult> {
            override fun onSuccess(request: PutObjectRequest, result: PutObjectResult) {
                Log.d("XiLei", "UploadSuccess")
                Log.d("XiLei", "PutObject==result:$result")
                //                uploadimgFlag = true;
            }

            override fun onFailure(request: PutObjectRequest, clientExcepion: ClientException?, serviceException: ServiceException?) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace()
                    Log.d("XiLei", "clientExcepion.getMessage()=" + clientExcepion.message)
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.d("XiLei", "serviceException.getErrorCode()=" + serviceException.errorCode)
                    Log.d("XiLei", "serviceException.requestId=" +serviceException.requestId)
                    Log.d("XiLei", "serviceException.hostId()=" +serviceException.hostId)
                    Log.d("XiLei","serviceException.rawMessage()=" + serviceException.rawMessage)
                }
                //                uploadimgFlag = true;
            }
        })
        // task.cancel(); // 可以取消任务

    }
}
