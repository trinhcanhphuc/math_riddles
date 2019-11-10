package com.math_riddles.core.base;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.math_riddles.MathRiddlesApplication;
import com.math_riddles.R;
import com.math_riddles.common.Constant;
import com.math_riddles.common.LoggerUtil;
import com.math_riddles.core.service.RestApi;
import com.math_riddles.core.service.ServiceException;

import java.net.SocketTimeoutException;
import io.reactivex.functions.Consumer;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.HttpException;

/**
 * @author phuocns on 26/11/2018.
 */

public class BasePresenter <V extends BaseView> {
    private volatile V view;
    protected CompositeDisposable mCompositeDisposable;
    protected RestApi mRestApi;

    public void bindView(@NonNull V view) {
        this.view = view;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @CallSuper private void unbindView(@NonNull V view) {

        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }

        this.view = null;
    }

    @Nullable protected V view() {
        return view;
    }

    @CallSuper public void unbindView() {
        unbindView(view);
    }

    public Consumer<Throwable> getNetErrorConsumer(
            final boolean isRedirectLoginScreen, final boolean isShowDialog) {

        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                view.hideLoadingDialog();
                LoggerUtil.e(this.getClass().getSimpleName(), "getNetErrorConsumer()", throwable.getMessage());

                if (throwable instanceof SocketTimeoutException) {
                    throwSocketTimeoutException(isShowDialog, isRedirectLoginScreen);
                    return;
                }

                if (throwable instanceof HttpException) {
                    throwHttpException(isShowDialog, isRedirectLoginScreen);
                    return;
                }

                if (throwable instanceof ServiceException) {
                    throwServiceException(throwable, isShowDialog, isRedirectLoginScreen);
                    return;
                }

                String msg = MathRiddlesApplication.getInstance().getString(R.string.service_reponse_error_no_code);
                if (isShowDialog) view.showErrorDialog(msg, isRedirectLoginScreen);
            }
        };
    }

    private void throwServiceException(Throwable throwable, boolean isShowDialog, boolean isRedirectLoginScreen) {
        ServiceException ex = (ServiceException) throwable;
        String msg = ex.getMessage();
        String code = ex.getCode();

        if (code.equalsIgnoreCase(Constant.ERROR_NETWORK_NOT_AVAILABLE)) {
            msg = MathRiddlesApplication.getInstance().getString(R.string.err_network_not_available);
        }

        if (TextUtils.isEmpty(msg)) {
            msg = MathRiddlesApplication.getInstance().getString(R.string.service_reponse_error_no_code);
        }

        if (isShowDialog) {
            view.showErrorDialog(msg, isRedirectLoginScreen);
        }
    }

    private void throwHttpException(boolean isShowDialog, boolean isRedirectLoginScreen) {
        String msg = MathRiddlesApplication.getInstance().getString(R.string.err_network_not_connected);
        if (isShowDialog) view.showErrorDialog(msg, isRedirectLoginScreen);
    }

    private void throwSocketTimeoutException(boolean isShowDialog, boolean isRedirectLoginScreen){
        String msg = MathRiddlesApplication.getInstance().getString(R.string.err_timeout);
        if (isShowDialog) view.showErrorDialog(msg, isRedirectLoginScreen);
    }

}
