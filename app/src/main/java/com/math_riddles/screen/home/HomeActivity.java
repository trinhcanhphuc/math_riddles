package com.math_riddles.screen.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.math_riddles.LevelActivity;
import com.math_riddles.R;
import com.math_riddles.core.base.BaseActivity;

import java.util.logging.Level;

public class HomeActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void play(View v) {
        return;
    }

    protected void levels(View v) {
        Intent levelsIntent = new Intent(this, LevelActivity.class);
        startActivity(levelsIntent);
    }

    protected void followUs(View v) {
        return;
    }

    protected void soundsOn(View v) {
        return;
    }

    protected void restart(View v) {
        return;
    }

    protected void exit(View v) {
        return;
    }
}
