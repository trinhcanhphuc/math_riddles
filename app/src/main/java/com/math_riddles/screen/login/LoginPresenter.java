package com.math_riddles.screen.login;

import com.math_riddles.core.base.BasePresenter;
import com.math_riddles.core.service.ServiceFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author phuocns on 26/11/2018.
 */

public class LoginPresenter extends BasePresenter<LoginView> {

    public LoginPresenter() {
        mRestApi = ServiceFactory.getServiceInstance();
    }

    public void login() {
        view().showLoadingDialog();
        mCompositeDisposable.add(mRestApi.testApi()
                .subscribeOn(Schedulers.computation())
                .map(trendsResponses -> trendsResponses.isSuccess())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                            view().hideLoadingDialog();
                            view().onLoginSuccess();
                        },
                        getNetErrorConsumer(false, true)));
    }
}
