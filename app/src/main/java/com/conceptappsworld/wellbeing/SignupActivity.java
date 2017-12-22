package com.conceptappsworld.wellbeing;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.conceptappsworld.wellbeing.asynctask.ForgotPasswordAsyncTask;
import com.conceptappsworld.wellbeing.asynctask.UserSignupAsyncTask;
import com.conceptappsworld.wellbeing.model.AsyncResponse;
import com.conceptappsworld.wellbeing.util.CommonUtil;
import com.conceptappsworld.wellbeing.util.ConnectionDetector;
import com.conceptappsworld.wellbeing.util.Constants;
import com.conceptappsworld.wellbeing.util.PrefsManager;


/**
 * A login screen that offers login via email/password.
 */
public class SignupActivity extends AppCompatActivity implements OnClickListener {

    private final String TAG = "SignUpActivity";

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserSignupAsyncTask mUserSignup = null;

    // UI references.
    private EditText etEmail, etFname, etLname;
    private EditText etPwd, etCnfPwd;
    private View mProgressView;
    private View mSignupFormView;
    Button btnSignup;
    TextView tvSignIn;

    // Progress dialog
    private ProgressDialog pDialog;

    PrefsManager prefsManager;

    private static final int PERMISSION_REQUEST_PHONE_STATE = 1;

    CommonUtil commonUtil;
    TextView tvForgotPwd;
    String strEmailForgot;
    EditText etEmailForgot;
    LinearLayout llCancelSubmit;
    ProgressBar pbForgot;
    private ForgotPasswordAsyncTask mForgotPwdTask = null;
    ConnectionDetector internet;
    private LinearLayout llSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initViewIds();
//        noInternet();

