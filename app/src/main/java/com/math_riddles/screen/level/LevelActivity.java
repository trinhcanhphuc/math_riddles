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

    @Override
    protected int getLayoutResourceId() {
        return R.layout.level_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        challengeRepository = new ChallengeRepository();
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

                    Intent challengeIntent = new Intent(recyclerView.getContext(), ChallengeActivity.class);
                    challengeIntent.putExtra("level", RecyclerViewItemPosition + 1);
                    startActivity(challengeIntent);
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

    public void AddItemsToRecyclerViewArrayList(){

        challenges = new ArrayList<>();
        int numLevels = getNumberLevels();
        for(int i = 0; i < numLevels; i++) {
            challenges.add(challengeRepository.getById(i+1).getQuestion());
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
}
