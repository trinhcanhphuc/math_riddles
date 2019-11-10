package com.math_riddles.common;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.math_riddles.MathRiddlesApplication;

/**
 * @author phuocns on 25/11/2018.
 */

public class AppPreferences {

    private static AppPreferences sInstance;

    private SharedPreferences mSecurePreferences;

    public static AppPreferences getInstance() {
        if (sInstance == null) { sInstance = new AppPreferences(); }

        return sInstance;
    }

    private AppPreferences() {
        mSecurePreferences = MathRiddlesApplication.getInstance().getSharedPreferences(
                Constant.SHARED_PREFERENCES_FILE_NAME, MathRiddlesApplication.MODE_PRIVATE);
    }

    public void clear() {
        mSecurePreferences.edit().clear().apply();
    }

    public String getToken() {
        return mSecurePreferences.getString(Constant.SHARED_PREFERENCES_TOKEN, Constant.BLANK);
    }

    public void setToken(String token) {
        if (token != null && 0 < token.length()) {
            mSecurePreferences.edit().putString(Constant.SHARED_PREFERENCES_TOKEN, token).apply();
        }
    }

    public long getUserId() {
        return mSecurePreferences.getLong(Constant.SHARED_PREFERENCES_USER_ID, -1);
    }

    public void setUserId(long userId) {
        if (userId < -1) {
            mSecurePreferences.edit().putLong(Constant.SHARED_PREFERENCES_TOKEN, userId).apply();
        }
    }

    public String getPin() {
        return mSecurePreferences.getString(Constant.SHARED_PREFERENCES_TOKEN, Constant.BLANK);
    }

    public void setPin(String pin) {
        if (!TextUtils.isEmpty(pin)) {
            mSecurePreferences.edit().putString(Constant.SHARED_PREFERENCES_TOKEN, pin).apply();
        }
    }

    public void clearAuthUser() {
        mSecurePreferences.edit()
                .remove(Constant.SHARED_PREFERENCES_TOKEN)
                .remove(Constant.SHARED_PREFERENCES_PIN)
                .remove(Constant.SHARED_PREFERENCES_USER_ID)
                .apply();
    }
}
