package com.yizhipin.goods.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.common.TeacherType
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Teacher
import com.yizhipin.base.data.response.TeacherWorks
import com.yizhipin.base.data.response.Evaluate
import com.yizhipin.base.data.response.OrderDetails
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ext.setVisible
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.ui.adapter.EvaluateAdapter
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.goods.R
import com.yizhipin.goods.injection.component.DaggerGoodsComponent
import com.yizhipin.goods.injection.module.GoodsModule
import com.yizhipin.goods.presenter.TeacherDetailsPresenter
import com.yizhipin.goods.presenter.view.TeacherDetailsView
import com.yizhipin.goods.ui.adapter.TeacherWorksAdapter
import com.yizhipin.provider.common.afterLogin
import com.yizhipin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_cameraman_details.*
import org.jetbrains.anko.startActivity

/**
 * Created by ${XiLei} on 2018/9/22.
 * 老师详情
 */
class TeacherDetailActivity : BaseMvpActivity<TeacherDetailsPresenter>(), TeacherDetailsView, View.OnClickListener {

    @Autowired(name = BaseConstant.KEY_TEACHER_ID)
    @JvmField
    var mTeacherId: String = "" //老师id

    @Autowired(name = BaseConstant.KEY_TEACHER_USER_ID)
    @JvmField
    var mTeacherUserID: String = "" //老师用户id

    @Autowired(name = BaseConstant.KEY_IS_DESTINE)
    @JvmField
    var mDestine: Boolean = false //是老师列表查看还是选择老师查看

    private lateinit var mSetMealDetails: Teacher
    private var mAttention: Boolean = false //是否关注
    private lateinit var mTeacherWorksAdapter: TeacherWorksAdapter
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
        var linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mBasicServicesRv.layoutManager = linearLayoutManager
        mTeacherWorksAdapter = TeacherWorksAdapter(this)
        mBasicServicesRv.adapter = mTeacherWorksAdapter

        mBackIv.onClick { finish() }
        mCollectionView.onClick(this)
        mBtn.onClick(this)
        mMoreWorkTv.onClick(this)
        mEvaluateMoreTv.onClick(this)
        mMoreWorkTv.onClick(this)

        if (mDestine) bottomView.setVisible(true) else bottomView.setVisible(false)

    }

    /**
     * 获取最新评价
     */
    private fun loadEvaluateData() {
        var map = mutableMapOf<String, String>()
        map.put("currentPage", "1")
        map.put("teacherId", mTeacherId)
        mBasePresenter.getEvaluateTeacherList(map)
    }

    /**
     * 获取最新评价成功
     */
    override fun onGetEvaluateListSuccess(result: BasePagingResp<MutableList<Evaluate>>) {
        mEvaluateAdapter.setData(result.data)
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

            R.id.mBtn -> {
                afterLogin {
                    var map = mutableMapOf<String, String>()
                    map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
                    map.put("teacherUid", mSetMealDetails.uid)
                    mBasePresenter.orderTeacher(map)
                }
            }
            R.id.mEvaluateMoreTv -> {
                startActivity<EvaluateActivity>(BaseConstant.KEY_TEACHER_ID to mTeacherId)
            }

            R.id.mMoreWorkTv -> {
                startActivity<TeacherWorksActivity>(BaseConstant.KEY_TEACHER_USER_ID to mSetMealDetails.webUser.id)
            }
        }
    }

    /**
     * 下单成功
     */
    override fun onOrderSuccess(result: OrderDetails) {
        startActivity<MealOrderConfirmActivity>(BaseConstant.KEY_MEAL_ORDER_ID to result.id
                , BaseConstant.KEY_ORDER_TEACHER to result.teachers[0])
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
        map.put("id", mTeacherId)
        map.put("loginUid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mBasePresenter.getCameramanDetails(map)
    }

    /**
     * 获取摄影师详情成功
     */
    override fun onGetCameramanDetailsSuccess(result: Teacher) {
        mSetMealDetails = result
        with(result) {

            mUserIconIv.loadUrl(webUser.imgurl)
            mPriceTv.text = "¥${webUser.photoAmount}/套系"
            mSeniorityPriceTv.text = "¥${webUser.extraAmount}/套系"
            mCreditTv.text = webUser.credit
            mTeacherIntroduceTv.text = selfIntroduction

            when (teacherType) {
                TeacherType.TEACHER_SHEYING -> mNameTv.text = webUser.nickname + " 摄影师 | " + applyType
                TeacherType.TEACHER_HUAZHUANG -> mNameTv.text = webUser.nickname + " 化妆师 | " + applyType
            }

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
    override fun onGetTeacherWorksSuccess(result: BasePagingResp<MutableList<TeacherWorks>>) {

        mTeacherWorksAdapter.setData(result.data)
    }

    /**
     * 关注、取消关注
     */
    private fun loadFollow() {
        var map = mutableMapOf<String, String>()
        map.put("teacherId", mTeacherUserID)
        map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mBasePresenter.getCameramanFollow(map)
    }

    override fun onFollowSuccess(result: Boolean) {

    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}