package com.ch.wallet.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ch.wallet.R;
import com.ch.wallet.util.ActivityManage;
import com.ch.wallet.util.SPUtil;
import com.ch.wallet.util.WalletManager;

import org.web3j.crypto.WalletFile;

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
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);
        ButterKnife.bind(this);
        ActivityManage.getInstance().addActivity(this);
        initView();
    }

    private void initView() {

        dialog = new Dialog(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(
                R.layout.show_request_result_hint_dialog, null);
        TextView hintTextView = view
                .findViewById(R.id.show_request_result_dialog_result_text);
        hintTextView.setText("钱包创建中...");
        dialog.setCancelable(false);
        dialog.setContentView(view);
    }

    @OnClick({R.id.create_wallet_activity_back_icon, R.id.create_wallet_activity_create_btn})
    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.create_wallet_activity_back_icon:
                finish();
                break;
            case R.id.create_wallet_activity_create_btn:
                new MyTask().execute(passEdit.getText().toString());
                break;
        }
    }

    class MyTask extends AsyncTask<String, Integer, WalletFile> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected WalletFile doInBackground(String... strings) {
            return WalletManager.getInstance().createWalletByMnemonic(strings.toString());
        }

        @Override
        protected void onPostExecute(WalletFile wallet) {
            super.onPostExecute(wallet);
            dialog.cancel();
            if (wallet == null) {
                Toast.makeText(context, "创建失败，请重试~", Toast.LENGTH_LONG).show();
            } else {
                String address = "0x" + wallet.getAddress();
                SPUtil.put(context, "address", address);
                SPUtil.put(context, "user", nameEdit.getText().toString());
                startActivity(new Intent(context, CreateSuccessActivity.class));
            }
        }
    }
}
