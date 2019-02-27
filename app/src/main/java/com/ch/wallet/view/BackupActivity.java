package com.ch.wallet.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ch.wallet.R;
import com.ch.wallet.util.WalletManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class BackupActivity extends AppCompatActivity {

    private Context context = BackupActivity.this;
    private static final String TAG = "EthWalletActivity";
    private WalletFile wallet;
    private ObjectMapper objectMapper = new ObjectMapper();
    private int type = 0;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);
        wallet = WalletManager.getInstance().loadWallet(this);
        initView();
    }

    private void initView() {

        dialog = new Dialog(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(
                R.layout.show_request_result_hint_dialog, null);
        TextView hintTextView = view
                .findViewById(R.id.show_request_result_dialog_result_text);
        hintTextView.setText("请稍等...");
        dialog.setCancelable(false);
        dialog.setContentView(view);
    }

    public void backBtn(View view) {
        finish();
    }

    public void onExportMnemonic(View view) {


    }

    public void onExportPrivateKey(View view) {
        type = 1;
        showInputPwDialog();
    }

    public void onExportKeyStore(View view) {
        type = 2;
        showInputPwDialog();
    }


    private void showInputPwDialog() {

        final Dialog dialog = new Dialog(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(
                R.layout.input_wallet_pw_dialog, null);
        TextView cancleTextView = view
                .findViewById(R.id.input_wallet_pw_dialog_cancle_text);
        TextView sureTextView = view
                .findViewById(R.id.input_wallet_pw_dialog_sure_text);
        final EditText editText = view.findViewById(R.id.input_wallet_pw_dialog_edit);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.show();
        Window dialogWindow = dialog.getWindow();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
        sureTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String pw = editText.getText().toString().trim();
                if ("".equals(pw) || pw.length() == 0) {
                    Toast.makeText(context, "请输入钱包密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.cancel();
                dialog.dismiss();
                new MyTask().execute(pw);
            }
        });

        cancleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                dialog.dismiss();
            }
        });
    }

    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected String doInBackground(String... string) {

            String result = "";
            switch (type) {
                case 0:

                    break;
                case 1:
                    try {
                        ECKeyPair ecKeyPair = Wallet.decrypt(string[0], wallet);
                        BigInteger privateKey = ecKeyPair.getPrivateKey();
                        //将私钥变成16精致字符串，不需要前缀，左边位数不够补0
                        result = Numeric.toHexStringNoPrefixZeroPadded(privateKey, Keys.PRIVATE_KEY_LENGTH_IN_HEX);
                    } catch (CipherException e) {
                        e.printStackTrace();
                        result = "erro";
                    }
                    break;
                case 2:
                    //将WalletFile变成json字符串
                    try {
                        ECKeyPair decrypt = Wallet.decrypt(string[0], wallet);
                        result = objectMapper.writeValueAsString(wallet);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    } catch (CipherException e) {
                        e.printStackTrace();
                        result = "erro";
                    }
                    break;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.cancel();
            dialog.dismiss();
            if (result.equals("erro")) {
                Toast.makeText(context, "密码错误", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent();
            intent.putExtra("result", result);
            switch (type) {
                case 0:

                    break;
                case 1:
                    intent.setClass(context, PrivateKeyActivity.class);
                    break;
                case 2:
                    intent.setClass(context, KeyStoreActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}
