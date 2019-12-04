package com.math_riddles.screen.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.math_riddles.common.Constant;
import com.math_riddles.core.model.Question;
import com.math_riddles.screen.level.LevelActivity;
import com.math_riddles.R;
import com.math_riddles.core.base.BaseActivity;
import com.math_riddles.screen.level.QuestionActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

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

//    protected void initData() {
//        String data = loadJSONFromAsset("questions.json");
//    }
//
//    public ArrayList<Question> getAllQuestions() {
//        ArrayList<Question> formList = new ArrayList<Question>();
//        try {
//            JSONArray m_jArry = new JSONArray(loadJSONFromAsset("questions.json"));
//            HashMap<String, String> m_li;
//
//            for (int i = 0; i < m_jArry.length(); i++) {
//                JSONObject jo_inside = m_jArry.getJSONObject(i);
//                Long id = jo_inside.getLong("id");
//                String question= jo_inside.getString("question");
//                String answer = jo_inside.getString("answer");
//                String solution = jo_inside.getString("solution");
//
//                //Add your values in your `ArrayList` as below:
//                Question item  = new Question(id, question, answer, solution);
//
//                formList.add(item);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return formList;
//    }
//
//    public String loadJSONFromAsset(String fileName) {
//        try {
//            InputStream is = getAssets().open(fileName);
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            return new String(buffer, "UTF-8");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//    }
}
