package com.yizhipin.generalizecenter.ui.activity

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.yizhipin.base.common.BaseApplication.Companion.context
import com.yizhipin.base.common.BaseConstant
import com.yizhipin.base.data.protocol.BasePagingResp
import com.yizhipin.base.data.response.Comment
import com.yizhipin.base.data.response.Interaction
import com.yizhipin.base.data.response.InteractionDetails
import com.yizhipin.base.ext.loadUrl
import com.yizhipin.base.ext.onClick
import com.yizhipin.base.ext.setVisible
import com.yizhipin.base.ui.activity.BaseMvpActivity
import com.yizhipin.base.utils.AppPrefsUtils
import com.yizhipin.base.utils.DateUtils
import com.yizhipin.generalizecenter.R
import com.yizhipin.generalizecenter.presenter.InteractionPresenter
import com.yizhipin.generalizecenter.presenter.view.ReportView
import com.yizhipin.generalizecenter.ui.adapter.InteractionDetailsEvaAdapter
import com.yizhipin.generalizecenter.ui.adapter.InteractionDetailsImageAdapter
import com.yizhipin.generalizecenter.ui.adapter.InteractionImageAdapter
import com.yizhipin.goods.injection.component.DaggerGeneralizeComponent
import com.yizhipin.goods.injection.module.GeneralizeModule
import kotlinx.android.synthetic.main.activity_interaction_details.*
import org.jetbrains.anko.toast

/**
 * Created by ${XiLei} on 2018/9/2.
 * 互动吧详情
 */
class InteractionDetailsActivity : BaseMvpActivity<InteractionPresenter>(), ReportView {

    @Autowired(name = BaseConstant.KEY_INTERACTION_ID)
    @JvmField
    var mId: String = ""

    private lateinit var mInteractionImageAdapter: InteractionImageAdapter
    private lateinit var mInteractionDetailsImageAdapter: InteractionDetailsImageAdapter
    private lateinit var mInteractionDetailsEvaAdapter: InteractionDetailsEvaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interaction_details)

        initView()
    }

    private fun initView() {

        mImageRv.layoutManager = GridLayoutManager(context!!, 3)
        mInteractionImageAdapter = InteractionImageAdapter(context)
        mImageRv.adapter = mInteractionImageAdapter

        var linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mLikeRv.layoutManager = linearLayoutManager
        mInteractionDetailsImageAdapter = InteractionDetailsImageAdapter(this)
        mLikeRv.adapter = mInteractionDetailsImageAdapter

        mEvaRv.layoutManager = LinearLayoutManager(this)
        mInteractionDetailsEvaAdapter = InteractionDetailsEvaAdapter(this)
        mEvaRv.adapter = mInteractionDetailsEvaAdapter

        mEt.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(view: View?, keyCode: Int, keyEvent: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    toast(mEt.text.toString())
                    comment(mEt.text.toString())
                }
                return false
            }
        })

        mCustomBtn.onClick {
            custom()
        }
    }


    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        var map = mutableMapOf<String, String>()
        map.put("id", mId)
        mBasePresenter.getInteractionDetails(map)
    }

    override fun injectComponent() {
        DaggerGeneralizeComponent.builder().activityComponent(mActivityComponent).generalizeModule(GeneralizeModule()).build().inject(this)
        mBasePresenter.mView = this
    }

    override fun onGetInteractionDetailsSuccess(result: InteractionDetails) {

        with(result) {
            mContentTv.text = content
            mDateTv.text = DateUtils.parseDate(releaseTime, DateUtils.FORMAT_SHORT).toString()
            mLikeCountTv.text = "${zanCount}点赞"
            mEvaCountTv.text = "${evaCount}评论"
            mLikeCountTv.isSelected = zan
            mLikeCountTv.setCompoundDrawables(getSortStatus(zan), null, null, null)

            nickname?.let {
                mNameTv.text = nickname
            }
            if (headImgurl.isNullOrEmpty()) {
                mIv.setImageDrawable(context.resources.getDrawable(R.drawable.avatarw))
            } else {
                mIv.loadUrl(headImgurl)
            }
            imgurl?.let {
                mImageRv.setVisible(true)
                var listResult = mutableListOf<String>()
                if (imgurl.contains(",")) {
                    var list = imgurl!!.split(",").toMutableList()
                    for (l in list) {
                        listResult.add(l)
                    }
                } else {
                    listResult.add(imgurl)
                }
                mInteractionImageAdapter.setData(listResult)
            }

            mInteractionDetailsImageAdapter.setData(zans)
            mInteractionDetailsEvaAdapter.setData(evas)
        }

    }

    /**
     * 评论
     */
    private fun comment(content: String) {
        var map = mutableMapOf<String, String>()
        map.put("interactiveId", mId)
        map.put("uid", AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_ID))
        map.put("content", content)
        mBasePresenter.comment(map)
    }

    override fun onCommentSuccess(result: Comment) {
        mEt.setText("")
        loadData()
    }

    private fun getSortStatus(isLike: Boolean): Drawable? {
        var drawable: Drawable? = null
        when (isLike) {
            true -> {
                drawable = ContextCompat.getDrawable(context, R.drawable.heart7)!!
                drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            }
            false -> {
                drawable = ContextCompat.getDrawable(context, R.drawable.heart5)!!
                drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            }
        }
        return drawable
    }

    override fun onGetInteractionListSuccess(result: BasePagingResp<MutableList<Interaction>>) {
    }
}