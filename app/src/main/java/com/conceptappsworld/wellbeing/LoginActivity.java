package com.conceptappsworld.wellbeing;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.conceptappsworld.wellbeing.asynctask.ForgotPasswordAsyncTask;
import com.conceptappsworld.wellbeing.asynctask.LoginAsyncTask;
import com.conceptappsworld.wellbeing.model.AsyncResponse;
import com.conceptappsworld.wellbeing.util.CommonUtil;
import com.conceptappsworld.wellbeing.util.ConnectionDetector;
import com.conceptappsworld.wellbeing.util.Constants;
import com.conceptappsworld.wellbeing.util.PrefsManager;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private LoginAsyncTask mLoginTask = null;
    private ForgotPasswordAsyncTask mForgotPwdTask = null;

    // UI references.
    private EditText etEmail;
    private EditText etPwd;
    private View mProgressView;
    private View mLoginFormView;
//    private View llPwd;
    private Button btLogin;
    private TextView tvSignUp;
//    Switch switchRememberMe;
    CheckBox chkRememberMe;
    TextView tvForgotPwd;

    CommonUtil commonUtil;
    int navWay;
    PrefsManager prefsManager;
    boolean isRememberMe;
    ConnectionDetector connectionDetector;

    String strEmailForgot;
    EditText etEmailForgot;
    LinearLayout llCancelSubmit;
    ProgressBar pbForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        initObjects();

        initViewIds();

//        setIconMaterial();

        isRememberMe = prefsManager.getRememberMe();

//        switchRememberMe.setChecked(isRememberMe);
        chkRememberMe.setChecked(isRememberMe);

        if (isRememberMe) {
            String email = prefsManager.getEmail();
            String password = prefsManager.getPassword();
            etEmail.setText(email);
            etPwd.setText(password);
        }

        navWay = getIntent().getIntExtra(Constants.NAV_WAY, -1);

        if (navWay == Constants.NAV_SIGNUP_TO_LOGIN_REGISTER_SUCCESS) {
            commonUtil.showSnack(mLoginFormView, Constants.SUCCESS_REGISTER_PAYMENT_SUCCESS, Snackbar.LENGTH_LONG);
        }

        setClickListeners();
    }

    private void setClickListeners() {
        btLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        tvForgotPwd.setOnClickListener(this);
    }

    private void initObjects() {
        prefsManager = new PrefsManager(LoginActivity.this);
        connectionDetector = new ConnectionDetector(LoginActivity.this);
        commonUtil = new CommonUtil(LoginActivity.this);
    }

    private void initViewIds() {
        etEmail = (EditText) findViewById(R.id.et_email);
        etPwd = (EditText) findViewById(R.id.et_password);

        btLogin = (Button) findViewById(R.id.bt_login);

        tvSignUp = (TextView) findViewById(R.id.tv_signup);

        mLoginFormView = findViewById(R.id.login_form);
//        llPwd = findViewById(R.id.ll_pwd);
        mProgressView = findViewById(R.id.login_progress);

//        switchRememberMe = (Switch) findViewById(R.id.switch_remember_me);

        chkRememberMe = (CheckBox) findViewById(R.id.chk_remember_me);

        tvForgotPwd = (TextView) findViewById(R.id.tv_forgot_pwd);

        /*etEmail.setText("g@rv.com");
        etPwd.setText("meghani");*/


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_signup:
                Intent intentSignup = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intentSignup);
                finish();
                break;
            case R.id.bt_login:
