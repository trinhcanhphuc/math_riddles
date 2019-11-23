package com.math_riddles.screen.level;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.math_riddles.R;
import com.math_riddles.core.base.BaseActivity;
import com.math_riddles.screen.question.KeyboardFragment;

import org.w3c.dom.Text;

public class QuestionActivity extends BaseActivity
        implements QuestionFragment.OnFragmentInteractionListener,
        KeyboardFragment.OnFragmentInteractionListener {

    protected int level;
    protected EditText answerET;
    protected TextView questionContentTV;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_question;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent questionIntent = getIntent();
        level = questionIntent.getIntExtra("level", 1);
        String question = getQuestionById(level);

        questionContentTV = (TextView) findViewById(R.id.question_content);
        questionContentTV.setText(question);

        answerET = (EditText) findViewById(R.id.answer);
        answerET.setShowSoftInputOnFocus(false);

        Log.d("level: %d", Integer.toString(level));
    }

    public void submitAnswer(View view) {
        if (isAnswerTrue(level)) {
            showSuccessPopup(view);
        }
        else {
            showWrongAlert(view);
        }
    }

    public void clearAnswer(View view) {
        answerET.setText("");
    }

    public void hint(View view) {

    }

    public void clickNumber(View view) {
        String number = view.getTag().toString();
        updateAnswerByValueFromKeyboard(number);
    }

    private void showSuccessPopup(View view) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_success, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

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

    private void answerFalse() {
        Log.d("answer is ", "false");
    }

    private boolean isAnswerTrue(int level) {
        EditText answerET = (EditText) findViewById(R.id.answer);
        String answerFromUser = answerET.getText().toString();
        return answerFromUser.equals(getAnswer(level));
    }

    private String getHint(int level) {
        return "A B + A B";
    }

    private String getSolution(int level) {
        return "A = 50 B = 10 A / B = 5";
    }

    private String getAnswer(int level) { return Integer.toString(5);}

    private void updateAnswerByValueFromKeyboard(String value) {
        EditText answer = (EditText) findViewById(R.id.answer);
        answer.setText(answer.getText() + value);
    }

    private String getQuestionById(int level) {
        return "A + B = 60\nA - B = 40\nA / B = ?";
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
