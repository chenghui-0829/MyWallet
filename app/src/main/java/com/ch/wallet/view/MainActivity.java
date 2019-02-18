package com.ch.wallet.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.ch.wallet.R;
import com.ch.wallet.util.ActivityManage;
import com.ch.wallet.util.SPUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private Context context = MainActivity.this;
    @BindView(R.id.main_activity_name_text)
    TextView nameText;
    @BindView(R.id.main_activity_address_text)
    TextView addressText;
    private long mExitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ActivityManage.getInstance().addActivity(this);
        InitView();
    }

    private void InitView() {
        nameText.setText((String) SPUtil.get(context, "user", ""));
        addressText.setText((String) SPUtil.get(context, "address", ""));
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                mExitTime = System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            } else {
                ActivityManage.getInstance().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
