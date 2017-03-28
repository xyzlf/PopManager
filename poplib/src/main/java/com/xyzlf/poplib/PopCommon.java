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
public class PopCommon {

    private PopBase mPopBase;

    public PopCommon(Activity activity, List<PopModel> popModels) {
        init(activity, popModels);
    }

    private void init(Activity activity, List<PopModel> popModels) {
        View layout = LayoutInflater.from(activity).inflate(R.layout.pop_popwindow_layout, null);
        ListView listView = (ListView) layout.findViewById(R.id.pop_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismissPop();
            }
        });
        PopAdapter adapter = new PopAdapter(popModels);
        listView.setAdapter(adapter);
        mPopBase = new PopBase(activity, layout);
        mPopBase.setShowAlphaWindow(true);
    }

    public void showPop(View targetView, int x, int y) {
        if (null != mPopBase) {
            mPopBase.show(targetView, x, y);
        }
    }

    public void dismissPop() {
        if (null != mPopBase) {
            mPopBase.dismiss();
        }
    }

}
