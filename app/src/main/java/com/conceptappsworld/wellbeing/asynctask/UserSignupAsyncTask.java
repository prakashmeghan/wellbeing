package com.conceptappsworld.wellbeing.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.conceptappsworld.wellbeing.model.AsyncResponse;
import com.conceptappsworld.wellbeing.util.Constants;
import com.conceptappsworld.wellbeing.util.PrefsManager;
import com.conceptappsworld.wellbeing.webservice.WebServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sprim on 27-12-2016.
 */

public class UserSignupAsyncTask extends AsyncTask<Void, Void, String> {

    private final String TAG = "UserSignupAsyncTask";
    String jsonStrSignUp;
    String jsonStrRegisterWelcome;
    JSONObject jsonObjSignUp;
    String resultMessage;
    int resultUserId;
    private final String mEmail;
    private final String mPassword;
    private final String mFname;
    private final String mLname;
    PrefsManager prefsManager;
    Context _context;
    AsyncResponse delegate = null;
    int loginMethod;

    public UserSignupAsyncTask(Context context, int loginMethod, String email, String password, String fName,
                               String lName, AsyncResponse delegate) {
        this._context = context;
        mEmail = email;
        mPassword = password;
        mFname = fName;
        mLname = lName;
        prefsManager = new PrefsManager(_context);
        this.delegate = delegate;
        this.loginMethod = loginMethod;
    }

    @Override
    protected String doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.
        String errorAndMSG = "";

        /*try {
            // Simulate network access.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        List<NameValuePair> nameValuePairs = new ArrayList<>();

        nameValuePairs.add(new BasicNameValuePair(Constants.NODE_FNAME, mFname));
        nameValuePairs.add(new BasicNameValuePair(Constants.NODE_LNAME, mLname));
        nameValuePairs.add(new BasicNameValuePair(Constants.NODE_EMAIL, mEmail));
        nameValuePairs.add(new BasicNameValuePair(Constants.NODE_PASSWORD, mPassword));

        jsonStrSignUp = null;
        jsonStrRegisterWelcome = null;

        WebServiceHandler sh = new WebServiceHandler();
        jsonStrSignUp = sh.makeWebServiceCall(Constants.URL_SIGN_UP, WebServiceHandler.POST, nameValuePairs);

        if (jsonStrSignUp != null) {
            Log.i(TAG, "jsonStrSignup:" + jsonStrSignUp);
            try {
                jsonObjSignUp = new JSONObject(jsonStrSignUp);
                boolean error = jsonObjSignUp.getBoolean(Constants.NODE_ERROR);
                resultMessage = jsonObjSignUp.getString(Constants.NODE_MESSAGE);

                if (!error) {

                    List<NameValuePair> nameValuePairsWelcome = new ArrayList<>();

                    nameValuePairsWelcome.add(new BasicNameValuePair("email", mEmail));
                    jsonStrRegisterWelcome = sh.makeWebServiceCall(
                            Constants.URL_REGISTER_WELCOME, WebServiceHandler.POST,
                            nameValuePairsWelcome);
                    if (jsonStrRegisterWelcome != null) {
                        Log.i(TAG, "jsonStrRegisterWelcome:" + jsonStrRegisterWelcome);
                    }

                    resultUserId = jsonObjSignUp.getInt(Constants.NODE_USER_ID);
                    if(loginMethod == Constants.LOGIN_METHOD_FACEBOOK || loginMethod == Constants.LOGIN_METHOD_GOOGLE){
                        saveInSp(resultUserId);
                    }
                    prefsManager.setUserId(resultUserId);

                    errorAndMSG = Constants.ERROR_STR_FALSE + ":" + resultMessage;
                }else {
                    errorAndMSG = Constants.ERROR_STR_TRUE + ":" + resultMessage;
                }

            } catch (JSONException je) {
                Log.e(TAG, "je:" + je.getMessage());
            }


        } else {
            Log.e(TAG, "json data not found");
        }


        return errorAndMSG;
    }

    private void saveInSp(int userId) {
        prefsManager.setFname(mFname);
        prefsManager.setLname(mLname);
        prefsManager.setPassword(mPassword);
        prefsManager.setUserId(userId);
        prefsManager.setEmail(mEmail);
        prefsManager.setStatus(1);
//        prefsManager.setCreatedAt(createdAt);
    }

    @Override
    protected void onPostExecute(final String errorAndMsg) {
        super.onPostExecute(errorAndMsg);
        Log.i(TAG, "login error:" + errorAndMsg);
        delegate.processFinish(errorAndMsg);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}