//                attemptLogin();

                finish();
                Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intentMain);
                break;
            case R.id.tv_forgot_pwd:
                showDialog();
                break;
            default:

                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        navWay = intent.getIntExtra(Constants.NAV_WAY, -1);

        if (navWay == Constants.NAV_SIGNUP_TO_LOGIN_REGISTER_SUCCESS) {
            commonUtil.showSnack(mLoginFormView, Constants.SUCCESS_REGISTER_PAYMENT_SUCCESS, Snackbar.LENGTH_LONG);
        }
    }

    private void attemptLogin() {
        if (mLoginTask != null) {
            return;
        }

        etEmail.setError(null);
        etPwd.setError(null);

        final String email = etEmail.getText().toString();
        String password = etPwd.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            etPwd.setError(getString(R.string.error_invalid_password));
            focusView = etPwd;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            etEmail.setError(getString(R.string.error_field_required));
            focusView = etEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            etEmail.setError(getString(R.string.error_invalid_email));
            focusView = etEmail;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);

            mLoginTask = new LoginAsyncTask(LoginActivity.this, email, password, new AsyncResponse() {
                @Override
                public void processFinish(Object output) {
                    boolean error = (Boolean) output;
                    mLoginTask = null;
                    showProgress(false);

                    if (!error) {
                        /*if (switchRememberMe.isChecked()) {
                            prefsManager.setRememberMe(true);
                        } else {
                            prefsManager.setRememberMe(false);
                        }*/

                        if(chkRememberMe.isChecked()){
                            prefsManager.setRememberMe(true);
                        }else {
                            prefsManager.setRememberMe(false);
                        }
                        prefsManager.setLoggedIn(false);
                        prefsManager.setLoginMethod(Constants.LOGIN_METHOD_SELF);

                        finish();
                        Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intentMain);
                    } else {
                        commonUtil.showSnackOnTop(mLoginFormView, Constants.ERR_LOGIN, Snackbar.LENGTH_LONG);
                    }
                }
            });
            mLoginTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            btLogin.setVisibility(show ? View.GONE : View.VISIBLE);
            btLogin.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    btLogin.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            btLogin.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void attemptForgot(final Dialog d) {
        if (mForgotPwdTask != null) {
            return;
        }

        etEmailForgot.setError(null);

        strEmailForgot = etEmailForgot.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(strEmailForgot)) {
            etEmailForgot.setError(getString(R.string.error_field_required));
            focusView = etEmailForgot;
            cancel = true;
        } else if (!isEmailValid(strEmailForgot)) {
            etEmailForgot.setError(getString(R.string.error_invalid_email));
            focusView = etEmailForgot;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgressForgot(true);

            mForgotPwdTask = new ForgotPasswordAsyncTask(LoginActivity.this, strEmailForgot, new AsyncResponse() {
                @Override
                public void processFinish(Object output) {

                    String result = (String) output;

                    mForgotPwdTask = null;
                    showProgressForgot(false);
                    d.dismiss();

                    String[] resultSplited = result.split(":");
                    if (resultSplited[0].equalsIgnoreCase(Constants.ERROR_STR_TRUE)) {
                        commonUtil.showSnack(mLoginFormView, resultSplited[1],
                                Snackbar.LENGTH_LONG);
                    } else {
                        commonUtil.showSnack(mLoginFormView, Constants.EMAIL_SENT + strEmailForgot,
                                Snackbar.LENGTH_LONG);
                    }
                }
            });
            mForgotPwdTask.execute((Void) null);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgressForgot(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            llCancelSubmit.setVisibility(show ? View.GONE : View.VISIBLE);
            llCancelSubmit.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    llCancelSubmit.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            pbForgot.setVisibility(show ? View.VISIBLE : View.GONE);
            pbForgot.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    pbForgot.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            pbForgot.setVisibility(show ? View.VISIBLE : View.GONE);
            llCancelSubmit.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void showDialog() {
        final Dialog d = new Dialog(LoginActivity.this);
//        d.setTitle(Constants.HEALTH_PARAM_MILK);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_forgot_pwd);
        Button btnCancel = (Button) d.findViewById(R.id.btn_cancel);
        Button btnSubmit = (Button) d.findViewById(R.id.btn_submit);
        etEmailForgot = (EditText) d.findViewById(R.id.et_email_forgot);
        llCancelSubmit = (LinearLayout) d.findViewById(R.id.ll_cancel_submit);
        pbForgot = (ProgressBar) d.findViewById(R.id.forgot_progress);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                attemptForgot(d);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }
}
