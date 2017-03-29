package com.xyzlf.pop;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xyzlf.poplib.PopCommon;
import com.xyzlf.poplib.PopModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        /** Item1 textView **/
        final TextView textView = (TextView) findViewById(R.id.text);
        findViewById(R.id.item_layout_1_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showItemPop(textView);
            }
        });

        /** Item2 textView **/
        final TextView textView2 = (TextView) findViewById(R.id.text2);
        findViewById(R.id.item_layout_2_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showItemPop(textView2);
            }
        });

        /** 菜单 ImageView **/
        final ImageView moreImage = (ImageView) findViewById(R.id.more);
        moreImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuPop(moreImage);
            }
        });
    }

    private void showMenuPop(View menuView) {
        PopModel feedPopModel = new PopModel();
        feedPopModel.setDrawableId(R.drawable.icon_search);
        feedPopModel.setItemDesc("搜索");

        PopModel messagePopMode = new PopModel();
        //如果设置了图标，则会显示，否则不显示
        messagePopMode.setDrawableId(R.drawable.icon_refresh);
        messagePopMode.setItemDesc("刷新网页");

        /** 初始化数据源 **/
        final List<PopModel> list = new ArrayList<>();
        list.add(feedPopModel);
        list.add(messagePopMode);

        PopCommon popCommon = new PopCommon(this, list, new PopCommon.OnPopCommonListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "点击了：" + list.get(position).getItemDesc(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDismiss() {

            }
        });
        /** 是否显示黑色背景，默认不显示 **/
        popCommon.setShowAplhaWindow(true);
        popCommon.showPop(menuView, dp2px(getApplicationContext(), 5), menuView.getHeight() / 4 * 5);
    }

    private void showItemPop(View targetView) {
        int location[] = new int[2];
        targetView.getLocationOnScreen(location);
        int x = dp2px(getApplicationContext(), 15);
        int y = location[1] + targetView.getHeight();

        PopModel feedPopModel = new PopModel();
        feedPopModel.setItemDesc("不感兴趣");

        PopModel messagePopMode = new PopModel();
        messagePopMode.setItemDesc("添加收藏");

        final List<PopModel> list = new ArrayList<>();
        list.add(feedPopModel);
        list.add(messagePopMode);

        PopCommon popCommon = new PopCommon(this, list, new PopCommon.OnPopCommonListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "点击了：" + list.get(position).getItemDesc(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDismiss() {

            }
        });
        popCommon.showPop(targetView, x, y);
    }

    private static int dp2px(Context context, float value) {
        return (int) (context.getResources().getDisplayMetrics().density * value + 0.5);
    }
}
