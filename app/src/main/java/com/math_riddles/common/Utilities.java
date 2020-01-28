package com.math_riddles.common;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * @author phuocns on 26/11/2018.
 */

public class Utilities {

    public static String trimWhiteSpace(String input) {
        return input.replaceAll(" ","");
    }
}
