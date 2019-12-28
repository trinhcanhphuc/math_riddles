package com.math_riddles.screen.level;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.math_riddles.R;
import com.math_riddles.common.CenterZoomLayoutManager;
import com.math_riddles.core.base.BaseActivity;
import com.math_riddles.core.repository.ChallengeRepository;
import com.math_riddles.core.repository.ScoreRepository;

import java.util.ArrayList;

public class LevelActivity extends BaseActivity {
    RecyclerView recyclerView;
    ArrayList<String> challenges;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerViewAdapter RecyclerViewHorizontalAdapter;
    CenterZoomLayoutManager HorizontalLayout ;
    View ChildView ;
    int RecyclerViewItemPosition ;
    private ChallengeRepository challengeRepository;
    private ScoreRepository scoreRepository;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.level_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        challengeRepository = new ChallengeRepository();
        scoreRepository = new ScoreRepository();
        initCardList();
    }

    public void initCardList() {
        View levelList = findViewById( R.id.level_list );
        recyclerView = levelList.findViewById(R.id.rv);

        RecyclerViewLayoutManager = new CenterZoomLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(RecyclerViewLayoutManager);

        // Adding items to RecyclerView.
        AddItemsToRecyclerViewArrayList();

        RecyclerViewHorizontalAdapter = new RecyclerViewAdapter(challenges);

        HorizontalLayout = new CenterZoomLayoutManager(LevelActivity.this, CenterZoomLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);

        recyclerView.setAdapter(RecyclerViewHorizontalAdapter);


        // Adding on item click listener to RecyclerView.
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(LevelActivity.this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

                ChildView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {

                    RecyclerViewItemPosition = recyclerView.getChildAdapterPosition(ChildView);

                    int level = RecyclerViewItemPosition + 1;
                    if (level <= getCurrentLevel()) {
                        executeChallenge(level);
                    }
                    else {
                        unlockPreChallenge(level);
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

    private void executeChallenge(int level) {

        Intent challengeIntent = new Intent(recyclerView.getContext(), ChallengeActivity.class);
        challengeIntent.putExtra("level", level);
        startActivity(challengeIntent);
    }

    private void unlockPreChallenge(int level) {

    }

    public void AddItemsToRecyclerViewArrayList(){

        challenges = new ArrayList<>();
        int numLevels = getNumberLevels();
        int currentLevel = getCurrentLevel();
        for(int i = 0; i < numLevels; i++) {
            String question = ((i+1) <= currentLevel)
                    ? challengeRepository.getById(i+1).getQuestion()
                    : "?";
            challenges.add(question);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableActivity();
    }

    private int getNumberLevels() {

        return challengeRepository.size();
    }

    private int getCurrentLevel() {

        return scoreRepository.getFirst().getChallengeId();
    }
}
