package com.math_riddles;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class LevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        LinearLayout table = (LinearLayout) findViewById(R.id.levels_layout);
        createLevelTable(table);
    }

    private int getNumberLevels() {
        return 100;
    }

    private void createLevelTable(LinearLayout table) {
        int numLevels = getNumberLevels();
        int rowId = 0;
        for(int i=0; i<numLevels;i++) {
            int level = i + 1;
            if (i % 5 == 0) {
                LinearLayout row = new LinearLayout(this);
                rowId = View.generateViewId();
                row.setId(rowId);
                row.setOrientation(LinearLayout.HORIZONTAL);
                table.addView(row);
            }
            LinearLayout row = (LinearLayout) findViewById(rowId);
            createLevelButton(row, level);
        }
    }

    private void createLevelButton(LinearLayout row, int level) {
        Button levelButton = new Button(getBaseContext());
        levelButton.setText(Integer.toString(level));
        row.addView(levelButton);
    }
}
