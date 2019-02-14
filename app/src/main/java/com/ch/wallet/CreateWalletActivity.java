package com.ch.wallet;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateWalletActivity extends AppCompatActivity {

    private Context context = CreateWalletActivity.this;
    @BindView(R.id.create_wallet_activity_name_edit)
    EditText nameEdit;
    @BindView(R.id.create_wallet_activity_pass_edit)
    EditText passEdit;
    @BindView(R.id.create_wallet_activity_cfmm_edit)
    EditText cfmmEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.create_wallet_activity_back_icon, R.id.create_wallet_activity_create_btn})
    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.create_wallet_activity_back_icon:
                finish();
                break;
            case R.id.create_wallet_activity_create_btn:



                break;
        }
    }

}
