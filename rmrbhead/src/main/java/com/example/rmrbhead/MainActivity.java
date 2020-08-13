package com.example.rmrbhead;

import android.graphics.Color;
import com.google.android.material.appbar.AppBarLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private Toolbar mToolbar;
    private TextView contentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentText = (TextView) findViewById(R.id.tv_content);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    //修改状态标记为展开
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    //修改状态标记为折叠
//                    contentText.setVisibility(View.GONE);
                } else {
                    //由折叠变为中间状态时隐藏播放按钮
//                    contentText.setVisibility(View.VISIBLE);
                }

                mToolbar.setBackgroundColor(changeAlpha(getResources().getColor(R.color.colorPrimary),
                        Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange()));

            }
        });

    }

    public int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }

}
