package com.ch.wallet.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

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

    private static final String TAG = "EthWalletActivity";
    private WalletFile wallet;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);

        wallet = WalletManager.getInstance().loadWallet(this);

        System.out.println("------------->" + wallet);
    }

    public void backBtn(View view) {
        finish();
    }

    public void onExportMnemonic(View view) {


    }

    public void onExportPrivateKey(View view) {
        try {
            ECKeyPair ecKeyPair = Wallet.decrypt("qwer", wallet);
            BigInteger privateKey = ecKeyPair.getPrivateKey();
            //将私钥变成16精致字符串，不需要前缀，左边位数不够补0
            String privateKeyString = Numeric.toHexStringNoPrefixZeroPadded(privateKey, Keys.PRIVATE_KEY_LENGTH_IN_HEX);
            Log.d(TAG, "--------onExportPrivateKey------------->" + privateKeyString);
        } catch (CipherException e) {
            e.printStackTrace();
        }
    }

    public void onExportKeyStore(View view) {

        //将WalletFile变成json字符串
        try {
            String s = objectMapper.writeValueAsString(wallet);
            Log.d(TAG, "----onExportKeyStore------->" + s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
