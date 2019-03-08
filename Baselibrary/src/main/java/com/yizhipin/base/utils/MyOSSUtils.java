//package com.yizhipin.base.utils;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.alibaba.sdk.android.oss.ClientConfiguration;
//import com.alibaba.sdk.android.oss.ClientException;
//import com.alibaba.sdk.android.oss.OSS;
//import com.alibaba.sdk.android.oss.OSSClient;
//import com.alibaba.sdk.android.oss.ServiceException;
//import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
//import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
//import com.alibaba.sdk.android.oss.common.OSSLog;
//import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
//import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
//import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
//import com.alibaba.sdk.android.oss.model.OSSRequest;
//import com.alibaba.sdk.android.oss.model.OSSResult;
//import com.alibaba.sdk.android.oss.model.PutObjectRequest;
//import com.alibaba.sdk.android.oss.model.PutObjectResult;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
///**
// * @author: XiLei  @date: 2019/3/5
// */
//public class MyOSSUtils {
//
//    private static MyOSSUtils instance;
//
//    private final String P_ENDPOINT = "http://oss-cn-hangzhou.aliyuncs.com";//主机地址（OSS文档中有提到）
//
//    private final String P_STSSERVER = "http://timecats-yunpan.oss-cn-hangzhou.aliyuncs.com";//（服务器域名）
//
//    private final String P_BUCKETNAME = "timecats-yunpan";//（文件夹名字）
//
//    private OSS oss;
//
//    private SimpleDateFormat simpleDateFormat;
//
//    public MyOSSUtils() {
//
//    }
//
//    public static MyOSSUtils getInstance() {
//
//        if (instance == null) {
//
//            if (instance == null) {
//
//                return new MyOSSUtils();
//
//            }
//
//        }
//
//        return instance;
//
//    }
//
//    private void getOSs(Context context) {
//
//        //推荐使用OSSAuthCredentialsProvider。token过期可以及时更新
//        OSSCredentialProvider credentialProvider = new OSSAuthCredentialsProvider(P_STSSERVER);
//        //该配置类如果不设置，会有默认配置，具体可看该类
//        ClientConfiguration conf = new ClientConfiguration();
//        conf.setConnectionTimeout(15 * 1000);// 连接超时，默认15秒
//        conf.setSocketTimeout(15 * 1000);// socket超时，默认15秒
//        conf.setMaxConcurrentRequest(5);// 最大并发请求数，默认5个
//        conf.setMaxErrorRetry(2);// 失败后最大重试次数，默认2次
//        oss = new OSSClient(context, P_ENDPOINT, credentialProvider, conf);
//        if (simpleDateFormat == null) {
//            simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//        }
//
//    }
//
//    /**
//     * 上传图片 上传文件
//     *
//     * @param context       application上下文对象
//     * @param ossUpCallback 成功的回调
//     * @param img_name      上传到oss后的文件名称，图片要记得带后缀 如：.jpg
//     * @param imgPath       图片的本地路径
//     */
//    public void upImage(Context context, final MyOSSUtils.OssUpCallback ossUpCallback, final String img_name, String imgPath) {
//
//        getOSs(context);
//
//        final Date data = new Date();
//        data.setTime(System.currentTimeMillis());
//
//        PutObjectRequest putObjectRequest = new PutObjectRequest(P_BUCKETNAME, simpleDateFormat.format(data) + "/" + img_name, imgPath);
//        Log.e("XiLei", "objectKey=" + simpleDateFormat.format(data) + "/" + img_name);
//        putObjectRequest.setProgressCallback(new OSSProgressCallback() {
//
//            @Override
//            public void onProgress(Object request, long currentSize, long totalSize) {
//                ossUpCallback.inProgress(currentSize, totalSize);
//            }
//
//           /* @Override
//            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//
//            }*/
//
//        });
//
//        oss.asyncPutObject(putObjectRequest, new OSSCompletedCallback() {
//
//            @Override
//            public void onSuccess(OSSRequest request, OSSResult result) {
//                Log.e("XiLei", "------getRequestId:" + result.getRequestId());
//
//// try {
//
//                ossUpCallback.successImg(oss.presignPublicObjectURL(P_BUCKETNAME, simpleDateFormat.format(data) + "/" + img_name));
//
//// } catch (ClientException e) {
//
//// e.printStackTrace();
//
//// }
//            }
//
//            @Override
//            public void onFailure(OSSRequest request, ClientException clientException, ServiceException serviceException) {
//                Log.e("XiLei", "------onFailure:");
//                Log.e("XiLei", "clientException=" + clientException.getMessage());
//                Log.e("XiLei", "serviceException=" + serviceException.getMessage());
//                ossUpCallback.successImg(null);
//            }
//
//           /* @Override
//            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
//            }
//
//            @Override
//            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
//            }*/
//
//        });
//
//    }
//
//    /**
//     * 上传图片 上传流
//     *
//     * @param context       application上下文对象
//     * @param ossUpCallback 成功的回调
//     * @param img_name      上传到oss后的文件名称，图片要记得带后缀 如：.jpg
//     * @param imgbyte       图片的byte数组
//     */
//
//    public void upImage(Context context, final MyOSSUtils.OssUpCallback ossUpCallback, final String img_name, byte[] imgbyte) {
//
//        getOSs(context);
//
//        final Date data = new Date();
//
//        data.setTime(System.currentTimeMillis());
//
//        PutObjectRequest putObjectRequest = new PutObjectRequest(P_BUCKETNAME, simpleDateFormat.format(data) + "/" + img_name, imgbyte);
//
//        putObjectRequest.setProgressCallback(new OSSProgressCallback() {
//
//            @Override
//            public void onProgress(Object request, long currentSize, long totalSize) {
//                ossUpCallback.inProgress(currentSize, totalSize);
//            }
//
//           /* @Override
//            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//            }*/
//
//        });
//
//        oss.asyncPutObject(putObjectRequest, new OSSCompletedCallback() {
//
//            @Override
//            public void onSuccess(OSSRequest request, OSSResult result) {
//                Log.e("MyOSSUtils", "------getRequestId:" + result.getRequestId());
//                ossUpCallback.successImg(oss.presignPublicObjectURL(P_BUCKETNAME, simpleDateFormat.format(data) + "/" + img_name));
//            }
//
//            @Override
//            public void onFailure(OSSRequest request, ClientException clientException, ServiceException serviceException) {
//                ossUpCallback.successImg(null);
//            }
//
//           /* @Override
//            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
//            }
//            @Override
//            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
//            }*/
//
//        });
//
//    }
//
//    /**
//     * 上传视频
//     *
//     * @param context       application上下文对象
//     * @param ossUpCallback 成功的回调
//     * @param video_name    上传到oss后的文件名称，视频要记得带后缀 如：.mp4
//     * @param video_path    视频的本地路径
//     */
//
//    public void upVideo(Context context, final MyOSSUtils.OssUpCallback ossUpCallback, final String video_name, String video_path) {
//
//        getOSs(context);
//
//        final Date data = new Date();
//
//        data.setTime(System.currentTimeMillis());
//
//        PutObjectRequest putObjectRequest = new PutObjectRequest(P_BUCKETNAME, simpleDateFormat.format(data) + "/" + video_name, video_path);
//
//        putObjectRequest.setProgressCallback(new OSSProgressCallback() {
//
//            @Override
//            public void onProgress(Object request, long currentSize, long totalSize) {
//                ossUpCallback.inProgress(currentSize, totalSize);
//            }
//
//           /* @Override
//            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//            }*/
//
//        });
//
//        oss.asyncPutObject(putObjectRequest, new OSSCompletedCallback() {
//
//            @Override
//            public void onSuccess(OSSRequest request, OSSResult result) {
//                ossUpCallback.successVideo(oss.presignPublicObjectURL(P_BUCKETNAME, simpleDateFormat.format(data) + "/" + video_name));
//            }
//
//            @Override
//            public void onFailure(OSSRequest request, ClientException clientException, ServiceException serviceException) {
//                ossUpCallback.successVideo(null);
//            }
//
//           /* @Override
//            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
//            }
//            @Override
//            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
//            }*/
//
//        });
//
//    }
//
//    public interface OssUpCallback {
//
//        void successImg(String img_url);
//
//        void successVideo(String video_url);
//
//        void inProgress(long progress, long zong);
//
//    }
//
//    public void initOss(Context context, String endpoint, final String AccessKeyId, final String SecretKeyId, String SecurityToken, OSSCredentialProvider credentialProvider) {
//// 在移动端建议使用STS方式初始化OSSClient。
//// 更多信息可查看sample 中 sts 使用方式(https://github.com/aliyun/aliyun-oss-android-sdk/tree/master/app/src/main/java/com/alibaba/sdk/android/oss/app)
////        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(
////                AccessKeyId, SecretKeyId, SecurityToken);
////        自签名模式
//
//
////该配置类如果不设置，会有默认配置，具体可看该类
//        ClientConfiguration conf = new ClientConfiguration();
//        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
//        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
//        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
//        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
////开启可以在控制台看到日志，并且会支持写入手机sd卡中的一份日志文件位置在SDCard_path\OSSLog\logs.csv  默认不开启
////日志会记录oss操作行为中的请求数据，返回数据，异常信息
////例如requestId,response header等
////android_version：5.1  android版本
////mobile_model：XT1085  android手机型号
////network_state：connected  网络状况
////network_type：WIFI 网络连接类型
////具体的操作行为信息:
////[2017-09-05 16:54:52] - Encounter local execpiton: //java.lang.IllegalArgumentException: The bucket name is invalid.
////A bucket name must:
////1) be comprised of lower-case characters, numbers or dash(-);
////2) start with lower case or numbers;
////3) be between 3-63 characters long.
////------>end of log
//        OSSLog.enableLog();
//        oss = new OSSClient(context, endpoint, credentialProvider);
//    }
//
//    public void OssUploadImg(String bucketName, String objectKey, String uploadimage) {
//        // 构造上传请求
//        PutObjectRequest put = new PutObjectRequest(bucketName, objectKey, uploadimage);
//// 异步上传时可以设置进度回调
//        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
//            @Override
//            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//                Log.d("XiLei", "currentSize: " + currentSize + " totalSize: " + totalSize);
////                uploadimgFlag = false;
//            }
//        });
//        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
//            @Override
//            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
//                Log.d("XiLei", "UploadSuccess");
//                Log.d("XiLei", "PutObject==result:" + result.toString());
////                uploadimgFlag = true;
//            }
//
//            @Override
//            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
//                // 请求异常
//                if (clientExcepion != null) {
//                    // 本地异常如网络异常等
//                    clientExcepion.printStackTrace();
//                    Log.d("XiLei", "clientExcepion.getMessage()=" + clientExcepion.getMessage());
//                }
//                if (serviceException != null) {
//                    // 服务异常
//                    Log.d("XiLei", "serviceException.getErrorCode()=" + serviceException.getErrorCode());
//                    Log.d("XiLei", serviceException.getRequestId());
//                    Log.d("XiLei", serviceException.getHostId());
//                    Log.d("XiLei", serviceException.getRawMessage());
//                }
////                uploadimgFlag = true;
//            }
//        });
//// task.cancel(); // 可以取消任务
//
//    }
//
//}