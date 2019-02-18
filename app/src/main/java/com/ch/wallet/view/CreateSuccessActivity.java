package com.ch.wallet.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.ch.wallet.R;
import com.ch.wallet.util.ActivityManage;

public class CreateSuccessActivity extends AppCompatActivity {

    private Context context = CreateSuccessActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_success);
        ActivityManage.getInstance().addActivity(this);
    }

    public void BfWallet(View view) {


    }

    public void backBtn(View view) {
        startActivity(new Intent(CreateSuccessActivity.this, MainActivity.class));
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(context, MainActivity.class));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
