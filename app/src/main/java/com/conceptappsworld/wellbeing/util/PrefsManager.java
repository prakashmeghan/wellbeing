package com.conceptappsworld.wellbeing.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


/**
 * Created by Sprim on 20-04-2016.
 */
public class PrefsManager {

    private static final String TAG = "PrefsManager";
    // Shared Preferences reference
    SharedPreferences mPreference;
    // Editor for Shared preferences
    SharedPreferences.Editor mEditor;
    // Context
    private Context _context;
    // Sharedpref file name

    // Constructor
    public PrefsManager(Context context) {
        try {
            this._context = context;
            mPreference = _context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
            mEditor = mPreference.edit();
        } catch (Exception e) {
            Log.i(TAG, "Exception: " + e.getMessage());
            e.printStackTrace();
        }

    }


    public void setPassword(String password) {
        mPreference.edit()
                .putString(Constants.PREF_PASSWORD, password).apply();
    }

    public String getPassword() {
        String password = mPreference.getString(Constants.PREF_PASSWORD, "");
        return password;
    }

    public void setFname(String fName) {
        mPreference.edit()
                .putString(Constants.PREF_FNAME, fName).apply();
    }

    public String getLname() {
        String lName = mPreference.getString(Constants.PREF_LNAME, "");
        return lName;
    }

    public void setLname(String lName) {
        mPreference.edit()
                .putString(Constants.PREF_LNAME, lName).apply();
    }

    public String getFname() {
        String fName = mPreference.getString(Constants.PREF_FNAME, "");
        return fName;
    }

    public void setEmail(String email) {
        mPreference.edit()
                .putString(Constants.PREF_EMAIL, email).apply();
    }

    public String getEmail() {
        String email = mPreference.getString(Constants.PREF_EMAIL, "");
        return email;
    }

    public void setProfilePic(String profilePic) {
        mPreference.edit()
                .putString(Constants.PREF_PROFILE_PIC, profilePic).apply();
    }

    public String getProfilePic() {
        String profilePic = mPreference.getString(Constants.PREF_PROFILE_PIC, "");
        return profilePic;
    }

    public void setMobile(String mobile) {
        mPreference.edit()
                .putString(Constants.PREF_MOBILE, mobile).apply();
    }

    public String getMobile() {
        String mobile = mPreference.getString(Constants.PREF_MOBILE, "");
        return mobile;
    }

    public void setTown(String town) {
        mPreference.edit()
                .putString(Constants.PREF_TOWN, town).apply();
    }

    public String getTown() {
        String town = mPreference.getString(Constants.PREF_TOWN, "");
        return town;
    }

    public void setCreatedAt(String createdAt) {
        mPreference.edit()
                .putString(Constants.PREF_CREATED_AT, createdAt).apply();
    }

    public String getCreatedAt() {
        String createdAt = mPreference.getString(Constants.PREF_CREATED_AT, "");
        return createdAt;
    }


    public void setStatus(int status) {
        mPreference.edit()
                .putInt(Constants.PREF_STATUS, status).apply();
    }

    public int getStatus() {
        int status = mPreference.getInt(Constants.PREF_STATUS, 2);
        return status;
    }

    public void setRememberMe(boolean rememberMe) {
        mPreference.edit()
                .putBoolean(Constants.PREF_REMEMBER_ME, rememberMe).apply();
    }

    public boolean getRememberMe() {
        boolean rememberMe = mPreference.getBoolean(Constants.PREF_REMEMBER_ME, false);
        return rememberMe;
    }

    public void setLoggedIn(boolean loggedIn) {
        mPreference.edit()
                .putBoolean(Constants.PREF_LOGGED_IN, loggedIn).apply();
    }

    public boolean getLoggedIn() {
        boolean loggedIn = mPreference.getBoolean(Constants.PREF_LOGGED_IN, false);
        return loggedIn;
    }

    public void setLoginMethod(int loginMethod) {
        mPreference.edit()
                .putInt(Constants.PREF_LOGIN_METHOD, loginMethod).apply();
    }

    public int getLoginMethod() {
        int loginMethod = mPreference.getInt(Constants.PREF_LOGIN_METHOD, 0);
        return loginMethod;
    }

    public void setUserId(int userId) {
        mPreference.edit()
                .putInt(Constants.PREF_USER_ID, userId).apply();
    }

    public int getUserId() {
        int userId = mPreference.getInt(Constants.PREF_USER_ID, 0);
        return userId;
    }
}
