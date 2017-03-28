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

    private TextView textView;
    private ImageView moreImage;
    private PopCommon popCommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        textView = (TextView) findViewById(R.id.text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != popCommon) {
                    int location[] = new int[2];
                    textView.getLocationOnScreen(location);
                    int x = getResources().getDisplayMetrics().widthPixels - (location[0] + textView.getWidth() / 3 * 2);
                    int y = location[1] + textView.getHeight();

                    popCommon.showPop(textView, x, y);
                }
            }
        });
        moreImage = (ImageView) findViewById(R.id.more);
        moreImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != popCommon) {
                    int[] location = new int[2];
                    moreImage.getLocationOnScreen(location);
                    popCommon.showPop(moreImage, dp2px(getApplicationContext(), 5), moreImage.getHeight() / 4 * 5);
                }
            }
        });
        initPop();
    }

    private void initPop() {
        PopModel feedPopModel = new PopModel();
        feedPopModel.setDrawableId(R.drawable.icon_search);
        feedPopModel.setItemDesc("搜索");

        PopModel messagePopMode = new PopModel();
        messagePopMode.setDrawableId(R.drawable.icon_refresh);
        messagePopMode.setItemDesc("刷新网页");

        final List<PopModel> list = new ArrayList<>();
        list.add(feedPopModel);
        list.add(messagePopMode);

        popCommon = new PopCommon(this, list, new PopCommon.OnPopCommonListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "点击了：" + list.get(position).getItemDesc(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDismiss() {

            }
        });
    }

    private static int dp2px(Context context, float value) {
        return (int) (context.getResources().getDisplayMetrics().density * value + 0.5);
    }
}
