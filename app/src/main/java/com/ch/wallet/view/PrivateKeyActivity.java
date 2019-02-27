package com.ch.wallet.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ch.wallet.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrivateKeyActivity extends AppCompatActivity {

    @BindView(R.id.private_key_activity_text)
    TextView privateKeyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_key);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        privateKeyText.setText(intent.getStringExtra("result"));
    }

    public void backBtn(View view) {
        finish();
    }
}
