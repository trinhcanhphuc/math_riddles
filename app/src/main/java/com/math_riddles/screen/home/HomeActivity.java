package com.math_riddles.screen.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.math_riddles.screen.level.LevelActivity;
import com.math_riddles.R;
import com.math_riddles.core.base.BaseActivity;
import com.math_riddles.screen.level.QuestionActivity;

import static com.math_riddles.common.Constant.INSTAGRAM_LINK;

public class HomeActivity extends BaseActivity {

    Dialog exitDialog;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Trigger events
        Button playBtn = (Button) findViewById(R.id.play_btn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play(view);
            }
        });

        Button levelsBtn = (Button) findViewById(R.id.levels_btn);
        levelsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                levels(view);
            }
        });

        Button followUsBtn = (Button) findViewById(R.id.follow_us_btn);
        followUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                followUs(view);
            }
        });

        Button soundOnBtn = (Button) findViewById(R.id.sound_on_btn);
        soundOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundsOn(view);
            }
        });

        Button restartBtn = (Button) findViewById(R.id.restart_btn);
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restart(view);
            }
        });

        Button exitBtn = (Button) findViewById(R.id.exit_btn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitConfirm(view);
            }
        });
    }

    protected void play(View v) {
        Intent questionIntent = new Intent(v.getContext(), QuestionActivity.class);
        startActivity(questionIntent);
    }

    protected void levels(View v) {
        Intent levelsIntent = new Intent(v.getContext(), LevelActivity.class);
        startActivity(levelsIntent);
    }

    protected void followUs(View v) {
        openWebURL(INSTAGRAM_LINK);
    }

    protected void soundsOn(View v) {
        return;
    }

    protected void restart(View v) {
        return;
    }

    protected void exitConfirm(View v) {
        exitDialog = new Dialog(this);
        exitDialog.setTitle("");
        exitDialog.setContentView(R.layout.dialog_exit);
        exitDialog.show();
        Button dialogExitBtn = (Button) exitDialog.findViewById(R.id.dialog_exit_btn);
        dialogExitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitApp(view);
            }
        });

            Button dialogCancelBtn = (Button) exitDialog.findViewById(R.id.dialog_cancel_btn);
        dialogCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel(view);
            }
        });
    }

    protected void exitApp(View v) {
        System.exit(1);
    }

    protected void cancel(View v) {
        exitDialog.cancel();
    }
}
