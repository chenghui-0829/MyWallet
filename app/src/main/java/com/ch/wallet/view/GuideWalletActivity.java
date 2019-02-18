package com.ch.wallet.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ch.wallet.R;
import com.ch.wallet.util.ActivityManage;

public class GuideWalletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_wallet);
        ActivityManage.getInstance().addActivity(this);
    }
}
