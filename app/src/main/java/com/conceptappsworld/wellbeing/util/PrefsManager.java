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
        try{
            this._context = context;
            mPreference = _context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
            mEditor = mPreference.edit();
        }catch (Exception e){
            Log.i(TAG, "Exception: " + e.getMessage());
            e.printStackTrace();
        }

    }


    public void setPassword(String password){
        mPreference.edit()
                .putString(Constants.PREF_PASSWORD, password).apply();
    }

    public String getPassword(){
        String password = mPreference.getString(Constants.PREF_PASSWORD, "");
        return password;
    }

    public void setFname(String fName){
        mPreference.edit()
                .putString(Constants.PREF_FNAME, fName).apply();
    }

    public String getLname(){
        String lName = mPreference.getString(Constants.PREF_LNAME, "");
        return lName;
    }

    public void setLname(String lName){
        mPreference.edit()
                .putString(Constants.PREF_LNAME, lName).apply();
    }

    public String getFname(){
        String fName = mPreference.getString(Constants.PREF_FNAME, "");
        return fName;
    }

    public void setEmail(String email){
        mPreference.edit()
                .putString(Constants.PREF_EMAIL, email).apply();
    }

    public String getEmail(){
        String email = mPreference.getString(Constants.PREF_EMAIL, "");
        return email;
    }

    public void setProfilePic(String profilePic){
        mPreference.edit()
                .putString(Constants.PREF_PROFILE_PIC, profilePic).apply();
    }

    public String getProfilePic(){
        String profilePic = mPreference.getString(Constants.PREF_PROFILE_PIC, "");
        return profilePic;
    }

    public void setCreatedAt(String createdAt){
        mPreference.edit()
                .putString(Constants.PREF_CREATED_AT, createdAt).apply();
    }

    public String getCreatedAt(){
        String createdAt = mPreference.getString(Constants.PREF_CREATED_AT, "");
        return createdAt;
    }


    public void setStatus(int status){
        mPreference.edit()
                .putInt(Constants.PREF_STATUS, status).apply();
    }

    public int getStatus(){
        int status = mPreference.getInt(Constants.PREF_STATUS, 2);
        return status;
    }

    public void setRememberMe(boolean rememberMe){
        mPreference.edit()
                .putBoolean(Constants.PREF_REMEMBER_ME, rememberMe).apply();
    }

    public boolean getRememberMe(){
        boolean rememberMe = mPreference.getBoolean(Constants.PREF_REMEMBER_ME, false);
        return rememberMe;
    }

    public void setLoggedIn(boolean loggedIn){
        mPreference.edit()
                .putBoolean(Constants.PREF_LOGGED_IN, loggedIn).apply();
    }

    public boolean getLoggedIn(){
        boolean loggedIn = mPreference.getBoolean(Constants.PREF_LOGGED_IN, false);
        return loggedIn;
    }

    public void setLoginMethod(int loginMethod){
        mPreference.edit()
                .putInt(Constants.PREF_LOGIN_METHOD, loginMethod).apply();
    }

    public int getLoginMethod(){
        int loginMethod = mPreference.getInt(Constants.PREF_LOGIN_METHOD, 0);
        return loginMethod;
    }

    public void setUserId(int userId){
        mPreference.edit()
                .putInt(Constants.PREF_USER_ID, userId).apply();
    }

    public int getUserId(){
        int userId = mPreference.getInt(Constants.PREF_USER_ID, 0);
        return userId;
    }

    public void setContinentName(String continent){
        mPreference.edit()
                .putString(Constants.PREF_CONTINENT_NAME, continent).apply();
    }

    public String getContinentName(){
        String continent = mPreference.getString(Constants.PREF_CONTINENT_NAME, "");
        return continent;
    }

    public void setCountryName(String country){
        mPreference.edit()
                .putString(Constants.PREF_COUNTRY_NAME, country).apply();
    }

    public String getCountryName(){
        String country = mPreference.getString(Constants.PREF_COUNTRY_NAME, "");
        return country;
    }

    public void setDestinationName(String destination){
        mPreference.edit()
                .putString(Constants.PREF_DESTINATION_NAME, destination).apply();
    }

    public String getDestinationName(){
        String destination = mPreference.getString(Constants.PREF_DESTINATION_NAME, "");
        return destination;
    }

    public void setContinentId(int continent){
        mPreference.edit()
                .putInt(Constants.PREF_CONTINENT_ID, continent).apply();
    }

    public int getContinentId(){
        int continent = mPreference.getInt(Constants.PREF_CONTINENT_ID, 0);
        return continent;
    }

    public void setCountryId(int country){
        mPreference.edit()
                .putInt(Constants.PREF_COUNTRY_ID, country).apply();
    }

    public int getCountryId(){
        int country = mPreference.getInt(Constants.PREF_COUNTRY_ID, 0);
        return country;
    }

    public void setDestinationId(int destination){
        mPreference.edit()
                .putInt(Constants.PREF_DESTINATION_ID, destination).apply();
    }

    public int getDestinationId(){
        int destination = mPreference.getInt(Constants.PREF_DESTINATION_ID, 0);
        return destination;
    }

    public void setNewLogin(boolean isNewLogin){
        mPreference.edit()
                .putBoolean(Constants.PREF_IS_NEW_LOGIN, isNewLogin).apply();
    }

    public boolean isNewLogin(){
        boolean isNewLogin = mPreference.getBoolean(Constants.PREF_IS_NEW_LOGIN, false);
        return isNewLogin;
    }

    public void setCountryPos(int countryPos){
        mPreference.edit()
                .putInt(Constants.PREF_COUNTRY_POS, countryPos).apply();
    }

    public int getCountryPos(){
        int country = mPreference.getInt(Constants.PREF_COUNTRY_POS, 0);
        return country;
    }

    public void setDestinationPos(int destinationPos){
        mPreference.edit()
                .putInt(Constants.PREF_DESTINATION_POS, destinationPos).apply();
    }

    public int getDestinationPos(){
        int destination = mPreference.getInt(Constants.PREF_DESTINATION_POS, 0);
        return destination;
    }

    public String getDesc(){
        String desc = mPreference.getString(Constants.PREF_DESC, "");
        return desc;
    }

    public void setDesc(String desc){
        mPreference.edit()
                .putString(Constants.PREF_DESC, desc).apply();
    }

}
