package com.ch.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OperateActivity extends AppCompatActivity {

    private Context context = OperateActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operate);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.operate_activity_create_btn, R.id.operate_activity_guide_btn})
    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.operate_activity_create_btn:
                startActivity(new Intent(context, CreateWalletActivity.class));
                break;
            case R.id.operate_activity_guide_btn:
                startActivity(new Intent(context, GuideWalletActivity.class));
                break;
        }
    }
}
