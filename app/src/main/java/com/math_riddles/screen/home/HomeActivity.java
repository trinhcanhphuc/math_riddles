package com.math_riddles.screen.home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.math_riddles.screen.challenges.ChallengesActivity;
import com.math_riddles.R;
import com.math_riddles.core.base.BaseActivity;

public class HomeActivity extends BaseActivity {

    Dialog exitDialog;

    @Override
    protected int getLayoutResourceId() {

        return R.layout.home_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Trigger events
        Button playBtn = findViewById(R.id.play_btn);
        playBtn.setOnClickListener(view -> play(view));

        Button exitBtn =  findViewById(R.id.exit_btn);
        exitBtn.setOnClickListener(view -> exitConfirm(view));
    }

    protected void play(View v) {

        Intent challengesIntent = new Intent(v.getContext(), ChallengesActivity.class);
        startActivity(challengesIntent);
    }

    protected void exitConfirm(View v) {

        exitDialog = new Dialog(this, R.style.Theme_Design_Light);
        exitDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        exitDialog.setTitle("");
        exitDialog.setContentView(R.layout.dialog_home_exit);
        exitDialog.show();
        Button dialogExitBtn = exitDialog.findViewById(R.id.dialog_exit_btn);
        dialogExitBtn.setOnClickListener(view -> exitApp(view));

            Button dialogCancelBtn = exitDialog.findViewById(R.id.dialog_cancel_btn);
        dialogCancelBtn.setOnClickListener(view -> cancel(view));
    }

    protected void exitApp(View v) {

        System.exit(1);
    }

    protected void cancel(View v) {

        exitDialog.cancel();
    }
}
