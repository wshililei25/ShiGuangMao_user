package com.yizhipin.goods.ui.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Cameraman
import com.yizhipin.base.data.response.CameranmanWorks
import com.yizhipin.base.data.response.Evaluate
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.goods.R
import com.yizhipin.goods.injection.component.DaggerGoodsComponent
import com.yizhipin.goods.injection.module.GoodsModule
import com.yizhipin.goods.presenter.CameramanDetailsPresenter
import com.yizhipin.goods.presenter.view.CameramanDetailsView
import com.yizhipin.goods.ui.adapter.CameramanWorksAdapter
import com.yizhipin.goods.ui.adapter.EvaluateAdapter
import com.yizhipin.provider.common.afterLogin
import com.yizhipin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_cameraman_details.*
import org.jetbrains.anko.startActivity

/**
 * Created by ${XiLei} on 2018/9/22.
 * 摄影师详情
 */
class CameramanDetailActivity : BaseMvpActivity<CameramanDetailsPresenter>(), CameramanDetailsView, View.OnClickListener {

    @Autowired(name = BaseConstant.KEY_CAMERAMAN_ID)
    @JvmField
    var mCameramanId: String = "" //摄影师id

    private lateinit var mSetMealDetails: Cameraman
    private var mAttention: Boolean = false //是否关注
    private lateinit var mCameramanWorksAdapter: CameramanWorksAdapter
    private lateinit var mEvaluateAdapter: EvaluateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cameraman_details)

        initView()
        loadEvaluateData()
    }

    override fun injectComponent() {
        DaggerGoodsComponent.builder().activityComponent(mActivityComponent).goodsModule(GoodsModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initView() {

        //最新评价
        mEvaluateRv.layoutManager = LinearLayoutManager(this!!)
        mEvaluateAdapter = EvaluateAdapter(this)
        mEvaluateRv.adapter = mEvaluateAdapter
        //作品欣赏
        mBasicServicesRv.layoutManager = GridLayoutManager(this, 3)
        mCameramanWorksAdapter = CameramanWorksAdapter(this)
        mBasicServicesRv.adapter = mCameramanWorksAdapter

        mBackIv.onClick { finish() }
        mCollectionView.onClick(this)
        mBtn.onClick(this)
        mTakePayTv.onClick(this)
    }

    /**
     * 获取最新评价
     */
    private fun loadEvaluateData() {
        var map = mutableMapOf<String, String>()
        map.put("packageId", mCameramanId)
        mBasePresenter.getEvaluateData(map)
    }

    /**
     * 获取最新评价成功
     */
    override fun onGetEvaluateSuccess(result: MutableList<Evaluate>) {
        mEvaluateAdapter.setData(result)
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.mShopView -> ARouter.getInstance().build(RouterPath.ShopCenter.PATH_SHOP_DETAILS).withString(BaseConstant.KEY_SHOP_ID, mSetMealDetails.storeId).navigation()
            R.id.mCollectionView -> {

                afterLogin {

                    mAttention = !mAttention
                    if (mAttention) {
                        mCollectionTv.text = getString(R.string.follow_y)
                        mCollectionIv.setImageDrawable(resources.getDrawable(R.drawable.heart4))
                    } else {
                        mCollectionTv.text = getString(R.string.follow)
                        mCollectionIv.setImageDrawable(resources.getDrawable(R.drawable.heart3))
                    }
                    loadFollow()
                }
            }

            R.id.mTakePayTv -> {
                /*   mSetMealDetails?.let {
                       startActivity<BasicServicesListActivity>(BaseConstant.KEY_SHOP_ID to mSetMealDetails.storeId)
                   }*/
            }

            R.id.mBtn -> {
                startActivity<MealOrderConfirmActivity>()
            }

        }
    }

    override fun onStart() {
        super.onStart()
        loadGoodDetailsData()
    }

    /**
     * 摄影师详情
     */
    private fun loadGoodDetailsData() {
        var map = mutableMapOf<String, String>()
        map.put("id", mCameramanId)
        map.put("loginUid", AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN))
        mBasePresenter.getCameramanDetails(map)
    }

    /**
     * 获取摄影师详情成功
     */
    override fun onGetCameramanDetailsSuccess(result: Cameraman) {
        mSetMealDetails = result
        with(result) {

            mUserIconIv.loadUrl(webUser.imgurl)
            mNameTv.text = webUser.nickname + " " + teacherType + " | " + applyType
            mPriceTv.text = "¥${webUser.photoAmount}/套系"
            mSeniorityPriceTv.text = "¥${webUser.extraAmount}/套系"
            mCreditTv.text = webUser.credit
            mTeacherIntroduceTv.text = selfIntroduction

            store?.let {
                mShopNameTv.text = store.storeName
            }

            mAttention = attention
            if (attention) {
                mCollectionTv.text = getString(R.string.follow_y)
                mCollectionIv.setImageDrawable(resources.getDrawable(R.drawable.heart4))
            } else {
                mCollectionTv.text = getString(R.string.follow)
                mCollectionIv.setImageDrawable(resources.getDrawable(R.drawable.heart3))
            }
            loadTeacherWorksData(webUser.id)
        }
    }

    /**
     * 获取摄影师作品
     */
    private fun loadTeacherWorksData(uid: String) {
        var map = mutableMapOf<String, String>()
        map.put("currentPage", "1")
        map.put("uid", uid)
        mBasePresenter.getTeacherWorks(map)
    }


    /**
     * 获取摄影师作品成功
     */
    override fun onGetTeacherWorksSuccess(result: BasePagingResp<MutableList<CameranmanWorks>>) {

        mCameramanWorksAdapter.setData(result.data)
    }

    /**
     * 关注、取消关注
     */
    private fun loadFollow() {
        var map = mutableMapOf<String, String>()
        map.put("teacherId", mCameramanId)
        map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN))
        mBasePresenter.getCameramanFollow(map)
    }

    override fun onFollowSuccess(result: Boolean) {

    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}