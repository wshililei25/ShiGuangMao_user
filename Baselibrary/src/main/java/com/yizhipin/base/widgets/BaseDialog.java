package com.yizhipin.base.widgets;

import android.app.Dialog;
import android.content.Context;
import android.text.SpannableString;
import android.view.View;

import com.yizhipin.base.R;


/**
 * Created by ${XiLei} on 2018/8/19.
 */
public class BaseDialog extends Dialog {

    private View.OnClickListener mClickListener;

    /**
     * 设置dialog的内容
     */
    public void setContent(String description) {
//        mListDialogAQuery.id(R.id.tipone).text(description);
    }

    /**
     * 设置dialog的内容
     */
    public void setContent(String description, int gravity) {
//        mListDialogAQuery.id(R.id.tipone).text(description);
//        mListDialogAQuery.id(R.id.tipone).getTextView().setGravity(gravity);
    }

    /**
     * 设置dialog的内容
     */
    public void setContent(SpannableString description) {
//        mListDialogAQuery.id(R.id.tipone).text(description);
    }

    /**
     * 设置dialog的内容
     */
    public void appedContent(SpannableString description) {
//        mListDialogAQuery.id(R.id.tipone).getTextView().append(description);
    }

    /**
     * 设置按钮的文字
     *
     * @param negative 左边按钮
     * @param positive 右边按钮
     */
    public void setButtonText(String negative, String positive) {
//        mListDialogAQuery.id(R.id.updatecancel).text(negative);
//        mListDialogAQuery.id(R.id.updaterightnow).text(positive);
    }

    /**
     * 设置dialog的title
     */
    public void setDialogTitle(String title) {
//        mListDialogAQuery.id(R.id.dialog_title).text(title);
    }

    /**
     * 设置未实名认证显示的文字
     */
    public void setPhoneVisble(int visible) {
//        mListDialogAQuery.id(R.id.not_auth_view).visibility(visible);
    }

    public BaseDialog(Context context) {
        super(context, R.style.HB_Dialog);
        this.getWindow().setWindowAnimations(R.style.animinandout);
        initView(context);
    }

    /**
     * 是否有title
     *
     * @param hasTitle true the title and line visible or gone
     */
    public void hasTitle(boolean hasTitle) {
//        mListDialogAQuery.id(R.id.dialog_title).visibility(
//                hasTitle ? View.VISIBLE : View.GONE);
//        mListDialogAQuery.id(R.id.dialog_title_line).visibility(
//                hasTitle ? View.VISIBLE : View.GONE);

    }

    /**
     * 设置按钮的点击事件
     */
    public void setClickListener(View.OnClickListener mClickListener) {
        this.mClickListener = mClickListener;
        setOnClickListener();
    }

    private void setOnClickListener() {
//        mListDialogAQuery.id(R.id.updatecancel).clicked(mClickListener)
//                .id(R.id.updaterightnow).clicked(mClickListener);
    }

    private void initView(Context context) {
//        View view = LayoutInflater.from(context).inflate(
//                R.layout.layout_basedialog, null);
//        setContentView(view);
//        setCanceledOnTouchOutside(false);
//        mListDialogAQuery = new AQuery(view);
//        Window window = getWindow();
//        WindowManager.LayoutParams params = window.getAttributes();
//        params.width = LayoutParams.MATCH_PARENT;
//        params.height = LayoutParams.WRAP_CONTENT;
//        params.gravity = Gravity.BOTTOM;
//        window.setAttributes(params);
    }

    public void isHideRightButton(boolean flag){
//        if(flag){
        //            mListDialogAQuery.id(R.id.updaterightnow).visibility(View.GONE).id(R.id.view_line).visibility(View.GONE);
        //        }else{
        //            mListDialogAQuery.id(R.id.updaterightnow).visibility(View.VISIBLE).id(R.id.view_line).visibility(View.VISIBLE);
        //        }
       
    }
    

}
