package com.math_riddles.screen.level;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.math_riddles.R;
import com.math_riddles.common.CenterZoomLayoutManager;
import com.math_riddles.core.base.BaseActivity;
import com.math_riddles.core.repository.QuestionRepository;

import java.util.ArrayList;

public class LevelActivity extends BaseActivity {
    RecyclerView recyclerView;
    ArrayList<String> Number;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerViewAdapter RecyclerViewHorizontalAdapter;
    CenterZoomLayoutManager HorizontalLayout ;
    View ChildView ;
    int RecyclerViewItemPosition ;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.level_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.levels);

        initCardList();
    }

    public void initCardList() {
        View levelList = findViewById( R.id.level_list );
        recyclerView = levelList.findViewById(R.id.rv);

        RecyclerViewLayoutManager = new CenterZoomLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(RecyclerViewLayoutManager);

        // Adding items to RecyclerView.
        AddItemsToRecyclerViewArrayList();

        RecyclerViewHorizontalAdapter = new RecyclerViewAdapter(Number);

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

                    Intent questionIntent = new Intent(recyclerView.getContext(), QuestionActivity.class);
                    questionIntent.putExtra("level", RecyclerViewItemPosition + 1);
                    startActivity(questionIntent);
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

        Number = new ArrayList<>();
        long numLevels = getNumberLevels();
        for(long i = 0; i < numLevels; i++) {
            Number.add(Long.toString(i+1));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableActivity();
    }

    private long getNumberLevels() {
        return QuestionRepository.getNumberQuestions();
    }
}
