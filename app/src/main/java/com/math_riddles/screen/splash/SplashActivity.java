package com.math_riddles.screen.splash;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.math_riddles.common.AppPreferences;
import com.math_riddles.core.base.BaseActivity;
import com.math_riddles.screen.login.LoginActivity;
import com.math_riddles.screen.main.MainActivity;

/**
 * @author phuocns on 26/11/2018.
 */

public class SplashActivity extends BaseActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(() -> {
            String token = AppPreferences.getInstance().getToken();
            if (TextUtils.isEmpty(token)) {
                LoginActivity.start(SplashActivity.this);
            } else {
                MainActivity.start(SplashActivity.this);
            }
            finish();
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected int getLayoutResourceId() {
        return 0;
    }
}
