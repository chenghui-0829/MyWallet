package com.ch.wallet.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ch.wallet.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KeyStoreActivity extends AppCompatActivity {

    @BindView(R.id.key_store_activity_text)
    TextView keyStoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_store);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        keyStoreText.setText(intent.getStringExtra("result"));

    }

    public void backBtn(View view) {
        finish();
    }
}
