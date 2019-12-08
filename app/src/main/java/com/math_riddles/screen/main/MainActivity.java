package com.math_riddles.screen.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.math_riddles.R;
import com.math_riddles.core.base.BaseActivity;

public class MainActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int getLayoutResourceId() {
        return R.layout.main_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
