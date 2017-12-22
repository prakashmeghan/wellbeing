package com.conceptappsworld.wellbeing.asynctask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.conceptappsworld.wellbeing.model.AsyncResponse;
import com.conceptappsworld.wellbeing.util.Constants;
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
public class ForgotPasswordAsyncTask extends AsyncTask<Void, Void, String> {

    private final String TAG = "ForgotPasswordAsyncTask";

    private final String mEmail;
    Context _context;
    Activity activity;

    String jsonStrForgot;

    JSONObject jsonObjForgot;

    String resultMessage;

    AsyncResponse delegate = null;

    public ForgotPasswordAsyncTask(Context context, String email, AsyncResponse delegate) {
        mEmail = email;
        this._context = context;
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.

        String result = null;

        List<NameValuePair> nameValuePairs = new ArrayList<>();

        nameValuePairs.add(new BasicNameValuePair("email", mEmail));

        jsonStrForgot = null;

        WebServiceHandler sh = new WebServiceHandler();
        jsonStrForgot = sh.makeWebServiceCall(Constants.URL_FORGOT, WebServiceHandler.POST, nameValuePairs);

        if (jsonStrForgot != null) {
            Log.i(TAG, "jsonStrForgot:" + jsonStrForgot);
            try{
                jsonObjForgot = new JSONObject(jsonStrForgot);
                boolean error = jsonObjForgot.getBoolean(Constants.NODE_ERROR);
                if(error){
                    resultMessage = jsonObjForgot.getString(Constants.NODE_MESSAGE);
                    result = Constants.ERROR_STR_TRUE + ":" + resultMessage;
                }else{
                    int userId = jsonObjForgot.getInt(Constants.NODE_USER_ID);
                    int status = jsonObjForgot.getInt(Constants.NODE_STATUS);
                    String createdAt = jsonObjForgot.getString(Constants.NODE_CREATED_AT);

                    result = Constants.ERROR_STR_FALSE + ":" + userId;
                }

            }catch (JSONException je){
                Log.e(TAG, "je:" + je.getMessage());
            }


        } else {
            Log.e(TAG, "json data not found");
        }


        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.i(TAG, "forgot:" + result);
        delegate.processFinish(result);
    }

}