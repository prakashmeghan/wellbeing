package com.conceptappsworld.wellbeing.asynctask;

import android.app.Activity;
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
 * Created by Sprim on 03-09-2016.
 */
public class LoginAsyncTask extends AsyncTask<Void, Void, Boolean> {

    private final String TAG = "LoginAsyncTask";

    private final String mEmail;
    private final String mPassword;
    Context _context;
    Activity activity;

    String jsonStrLogin;

    JSONObject jsonObjLogin;

    String resultMessage;

    PrefsManager prefsManager;

    AsyncResponse delegate = null;

    public LoginAsyncTask(Activity activity, String email, String password, AsyncResponse delegate) {
        mEmail = email;
        mPassword = password;
        this.activity = activity;
        prefsManager = new PrefsManager(this.activity);
        this.delegate = delegate;
    }

    public LoginAsyncTask(Context context, String email, String password, AsyncResponse delegate) {
        mEmail = email;
        mPassword = password;
        this._context = context;
        prefsManager = new PrefsManager(this._context);
        this.delegate = delegate;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.

        boolean error = true;

        try {
            // Simulate network access.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<NameValuePair> nameValuePairs = new ArrayList<>();

        nameValuePairs.add(new BasicNameValuePair(Constants.NODE_EMAIL, mEmail));
        nameValuePairs.add(new BasicNameValuePair(Constants.NODE_PASSWORD, mPassword));

        jsonStrLogin = null;

        WebServiceHandler sh = new WebServiceHandler();
        jsonStrLogin = sh.makeWebServiceCall(Constants.URL_LOGIN, WebServiceHandler.POST, nameValuePairs);

        if (jsonStrLogin != null) {
            Log.i(TAG, "jsonStrLogin:" + jsonStrLogin);
            try {
                jsonObjLogin = new JSONObject(jsonStrLogin);
                error = jsonObjLogin.getBoolean(Constants.NODE_ERROR);
                if (error) {
                    resultMessage = jsonObjLogin.getString(Constants.NODE_MESSAGE);
                } else {
                    String fName = jsonObjLogin.getString(Constants.NODE_FNAME);
                    String lName = jsonObjLogin.getString(Constants.NODE_LNAME);
                    String email = jsonObjLogin.getString(Constants.NODE_EMAIL);
                    String mobile = jsonObjLogin.getString(Constants.NODE_MOBILE);
                    String town = jsonObjLogin.getString(Constants.NODE_TOWN);
                    int userId = jsonObjLogin.getInt(Constants.NODE_USER_ID);
                    String profilePic = jsonObjLogin.getString(Constants.NODE_PROFILE_PIC);
                    int status = jsonObjLogin.getInt(Constants.NODE_STATUS);
                    String createdAt = jsonObjLogin.getString(Constants.NODE_CREATED_AT);

//                    String existingEmail = prefsManager.getEmail();
                    /*if(!existingEmail.equalsIgnoreCase("") && !existingEmail.equalsIgnoreCase(email)){
                        prefsManager.setNewLogin(true);
                    } else {
                        prefsManager.setNewLogin(false);
                    }*/

                    /*if(existingEmail.equalsIgnoreCase("")){
                        prefsManager.setNewLogin(true);
                    }else {
                        if(!existingEmail.equalsIgnoreCase(email)){
                            prefsManager.setNewLogin(true);
                        }else {
                            prefsManager.setNewLogin(false);
                        }
                    }*/

                    prefsManager.setFname(fName);
                    prefsManager.setLname(lName);
                    prefsManager.setPassword(mPassword);
                    prefsManager.setUserId(userId);
                    prefsManager.setEmail(email);
                    prefsManager.setMobile(mobile);
                    prefsManager.setTown(town);
                    prefsManager.setProfilePic(profilePic);
                    prefsManager.setStatus(status);
                    prefsManager.setCreatedAt(createdAt);
                }

            } catch (JSONException je) {
                Log.e(TAG, "je:" + je.getMessage());
            }


        } else {
            Log.e(TAG, "json data not found");
        }


        return error;
    }

    @Override
    protected void onPostExecute(final Boolean error) {
        super.onPostExecute(error);
        Log.i(TAG, "login error:" + error);
        delegate.processFinish(error);
    }

}