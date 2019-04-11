package com.yizhipin.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.yizhipin.R
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.*
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ext.setVisible
import com.yizhipin.base.ui.fragment.BaseMvpFragment
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.generalizecenter.ui.activity.InteractionActivity
import com.yizhipin.presenter.HomePresenter
import com.yizhipin.presenter.view.HomeView
import com.yizhipin.provider.common.ProvideReqCode
import com.yizhipin.provider.common.afterLogin
import com.yizhipin.provider.common.isLogined
import com.yizhipin.provider.router.RouterPath
import com.yizhipin.shop.ui.activity.ShopActivity
import com.yizhipin.ui.activity.InformationActivity
import com.yizhipin.ui.activity.NewsActivity
import com.yizhipin.usercenter.injection.component.DaggerMainComponent
import com.yizhipin.usercenter.injection.module.MianModule
import kotlinx.android.synthetic.main.fragment_find.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult


/**
 * Created by ${XiLei} on 2018/8/19.
 */
class FindFragment : BaseMvpFragment<HomePresenter>(), HomeView, View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_find, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun injectComponent() {
        DaggerMainComponent.builder().activityComponent(mActivityComponent).mianModule(MianModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    private fun initView() {
        mInteractionTv.onClick(this)
        mIntegralMallTv.onClick(this)
        mStoreTv.onClick(this)
        mNewIv.onClick(this)
        mTimeTopTv.onClick(this)
        mOnSelectionTv.onClick(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mInteractionTv -> startActivity<InteractionActivity>()
            R.id.mIntegralMallTv -> afterLogin { ARouter.getInstance().build(RouterPath.GoodsCenter.PATH_INTEGRAL).navigation() }
            R.id.mStoreTv -> startActivityForResult<ShopActivity>(ProvideReqCode.CODE_REQ_SHOP)
            R.id.mTimeTopTv -> startActivity<InformationActivity>()
            R.id.mNewIv -> afterLogin { startActivity<NewsActivity>() }
            R.id.mOnSelectionTv -> afterLogin { ARouter.getInstance().build(RouterPath.GoodsCenter.PATH_CLOUD_DISK).navigation() }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            ProvideReqCode.CODE_RESULT_SHOP -> {
                mStoreTv.text = data!!.getStringExtra(BaseConstant.KEY_SHOP_NAME)
                AppPrefsUtils.putString(BaseConstant.KEY_SHOP_ID, data!!.getStringExtra(BaseConstant.KEY_SHOP_ID))
                AppPrefsUtils.putString(BaseConstant.KEY_SHOP_NAME, data!!.getStringExtra(BaseConstant.KEY_SHOP_NAME))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mStoreTv.text = AppPrefsUtils.getString(BaseConstant.KEY_SHOP_NAME)
        if (isLogined()) {
            loadUnReadNewCount()
        }
    }

    private fun loadUnReadNewCount() {
        var mapCount = mutableMapOf<String, String>()
        mapCount.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        mBasePresenter.getUnreadNewCount(mapCount)
    }

    /**
     * 获取未读消息数成功
     */
    override fun getUnReadNewCount(result: Int) {
        if (result > 0) {
            mNewCountIv.setVisible(true)
        }
    }

    override fun onGetOssAddressSuccess(result: OssAddress) {
    }

    override fun onGetDefaultStoreSuccess(result: Store) {
    }

    override fun onGetNewsSuccess(result: BasePagingResp<MutableList<News>>) {
    }

    override fun onGetBannerSuccess(result: MutableList<Banner>) {
    }

    override fun onGetGoodsListSuccess(result: MutableList<ScenicSpot>) {
    }
}