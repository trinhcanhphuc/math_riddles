package com.math_riddles.screen.level;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.math_riddles.R;
import com.math_riddles.core.base.BaseActivity;
import com.math_riddles.core.model.Challenge;
import com.math_riddles.core.repository.ChallengeRepository;
import com.math_riddles.screen.challenge.KeyboardFragment;

public class ChallengeActivity extends BaseActivity
        implements QuestionFragment.OnFragmentInteractionListener,
        KeyboardFragment.OnFragmentInteractionListener {

    protected int level;
    protected EditText answerET;
    protected TextView questionContentTV;
    protected Challenge q;
    protected View popupView;
    protected PopupWindow popupWindow;
    private ChallengeRepository challengeRepository;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.challenge_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        challengeRepository = new ChallengeRepository();

        Intent challengeIntent = getIntent();
        level = challengeIntent.getIntExtra("level", 1);
        q = challengeRepository.getById(level);

        questionContentTV = findViewById(R.id.question_content);
        questionContentTV.setText(q.getQuestion());

        answerET = findViewById(R.id.answer);
        answerET.setShowSoftInputOnFocus(false);

        Log.d("level: %d", Integer.toString(level));
    }

    private long getNumberLevels() {
        return challengeRepository.size();
    }

    public void submitAnswer(View view) {
        if (isAnswerTrue(level)) {
            if (level == getNumberLevels()) {
                showChampionPopup(view);
            }
            else {
                showSuccessPopup(view);
            }
        }
        else {
            showWrongAlert();
        }
    }

    public void clearAnswer(View view) {
        answerET.setText("");
    }

    public void clickNumber(View view) {
        String number = view.getTag().toString();
        updateAnswerByValueFromKeyboard(number);
    }

    private void showPopup(View view, int layoutId) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(layoutId, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    private void showChampionPopup(View view) {
        showPopup(view, R.layout.popup_challenge_champion);

        Button return_level_page_btn = popupView.findViewById(R.id.return_level_page_btn);
        return_level_page_btn.setOnClickListener(view1 -> {
            Intent levelsIntent = new Intent(view1.getContext(), LevelActivity.class);
            startActivity(levelsIntent);
            finish();
        });
    }

    public void showHintPopup(View view) {
        showPopup(view, R.layout.popup_challenge_hint);

        TextView solutionTV = popupView.findViewById(R.id.solution_tv);
        solutionTV.setText(getSolution());

        Button exitBtn = popupView.findViewById(R.id.exit_btn);
        exitBtn.setOnClickListener(view1 -> popupWindow.dismiss());
    }

    private void showSuccessPopup(View view) {
        showPopup(view, R.layout.popup_challenge_correct);

        Button nextLevelBtn = popupView.findViewById(R.id.next_level_btn);
        nextLevelBtn.setOnClickListener(view1 -> nextLevel(level));
    }

    private void nextLevel(int level) {
        Intent challengeIntent = new Intent(this, ChallengeActivity.class);
        challengeIntent.putExtra("level", level+1);
        startActivity(challengeIntent);
        finish();
    }

    private void showWrongAlert() {

    }

    private boolean isAnswerTrue(int level) {
        EditText answerET = findViewById(R.id.answer);
        String answerFromUser = answerET.getText().toString();
        return answerFromUser.equals(getAnswer(level));
    }

    private String getSolution() {
        return q.getSolution();
    }

    private String getAnswer(int level) {
        return q.getAnswer(); }

    private void updateAnswerByValueFromKeyboard(String value) {
        EditText answer = findViewById(R.id.answer);
        answer.setText(answer.getText() + value);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
