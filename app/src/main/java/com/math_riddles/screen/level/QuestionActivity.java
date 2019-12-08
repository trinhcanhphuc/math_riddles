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
import com.math_riddles.core.model.Question;
import com.math_riddles.core.repository.QuestionRepository;
import com.math_riddles.screen.question.KeyboardFragment;

public class QuestionActivity extends BaseActivity
        implements QuestionFragment.OnFragmentInteractionListener,
        KeyboardFragment.OnFragmentInteractionListener {

    protected int level;
    protected EditText answerET;
    protected TextView questionContentTV;
    protected Question q;
    protected View popupView;
    protected PopupWindow popupWindow;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.question_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent questionIntent = getIntent();
        level = questionIntent.getIntExtra("level", 1);
        q = QuestionRepository.getQuestionById(level);

        questionContentTV = findViewById(R.id.question_content);
        questionContentTV.setText(q.getQuestion());

        answerET = findViewById(R.id.answer);
        answerET.setShowSoftInputOnFocus(false);

        Log.d("level: %d", Integer.toString(level));
    }

    private long getNumberLevels() {
        return QuestionRepository.getNumberQuestions();
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
            showWrongAlert(view);
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
        showPopup(view, R.layout.popup_question_champion);

        Button return_level_page_btn = (Button) popupView.findViewById(R.id.return_level_page_btn);
        return_level_page_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent levelsIntent = new Intent(view.getContext(), LevelActivity.class);
                startActivity(levelsIntent);
                finish();
            }
        });
    }

    public void showHintPopup(View view) {
        showPopup(view, R.layout.popup_question_hint);

        TextView solutionTV = popupView.findViewById(R.id.solution_tv);
        solutionTV.setText(getSolution());

        Button exitBtn = popupView.findViewById(R.id.exit_btn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    private void showSuccessPopup(View view) {
        showPopup(view, R.layout.popup_question_success);

        Button nextLevelBtn = (Button) popupView.findViewById(R.id.next_level_btn);
        nextLevelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextLevel(level);
            }
        });
    }

    private void nextLevel(int level) {
        Intent questionIntent = new Intent(this, QuestionActivity.class);
        questionIntent.putExtra("level", level+1);
        startActivity(questionIntent);
        finish();
    }

    private void showWrongAlert(View view) {

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
        Log.d("question: ", q.toString());
        return q.getAnswer(); }

    private void updateAnswerByValueFromKeyboard(String value) {
        EditText answer = findViewById(R.id.answer);
        answer.setText(answer.getText() + value);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
