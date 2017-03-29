package com.xyzlf.poplib;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

/**
 * Created by zhanglifeng on 2017/3/28.
 * PopWindow Manager
 */
public class PopCommon implements PopBase.OnPopBaseListener {

    private PopBase mPopBase;
    private OnPopCommonListener onPopCommonListener;
    private boolean isShowAplhaWindow;
    private PopAdapter adapter;

    public interface OnPopCommonListener {
        void onItemClick(AdapterView<?> parent, View view, int position, long id);
        void onDismiss();
    }

    public PopCommon(Activity activity, List<PopModel> popModels) {
        this(activity, popModels, null);
    }

    public PopCommon(Activity activity, List<PopModel> popModels, OnPopCommonListener onPopCommonListener) {
        this.onPopCommonListener = onPopCommonListener;
        init(activity, popModels);
    }

    private void init(Activity activity, List<PopModel> popModels) {
        View layout = LayoutInflater.from(activity).inflate(R.layout.pop_popwindow_layout, null);
        ListView listView = (ListView) layout.findViewById(R.id.pop_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismissPop();
                if (null != onPopCommonListener) {
                    onPopCommonListener.onItemClick(parent, view, position, id);
                }
            }
        });
        adapter = new PopAdapter(popModels);
        listView.setAdapter(adapter);
        mPopBase = new PopBase(activity, layout, this);
    }

    public void setPopModels(List<PopModel> popModels) {
        if (null != adapter) {
            adapter.setPopModels(popModels);
        }
    }

    /**
     * 是否显示黑色透明背景
     * @param showAplhaWindow boolean
     */
    public void setShowAplhaWindow(boolean showAplhaWindow) {
        isShowAplhaWindow = showAplhaWindow;
    }

    public void showPop(View targetView, int x, int y) {
        if (null == targetView) {
            return;
        }
        if (null != mPopBase) {
            mPopBase.setShowAlphaWindow(isShowAplhaWindow);
            mPopBase.show(targetView, x, y);
        }
    }

    public void dismissPop() {
        if (null != mPopBase) {
            mPopBase.dismiss();
        }
    }

    @Override
    public void onDismiss() {
        if (null != onPopCommonListener) {
            onPopCommonListener.onDismiss();
        }
    }
}