        commonUtil = new CommonUtil(SignupActivity.this);
        internet = new ConnectionDetector(SignupActivity.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionCheckPhoneState = ContextCompat.checkSelfPermission(SignupActivity.this,
                    Manifest.permission.READ_PHONE_STATE);
            if (permissionCheckPhoneState == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_PHONE_STATE);
            }
        }

        prefsManager = new PrefsManager(SignupActivity.this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


//        setIconMaterial();

        etPwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptSignup();
                    return true;
                }
                return false;
            }
        });

        setClickListeners();
    }

    /*private void setIconMaterial() {
        final float density = getResources().getDisplayMetrics().density;
        final Drawable drawableName = getResources().getDrawable(R.drawable.name);
        final Drawable drawableFamily = getResources().getDrawable(R.drawable.family);
        final Drawable drawableEmail = getResources().getDrawable(R.drawable.mail);
        final Drawable drawablePwd = getResources().getDrawable(R.drawable.lock);

        final int width = Math.round(35 * density);
        final int height = Math.round(35 * density);

        drawableName.setBounds(0, 0, width, height);
        drawableFamily.setBounds(0, 0, width, height);
        drawableEmail.setBounds(0, 0, width, height);
        drawablePwd.setBounds(0, 0, width, height);

        etFname.setCompoundDrawables(drawableName, null, null, null);
        etLname.setCompoundDrawables(drawableFamily, null, null, null);
        etEmail.setCompoundDrawables(drawableEmail, null, null, null);
        etPwd.setCompoundDrawables(drawablePwd, null, null, null);
        etCnfPwd.setCompoundDrawables(drawablePwd, null, null, null);
    }*/

    private void setClickListeners() {
        tvSignIn.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        tvForgotPwd.setOnClickListener(this);
    }

    private void noInternet() {
        Log.i(TAG, "net: " + internet.isConnectingToInternet());
//        Log.i(TAG, "net2: " + internet.isNetworkAvailable());
//        Log.i(TAG, "net3: " + internet.isNetWorking());
        if (!internet.isConnectingToInternet()) {
            commonUtil.showSnack(llSignup, getResources().getString(R.string.no_internet), Snackbar.LENGTH_LONG);
            return;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                Toast.makeText(SignupActivity.this, "back", Toast.LENGTH_LONG).show();
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initViewIds() {
        etFname = (EditText) findViewById(R.id.et_fname);
        etLname = (EditText) findViewById(R.id.et_lname);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        etCnfPwd = (EditText) findViewById(R.id.et_confirm_pwd);

        btnSignup = (Button) findViewById(R.id.btn_signup);

        tvSignIn = (TextView) findViewById(R.id.tv_signin);

        mSignupFormView = findViewById(R.id.ll_signup);
        mProgressView = findViewById(R.id.login_progress);

        tvForgotPwd = (TextView) findViewById(R.id.tv_forgot_pwd);
        llSignup = (LinearLayout) findViewById(R.id.ll_activity_signup);
    }

    private void attemptSignup() {
//        noInternet();
        if (!internet.isConnectingToInternet()) {
            commonUtil.showSnack(llSignup, getResources().getString(R.string.no_internet), Snackbar.LENGTH_LONG);
        } else {
            if (mUserSignup != null) {
                return;
            }

            // Reset errors.
            etFname.setError(null);
            etLname.setError(null);
            etEmail.setError(null);
            etPwd.setError(null);

            String fName = etFname.getText().toString();
            String lName = etLname.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPwd.getText().toString();
            String cnf_password = etCnfPwd.getText().toString();

            boolean cancel = false;
            View focusView = null;

            if (TextUtils.isEmpty(fName) || !isNameValid(fName)) {
                etFname.setError(getString(R.string.error_field_len));
                focusView = etFname;
                cancel = true;
            } else if (TextUtils.isEmpty(lName) || !isNameValid(lName)) {
                etLname.setError(getString(R.string.error_field_len));
                focusView = etLname;
                cancel = true;
            } else if (TextUtils.isEmpty(email)) {
                etEmail.setError(getString(R.string.error_field_required));
                focusView = etEmail;
                cancel = true;
            } else if (!isEmailValid(email)) {
                etEmail.setError(getString(R.string.error_invalid_email));
                focusView = etEmail;
                cancel = true;
            } else if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
                etPwd.setError(getString(R.string.error_invalid_password));
                focusView = etPwd;
                cancel = true;
            } else if(!isPasswordUpper(password)){
                etPwd.setError(getString(R.string.error_invalid_password_upper));
                focusView = etPwd;
                cancel = true;
            } else if (TextUtils.isEmpty(cnf_password) || !isPasswordValid(cnf_password)) {
                etCnfPwd.setError(getString(R.string.error_invalid_password));
                focusView = etCnfPwd;
                cancel = true;
            } else if(!isPasswordUpper(cnf_password)){
                etCnfPwd.setError(getString(R.string.error_invalid_cnf_password_upper));
                focusView = etCnfPwd;
                cancel = true;
            } else if (!cnf_password.equalsIgnoreCase(password)) {
                etCnfPwd.setError(getString(R.string.error_password_match));
                focusView = etCnfPwd;
                cancel = true;
            }

            if (cancel) {
                focusView.requestFocus();
            } else {
                showProgress(true);
                mUserSignup = new UserSignupAsyncTask(SignupActivity.this, Constants.LOGIN_METHOD_SELF, email, password, fName,
                        lName, new AsyncResponse() {
                    @Override
                    public void processFinish(Object output) {
                        String errorAndMsg = (String) output;

                        String[] resultSplited = errorAndMsg.split(":");

                        mUserSignup = null;
                        showProgress(false);

                        if (resultSplited[0].equalsIgnoreCase(Constants.ERROR_STR_TRUE)) {
                            commonUtil.showSnack(mSignupFormView, Constants.ERROR_MSG + resultSplited[1],
                                    Snackbar.LENGTH_LONG);
                        } else {

                            backToSignIn(Constants.NAV_SIGNUP_TO_LOGIN_REGISTER_SUCCESS);

                        }
                    }
                });
                mUserSignup.execute((Void) null);
            }
        }

    }

    private boolean isPasswordUpper(String pwd) {
        boolean isUpper = false;
        for(int p=0; p<pwd.length(); p++){
            if(Character.isUpperCase(pwd.charAt(p))){
                isUpper = true;
                break;
            }
        }
        return isUpper;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 6;
    }

    private boolean isUserValid(String uName) {
        //TODO: Replace this with your own logic
        return uName.length() > 5;
    }

    private boolean isNameValid(String name) {
        //TODO: Replace this with your own logic
        return name.length() > 3;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            btnSignup.setVisibility(show ? View.GONE : View.VISIBLE);
            btnSignup.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    btnSignup.setVisibility(show ? View.GONE : View.VISIBLE);
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
            btnSignup.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_signin:
                backToSignIn(Constants.NAV_SIGNUP_TO_LOGIN_BY_LINK);
                break;
            case R.id.btn_signup:
//                noInternet();
                attemptSignup();
                break;
            case R.id.tv_forgot_pwd:
                showDialog();
                break;
            default:

                break;
        }
    }

    private void backToSignIn(int navWay) {
        finish();
        Intent intentSignin;
        if (navWay == Constants.NAV_SIGNUP_TO_LOGIN_REGISTER_SUCCESS) {
            intentSignin = new Intent(SignupActivity.this, MainActivity.class);
        } else {
            intentSignin = new Intent(SignupActivity.this, LoginActivity.class);
        }
        intentSignin.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intentSignin.putExtra(Constants.NAV_WAY, navWay);
        startActivity(intentSignin);
    }

    private void showDialog() {
        final Dialog d = new Dialog(SignupActivity.this);
//        d.setTitle(Constants.HEALTH_PARAM_MILK);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_forgot_pwd);
        Button btnCancel = (Button) d.findViewById(R.id.btn_cancel);
        Button btnSubmit = (Button) d.findViewById(R.id.btn_submit);
        etEmailForgot = (EditText) d.findViewById(R.id.et_email_forgot);
        llCancelSubmit = (LinearLayout) d.findViewById(R.id.ll_cancel_submit);
        pbForgot = (ProgressBar) d.findViewById(R.id.forgot_progress);

        btnSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                attemptForgot(d);
            }
        });
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
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

            mForgotPwdTask = new ForgotPasswordAsyncTask(SignupActivity.this, strEmailForgot, new AsyncResponse() {
                @Override
                public void processFinish(Object output) {

                    String result = (String) output;

                    mForgotPwdTask = null;
                    showProgressForgot(false);
                    d.dismiss();

                    String[] resultSplited = result.split(":");
                    if (resultSplited[0].equalsIgnoreCase(Constants.ERROR_STR_TRUE)) {
                        commonUtil.showSnack(mSignupFormView, resultSplited[1],
                                Snackbar.LENGTH_LONG);
                    } else {
                        commonUtil.showSnack(mSignupFormView, Constants.EMAIL_SENT + strEmailForgot,
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

}

