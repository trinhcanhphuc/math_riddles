package com.math_riddles.core.base;

/**
 * @author phuocns on 25/11/2018.
 */

public interface BaseView {
	void showLoadingDialog();

    void hideLoadingDialog();

    void showErrorDialog(String errorMessage);

    void showErrorDialog(String errorMessage, final boolean isRedirectToLoginScreen);
}

