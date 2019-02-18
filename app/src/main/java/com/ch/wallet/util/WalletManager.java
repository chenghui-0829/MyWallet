package com.ch.wallet.util;

import android.content.Context;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;

import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicHierarchy;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.wallet.DeterministicSeed;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by CH on 2019/2/15.
 */

public class WalletManager {

    private static WalletManager instance;
    private ObjectMapper objectMapper = new ObjectMapper();
    //m / 44' / 60' / 0' / 0
    //Hardened意思就是派生加固，防止获取到一个子私钥之后可以派生出后面的子私钥
    //必须还有上一级的父私钥才能派生
    private static final ImmutableList<ChildNumber> BIP44_ETH_ACCOUNT_ZERO_PATH =
            ImmutableList.of(new ChildNumber(44, true), new ChildNumber(60, true),
                    ChildNumber.ZERO_HARDENED, ChildNumber.ZERO);


    public static WalletManager getInstance() {
        if (instance == null) {
            synchronized (WalletManager.class) {
                if (instance == null) {
                    instance = new WalletManager();
                }
            }
        }
        return instance;
    }


    /**
     * 创建钱包(这里不涉及BIP协议，为非确定性钱包)
     *
     * @param context
     * @param passWord
     * @return
     */
    public WalletFile createWallet(Context context, String passWord) {

        try {
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            WalletFile walletFile = Wallet.createLight(passWord, ecKeyPair);
            File dir = context.getDir("eth_wallet", Context.MODE_PRIVATE);
            //创建了WalletFile对应的文件
            File file = new File(dir, getWalletFileName(walletFile));
            objectMapper.writeValue(file, walletFile);
            return walletFile;
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //根据WalletFile的文件名字
    private static String getWalletFileName(WalletFile walletFile) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("'UTC--'yyyy-MM-dd'T'HH-mm-ss.SSS'--'");
        return dateFormat.format(new Date()) + walletFile.getAddress() + ".json";
    }

    /**
     * 创建助记词
     */
    public List<String> createMnemonics() throws MnemonicException.MnemonicLengthException {
        SecureRandom secureRandom = new SecureRandom();
        byte[] entropy = new byte[DeterministicSeed.DEFAULT_SEED_ENTROPY_BITS / 8];
        secureRandom.nextBytes(entropy);
        return MnemonicCode.INSTANCE.toMnemonic(entropy);
    }

    /***
     * 通过助记词创建钱包
     * @return
     */
    public WalletFile createWalletByMnemonic(String password) {
        try {
            //1. 通过助记词创建seed
            byte[] seed = MnemonicCode.toSeed(createMnemonics(), "");
            //2. 通过种子派生主私钥
            DeterministicKey rootKey = HDKeyDerivation.createMasterPrivateKey(seed);

            //3. 通过主私钥，派生出第一个地址
            DeterministicHierarchy hierarchy = new DeterministicHierarchy(rootKey);
            //m/44'/60'/0'/0/0
            //parent path: m/44'/60'/0'/0/
            //child number 0
            DeterministicKey deterministicKey = hierarchy.deriveChild(BIP44_ETH_ACCOUNT_ZERO_PATH, false, true, new ChildNumber(0));
            //派生出来的第一个地址对应的私钥
            byte[] privKeyBytes = deterministicKey.getPrivKeyBytes();
            ECKeyPair ecKeyPair = ECKeyPair.create(privKeyBytes);
            //创建keysotore = 使用用户输入的密码加密子私钥
            return Wallet.createLight(password, ecKeyPair);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
