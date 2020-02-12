package com.math_riddles.screen.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.math_riddles.R;
import com.math_riddles.core.base.BaseActivity;
import com.math_riddles.core.service.ServiceFactory;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author phuocns on 26/11/2018.
 */

public class LoginActivity extends BaseActivity implements LoginView{

    @BindView(R.id.act_login_btn_action) AppCompatButton btnLogin;

    private LoginPresenter mPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new LoginPresenter();
        mPresenter.bindView(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.login_activity;
    }

    @OnClick(R.id.act_login_btn_action) void onClick(View view) {
        ServiceFactory.changeApiBaseUrl(ServiceFactory.SERVER_MALAY_URL);
        mPresenter.login();
    }

    @Override
    public void onLoginSuccess() {
        //  is called when call api success
    }
}
