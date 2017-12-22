package com.conceptappsworld.wellbeing;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.conceptappsworld.wellbeing.util.CommonUtil;
import com.conceptappsworld.wellbeing.util.PrefsManager;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "SplashActivity";
    TextView tvLogin;
    TextView tvFb;
    TextView tvGoogle;
    PrefsManager prefsManager;
    private View mProgressView;
    CommonUtil commonUtil;
    RelativeLayout rlActivitySplash;
    private static final int RC_SIGN_IN = 007;
    private ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initObjects();
        findViewByIds();

        Animation animation = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.move);
        ivLogo.startAnimation(animation);

        if (prefsManager.getLoggedIn()) {
            Intent intentMain = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intentMain);
            finish();
        }

        setClickListeners();
    }

    private void googleLogin() {
        commonUtil.showSnack(rlActivitySplash, getString(R.string.under_construction), Snackbar.LENGTH_LONG);
    }

    private void fbLogin() {
        commonUtil.showSnack(rlActivitySplash, getString(R.string.under_construction), Snackbar.LENGTH_LONG);
    }


    private void initObjects() {
        prefsManager = new PrefsManager(SplashActivity.this);
        commonUtil = new CommonUtil(SplashActivity.this);
        ivLogo = (ImageView) findViewById(R.id.iv_logo);
    }

    private void setClickListeners() {
        tvLogin.setOnClickListener(this);
        tvFb.setOnClickListener(this);
        tvGoogle.setOnClickListener(this);
    }

    private void findViewByIds() {
        tvLogin = (TextView) findViewById(R.id.tv_login);
        tvFb = (TextView) findViewById(R.id.tv_fb);
        tvGoogle = (TextView) findViewById(R.id.tv_google);
        mProgressView = findViewById(R.id.social_login_progress);
        rlActivitySplash = (RelativeLayout) findViewById(R.id.activity_splash);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                finish();
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_fb:
                fbLogin();
                break;
            case R.id.tv_google:
                googleLogin();
                break;
            default:

                break;
        }
    }
}
