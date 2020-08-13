package com.bilibili;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ButtonBarLayout buttonBarLayout;
    private TextView titleText;
    private FloatingActionButton floatingActionButton;
    private ImageView imageView;
    private View videoLayout;
    private MyVideoView videoView; // VideoView不好用,建议使用第三方的播放控件
    private NestedScrollView nestedScrollView;

    private CollapsingToolbarLayoutState state;

    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        nestedScrollView = (NestedScrollView) findViewById(R.id.nested_scrollview);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        imageView = (ImageView) findViewById(R.id.imageview);
        videoLayout = findViewById(R.id.video_layout);
        videoView = (MyVideoView) findViewById(R.id.videoview);
        titleText = (TextView) findViewById(R.id.toolbar_title);
        titleText.setText("Bill");
        buttonBarLayout = (ButtonBarLayout) findViewById(R.id.playButton);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d("Bill", "verticalOffset:" + verticalOffset);
                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
//                        collapsingToolbarLayout.setTitle("EXPANDED");//设置title为EXPANDED
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
//                        collapsingToolbarLayout.setTitle("");//设置title
                        buttonBarLayout.setVisibility(View.VISIBLE);//播放按钮
                        titleText.setVisibility(View.GONE);
                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                            buttonBarLayout.setVisibility(View.GONE);//由折叠变为中间状态时隐藏播放按钮
                            titleText.setVisibility(View.VISIBLE);
                        }
                        state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
//                        collapsingToolbarLayout.setTitle("INTERNEDIATE");//设置title为INTERNEDIATE
                    }
                }
            }
        });

        mController = new MediaController(this);

        buttonBarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                palyVideo();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                palyVideo();
            }
        });

    }

    MediaController mController;

    private void palyVideo() {
        imageView.setVisibility(View.GONE);
        videoLayout.setVisibility(View.VISIBLE);
        floatingActionButton.setVisibility(View.GONE);
        nestedScrollView.setNestedScrollingEnabled(false);
        appBarLayout.setExpanded(true);

        // 现在这个链接是可以用的，播放比较慢，耐心等一会
        Uri uri = Uri.parse("rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov");
        videoView.setVideoURI(uri);
        videoView.setMediaController(mController);
        videoView.start();
        videoView.requestFocus();


        /*File videoFile = new File("/sdcard/test.mp4");
        if (videoFile.exists()) {
            videoView.setVideoPath(videoFile.getAbsolutePath());
            videoView.setMediaController(mController);
            videoView.start();
            videoView.requestFocus();
        }*/
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
