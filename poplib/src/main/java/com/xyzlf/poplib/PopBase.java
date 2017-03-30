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

    private Activity mActivity;
    private PopupWindow mPopWindow;
    private boolean isShowAlphaWindow;
    private float mAlphaValue = DEFAULT_WINDOW_ALPHA_VALUE;
    private int screenWidth;

    private OnPopBaseListener onPopBaseListener;

    public interface OnPopBaseListener {
        void onDismiss();
    }

    public PopBase(Activity activity, View layout) {
        this(activity, layout, R.style.popShowAnim, null);
    }

    public PopBase(Activity activity, View layout, OnPopBaseListener onPopBaseListener) {
        this(activity, layout, R.style.popShowAnim, onPopBaseListener);
    }


    public PopBase(Activity activity, View layout, @StyleRes int animationStyle, OnPopBaseListener onPopBaseListener) {
        this.mActivity = activity;
        this.onPopBaseListener = onPopBaseListener;
        screenWidth = activity.getResources().getDisplayMetrics().widthPixels;
        initPop(layout, animationStyle);
    }

    private void initPop(View layout, @StyleRes int animationStyle) {
        if (null == layout) {
            return;
        }
        mPopWindow = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setBackgroundDrawable(null);
        mPopWindow.setAnimationStyle(animationStyle);
        mPopWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (isShowAlphaWindow) {
                    changeWindowAlpha(1f);
                }
                if (null != onPopBaseListener) {
                    onPopBaseListener.onDismiss();
                }
            }
        });

        //点击外部消失
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dismiss();
                return true;
            }
        });
        //必须设置该项才能保证相应按键事件
        layout.setFocusableInTouchMode(true);
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
        if (null == menuBtn || null == mPopWindow) {
            return;
        }
        int xLocation = screenWidth - mPopWindow.getContentView().getMeasuredWidth() - x;
        this.show(menuBtn, Gravity.TOP | Gravity.START, xLocation, y);
    }

    public void show(View menuBtn, int gravity, int x, int y) {
        if (mPopWindow == null) {
            return;
        }
        if (mPopWindow.isShowing()) {
            mPopWindow.setFocusable(false);
            mPopWindow.dismiss();
        } else {
            if (isShowAlphaWindow) {
                changeWindowAlpha(mAlphaValue);
            }
            mPopWindow.setFocusable(true);
            mPopWindow.showAtLocation(menuBtn, gravity, x, y);
            mPopWindow.update();
        }
    }

    public void dismiss() {
        if (null != mPopWindow && mPopWindow.isShowing()) {
            mPopWindow.dismiss();
        }
    }

    public void setAlphaValue(float alphaValue) {
        this.mAlphaValue = alphaValue;
    }

    public void setShowAlphaWindow(boolean showAlphaWindow) {
        this.isShowAlphaWindow = showAlphaWindow;
    }

    /**
     * Change window alpha
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
