package com.math_riddles.screen.challenges;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.math_riddles.R;
import com.math_riddles.common.CenterZoomLayoutManager;
import com.math_riddles.core.base.BaseActivity;
import com.math_riddles.core.model.Challenge;
import com.math_riddles.core.repository.ChallengeRepository;
import com.math_riddles.core.repository.ScoreRepository;
import com.math_riddles.screen.challenge.ChallengeActivity;

import java.util.ArrayList;

public class ChallengesActivity extends BaseActivity {
    RecyclerView recyclerView;
    ArrayList<Challenge> challenges;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerViewAdapter RecyclerViewHorizontalAdapter;
    CenterZoomLayoutManager HorizontalLayout ;
    View ChildView ;
    int RecyclerViewItemPosition ;
    private ChallengeRepository challengeRepository;
    private ScoreRepository scoreRepository;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.challenges_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        challengeRepository = new ChallengeRepository();
        scoreRepository = new ScoreRepository();
        initCardList();
    }

    public void initCardList() {
        View challengeList = findViewById( R.id.challenge_list );
        recyclerView = challengeList.findViewById(R.id.rv);

        RecyclerViewLayoutManager = new CenterZoomLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(RecyclerViewLayoutManager);

        // Adding items to RecyclerView.
        AddItemsToRecyclerViewArrayList();

        RecyclerViewHorizontalAdapter = new RecyclerViewAdapter(challenges);

        HorizontalLayout = new CenterZoomLayoutManager(ChallengesActivity.this, CenterZoomLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);

        recyclerView.setAdapter(RecyclerViewHorizontalAdapter);


        // Adding on item click listener to RecyclerView.
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(ChallengesActivity.this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

                ChildView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {

                    RecyclerViewItemPosition = recyclerView.getChildAdapterPosition(ChildView);

                    int challenge = RecyclerViewItemPosition + 1;
                    if (challenge <= getCurrentChallenge()) {
                        executeChallenge(challenge);
                    }
                    else {
                        unlockPreChallenge(challenge);
                    }
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    private void executeChallenge(int challengeId) {

        Intent challengeIntent = new Intent(recyclerView.getContext(), ChallengeActivity.class);
        challengeIntent.putExtra("challenge_id", challengeId);
        startActivity(challengeIntent);
        finish();
    }

    private void unlockPreChallenge(int challengeId) {

    }

    public void AddItemsToRecyclerViewArrayList(){

        challenges = new ArrayList<>();
        int numChallenges = getNumberChallenges();
        int currentChallenge = getCurrentChallenge();
        for(int i = 0; i < numChallenges; i++) {
            Challenge challenge = ((i+1) <= currentChallenge)
                    ? challengeRepository.getById(i+1)
                    : new Challenge();
            challenges.add(challenge);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableActivity();
    }

    private int getNumberChallenges() {

        return challengeRepository.size();
    }

    private int getCurrentChallenge() {

        return scoreRepository.getFirst().getChallengeId();
    }
}
