package com.math_riddles.core.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.math_riddles.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author phuocns on 25/11/2018.
 */

@SuppressWarnings("deprecation")
public abstract class BaseActivity extends AppCompatActivity implements BaseView {

	private ProgressDialog mProgressDialog;
	private Unbinder mUnBinder;
	protected ActionBar mActionBar;
	private AlertDialog mErrorDialog;

	protected boolean isDisplayedHomeAsUpEnabled = true;
	private boolean mIsRedirectToLoginScreen = false;

	@Nullable @BindView(R.id.toolbar) protected Toolbar mToolbar;
	@Nullable @BindView(R.id.tv_title) protected TextView mTitle;

	protected abstract int getLayoutResourceId();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getLayoutResourceId() != 0)
			setContentView(getLayoutResourceId());
		mUnBinder = ButterKnife.bind(this);
		setupActionBar();
	}

	@Override
	protected void onDestroy() {
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}

		if(mUnBinder != null) {
			mUnBinder.unbind();
			mUnBinder = null;
		}

		super.onDestroy();
	}

	public Toolbar getToolbar() {
		return mToolbar;
	}

	public void setDisplayedHomeAsUpEnabled( boolean displayedHomeAsUpEnabled){
		isDisplayedHomeAsUpEnabled = displayedHomeAsUpEnabled;
		setupActionBar();
	}
	public void setToolbar(Toolbar toolbar) {
		mToolbar = toolbar;
	}

	public void setupActionBar() {
		mToolbar = findViewById(R.id.toolbar);
		if (mToolbar != null) {
			setSupportActionBar(mToolbar);
			mActionBar = getSupportActionBar();
			if (mActionBar != null) {
				mActionBar
						.setDisplayHomeAsUpEnabled(isDisplayedHomeAsUpEnabled);
				mActionBar.setDisplayShowTitleEnabled(false);
			}
		}
	}

	private void setDisplayHomeAsUpEnabled(boolean displayHomeAsUpEnabled) {
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled);
        }
    }

	
	public void setScreenTitle(String title) {
	    mTitle = findViewById(R.id.tv_title);
		mTitle.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        if (mTitle != null) mTitle.setText(title);
    }

    public void setScreenTitle(@StringRes int id) {
        setScreenTitle(getString(id));
    }

	@Override
	public void showLoadingDialog() {
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
			mProgressDialog.setTitle("Processing...");
			mProgressDialog.setMessage("Processing...");
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
		} else if (!mProgressDialog.isShowing()) {
			mProgressDialog.show();
		}

	}

	@Override
	public void hideLoadingDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}

	}

	@Override
	public void showErrorDialog(String errorMessage) {
		showErrorDialog(errorMessage, false);
	}

	@Override
	public void showErrorDialog(final String errorMessage,
			final boolean isRedirectToLoginScreen) {
		mIsRedirectToLoginScreen = isRedirectToLoginScreen;
		if (mErrorDialog == null) {
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);

			dialogBuilder.setTitle("Error Messages");
			dialogBuilder.setMessage(errorMessage).setCancelable(false)
					.setOnCancelListener(new OnCancelListener() {

						@Override
						public void onCancel(DialogInterface dialog) {
							dialog.dismiss();
						}
					}).setPositiveButton(R.string.action_close,
							new OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									// Do nothing
								}
							});
			mErrorDialog = dialogBuilder.create();
		}

		if (!mErrorDialog.isShowing()) {
			if(errorMessage.contains("<p>") || errorMessage.contains("<li>"))
				mErrorDialog.setMessage(Html.fromHtml(errorMessage));
			else
				mErrorDialog.setMessage(errorMessage);
			mErrorDialog.show();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}

