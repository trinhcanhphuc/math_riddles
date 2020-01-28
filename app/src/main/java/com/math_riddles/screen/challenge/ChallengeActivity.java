package com.math_riddles.screen.challenge;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.test.espresso.core.deps.guava.base.Splitter;
import android.support.test.espresso.core.deps.guava.collect.Lists;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.math_riddles.core.model.Score;
import com.math_riddles.core.repository.ChallengeRepository;
import com.math_riddles.core.repository.ScoreRepository;
import com.math_riddles.screen.challenges.ChallengesActivity;
import com.math_riddles.screen.challenges.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChallengeActivity extends BaseActivity
        implements QuestionFragment.OnFragmentInteractionListener,
        KeyboardFragment.OnFragmentInteractionListener,
        ChoiceFragment.OnFragmentInteractionListener {

    protected int challengeId;
    protected EditText answerET;
    protected TextView questionContentTV;
    protected Challenge challenge;
    protected View popupView;
    protected PopupWindow popupWindow;
    protected ArrayList<View> chooseBtns;
    private ChallengeRepository challengeRepository;
    private ScoreRepository scoreRepository;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.challenge_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        challengeRepository = new ChallengeRepository();
        scoreRepository = new ScoreRepository();

        Intent challengeIntent = getIntent();
        challengeId = challengeIntent.getIntExtra("challenge_id", 1);
        challenge = challengeRepository.getById(challengeId);

        questionContentTV = findViewById(R.id.question_content);
        questionContentTV.setText(challenge.getQuestion());

        initButtonsChoose();
    }

    private void initButtonsChoose() {
        chooseBtns =  (findViewById(R.id.btn_list_choose)).getTouchables();
        for(int i = 0; i < chooseBtns.size(); i++) {
            Button chooseBtn = (Button) chooseBtns.get(i);
            chooseBtn.setText(Integer.toString(getOptionValue(challenge, i)));
        }
    }

    private long getChallengesSize() {
        return challengeRepository.size();
    }

    public void submitAnswer(View v) {
        int position = Integer.parseInt(v.getTag().toString());
        int submitValue = getOptionValue(challenge, position-1);
        boolean isAnswerValid = isAnswerValid(submitValue);
        if (isAnswerValid) {
            if (challengeId == getChallengesSize()) {
                showChampionPopup(v);
            }
            else {
                upgradeScore(challengeId);
                showSuccessPopup(v);
            }
        }
        else {
            showWrongAlert();
        }
    }

    public int getOptionValue(Challenge challenge, int position) {
        List<String> options = Lists.newArrayList(Splitter.on(",").split(challenge.getOptions()));
        return Integer.parseInt(options.get(position));
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
        // which view you pass in doesn't matter, it is only used for the window token
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    private void showChampionPopup(View view) {
        showPopup(view, R.layout.popup_challenge_champion);

        Button return_challenges_page_btn = popupView.findViewById(R.id.return_challenges_page_btn);
        return_challenges_page_btn.setOnClickListener(view1 -> {
            Intent challengesIntent = new Intent(view1.getContext(), ChallengesActivity.class);
            startActivity(challengesIntent);
            finish();
        });
    }

    private void dynamicAddChoiceFrag() {
        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ftAdd = fm.beginTransaction();
        ftAdd.add(R.id.challenge_activity, new ChoiceFragment());
        ftAdd.commit();
    }

    public void showHintPopup(View view) {
        showPopup(view, R.layout.popup_challenge_hint);

        TextView solutionTV = popupView.findViewById(R.id.solution_tv);
//        solutionTV.setText(getSolution());

        Button exitBtn = popupView.findViewById(R.id.exit_btn);
        exitBtn.setOnClickListener(view1 -> popupWindow.dismiss());
    }

    private void showSuccessPopup(View view) {
        showPopup(view, R.layout.popup_challenge_correct);

        Button nextChallengeBtn = popupView.findViewById(R.id.next_challenge_btn);
        nextChallengeBtn.setOnClickListener(view1 -> nextChallenge(challengeId));
    }

    private void upgradeScore(int challengeId) {
        Score currentScore = scoreRepository.getFirst();
        if (challengeId+1 > currentScore.getChallengeId()) {
            scoreRepository.updateChallengeId(challengeId + 1);
        }
    }

    private void nextChallenge(int challengeId) {
        Intent challengeIntent = new Intent(this, ChallengeActivity.class);
        challengeIntent.putExtra("challenge_id", challengeId+1);
        startActivity(challengeIntent);
        finish();
    }

    private void showWrongAlert() {

    }

    private boolean isAnswerValid(int submitValue) {
        return Integer.toString(submitValue).equals(getAnswer());
    }

    private String getAnswer() {
        return challenge.getAnswer(); }

    private void updateAnswerByValueFromKeyboard(String value) {
        EditText answer = findViewById(R.id.answer);
        answer.setText(answer.getText() + value);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
