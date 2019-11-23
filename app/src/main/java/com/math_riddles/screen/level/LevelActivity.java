package com.math_riddles.screen.level;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.math_riddles.R;
import com.math_riddles.core.base.BaseActivity;

public class LevelActivity extends BaseActivity {

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_level;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.levels);

        TableLayout table = (TableLayout) findViewById(R.id.levels_table);
        createLevelRows(table);
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableActivity();
    }

    private int getNumberLevels() {
        return 100;
    }

    private void createLevelRows(TableLayout table) {
        int numLevels = getNumberLevels();


        for (int r=0; r<numLevels/5; r++) {
            TableRow tr = new TableRow(this);
            for (int c=0; c<5; c++) {
                createLevelButton(tr, r*5+c+1);
            }
            table.addView(tr);
        }
    }

    private void createLevelButton(LinearLayout tableRow, int level) {
        Button b = new Button (this);
        b.setText(Integer.toString(level));
        b.setTextSize(10.0f);
        b.setTextColor(Color.rgb( 100, 200, 200));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableActivity();
                Intent questionIntent = new Intent(view.getContext(), QuestionActivity.class);
                questionIntent.putExtra("level",level);
                startActivity(questionIntent);
            }
        });
        tableRow.addView(b);
        b.getLayoutParams().width = 0;
        final float scale = b.getContext().getResources().getDisplayMetrics().density;
        b.getLayoutParams().height = (int) (80 * scale + 0.5f);
        b.setBackground(getDrawable(R.drawable.shadow_button));
        b.setTextColor(getColor(R.color.text_color));
        b.setTextSize(20);
        b.setTypeface(Typeface.MONOSPACE);
    }
}
