package com.ch.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingActivity extends AppCompatActivity {

    private Context context = LoadingActivity.this;
    @BindView(R.id.loading_activity_text)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // 全屏
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        final Intent intent = new Intent();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                intent.setClass(context, OperateActivity.class);
                intent.putExtra("tab", "");
                startActivity(intent);
                finish();
            }
        }, 1800);

        Animation animation = new AlphaAnimation(0, 1);
        animation.setFillAfter(true);
        animation.setDuration(1600);
        textView.startAnimation(animation);
    }
}
