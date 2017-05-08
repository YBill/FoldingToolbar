package com.example.rmrbhead;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ScrollingActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        titleText = (TextView) findViewById(R.id.title);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    //修改状态标记为展开
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    //修改状态标记为折叠
                    titleText.setVisibility(View.VISIBLE);
                } else {
                    //由折叠变为中间状态时隐藏播放按钮
                    titleText.setVisibility(View.GONE);
                }
            }
        });

    }

}
