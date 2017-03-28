package com.xyzlf.poplib;

import android.app.Activity;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by zhanglifeng on 2017/3/28.
 * Pop Base
 */

public class PopBase {

    public static final float DEFAULT_WINDOW_ALPHA_VALUE = 0.3f;

    protected Activity mActivity;
    protected PopupWindow mPopWindow;
    protected boolean isShowAlphaWindow;
    protected float mAlphaValue = DEFAULT_WINDOW_ALPHA_VALUE;

    public PopBase(Activity activity, View layout) {
        this.mActivity = activity;
        initPop(layout);
    }

    protected void initPop(View layout) {
        if (null == layout) {
            return;
        }
        mPopWindow = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setBackgroundDrawable(null);
        mPopWindow.setAnimationStyle(R.style.popShowAnim);
        mPopWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (isShowAlphaWindow) {
                    changeWindowAlpha(1f);//pop消失，透明度恢复
                }
            }
        });

        //必须设置该项才能保证相应按键事件
        layout.setFocusableInTouchMode(true);
        //点击外部消失
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dismiss();
                return true;
            }
        });
        layout.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    dismiss();
                }
                return false;
            }
        });
    }

    public void show(View menuBtn, int x, int y) {
        if (mPopWindow == null) {
            return;
        }
        if (mPopWindow.isShowing()) {
            mPopWindow.setFocusable(false);
            mPopWindow.dismiss();
        } else {
            if (isShowAlphaWindow) {
                changeWindowAlpha(mAlphaValue); //改变窗口透明度
            }
            mPopWindow.setFocusable(true);
            mPopWindow.showAtLocation(menuBtn, Gravity.TOP | Gravity.RIGHT, x, y);
            mPopWindow.update();
        }
    }

    public void dismiss() {
        if (null != mPopWindow && mPopWindow.isShowing()) {
            mPopWindow.dismiss();
        }
    }

    public void setAnimationStyle(@StyleRes int animationStyle) {
        if (null != mPopWindow) {
            mPopWindow.setAnimationStyle(animationStyle);
        }
    }

    protected void setAlphaValue(float alphaValue) {
        this.mAlphaValue = alphaValue;
    }

    protected void setShowAlphaWindow(boolean showAlphaWindow) {
        this.isShowAlphaWindow = showAlphaWindow;
    }

    /**
     * 更改窗口透明度
     * @param alpha (alphaValue 0.0 - 1.0)
     */
    private void changeWindowAlpha(float alpha){
        if (null == mActivity || mActivity.isFinishing()) {
            return;
        }
        if (null == mActivity.getWindow()) {
            return;
        }
        WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
        params.alpha = alpha;
        mActivity.getWindow().setAttributes(params);
    }

}
