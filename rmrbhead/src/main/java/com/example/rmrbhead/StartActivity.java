package com.example.rmrbhead;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void handleClick1(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void handleClick2(View v) {
        startActivity(new Intent(this, ScrollingActivity.class));
    }

    public void handleClick3(View v) {
        startActivity(new Intent(this, TitleBarHideActivity.class));
    }

}
