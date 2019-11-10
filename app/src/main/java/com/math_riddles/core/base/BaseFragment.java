package com.math_riddles.core.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author phuocns on 26/11/2018.
 */

public abstract class BaseFragment extends Fragment implements BaseView{

	protected View mRootView;
	protected BaseActivity mContext;
    protected Unbinder mUnBinder;
	
	@Override 
	public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = (BaseActivity) getActivity();
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView();
	}
	
    @Override public void onDestroyView() {
    	mUnBinder.unbind();
        super.onDestroyView();
    }
    
    protected abstract void initView();

	@Override
	public void showLoadingDialog() {
		BaseActivity activity = (BaseActivity) getActivity();
        if (activity != null) {
            activity.showLoadingDialog();
        }
	}

	@Override
	public void hideLoadingDialog() {
		BaseActivity activity = (BaseActivity) getActivity();
        if (activity != null) {
            activity.hideLoadingDialog();
        }
	}

	@Override
	public void showErrorDialog(String errorMessage) {
		showErrorDialog(errorMessage, false);
	}

	@Override
	public void showErrorDialog(String errorMessage,
			boolean isRedirectToLoginScreen) {
		 BaseActivity activity = (BaseActivity) getActivity();
	        if (activity != null) {
	            activity.showErrorDialog(errorMessage, isRedirectToLoginScreen);
	        }
	}
}
