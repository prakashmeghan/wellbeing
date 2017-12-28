package com.conceptappsworld.wellbeing.asynctask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.conceptappsworld.wellbeing.model.AsyncResponse;
import com.conceptappsworld.wellbeing.model.Category;
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
public class CategoryAsyncTask extends AsyncTask<Void, Void, ArrayList<Category>> {

    private final String TAG = "CategoryAsyncTask";

    Context _context;
    Activity activity;

    String jsonStr;

    JSONObject jsonObj;

    String resultMessage;

    PrefsManager prefsManager;

    AsyncResponse delegate = null;

    public CategoryAsyncTask(Activity activity, AsyncResponse delegate) {
        this.activity = activity;
        prefsManager = new PrefsManager(this.activity);
        this.delegate = delegate;
    }

    public CategoryAsyncTask(Context context, AsyncResponse delegate) {
        this._context = context;
        prefsManager = new PrefsManager(this._context);
        this.delegate = delegate;
    }

    @Override
    protected ArrayList<Category> doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.

        ArrayList<Category> alCats = new ArrayList<Category>();

        boolean error = true;

        try {
            // Simulate network access.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        jsonStr = null;

        WebServiceHandler sh = new WebServiceHandler();
        jsonStr = sh.makeWebServiceCall(Constants.URL_CATEGORIES, WebServiceHandler.GET);

        if (jsonStr != null) {
            Log.i(TAG, "jsonStr:" + jsonStr);
            try {
                jsonObj = new JSONObject(jsonStr);
                error = jsonObj.getBoolean(Constants.NODE_ERROR);
                if (error) {
                    resultMessage = jsonObj.getString(Constants.NODE_MESSAGE);
                } else {
                    Category category = new Category();

                    String categoryName = jsonObj.getString(Constants.NODE_CATEGORY_NAME);
                    int categoryId = jsonObj.getInt(Constants.NODE_CATEGORY_ID);

                    category.setCategoryId(categoryId);
                    category.setCategoryName(categoryName);

                    alCats.add(category);
                }

            } catch (JSONException je) {
                Log.e(TAG, "je:" + je.getMessage());
            }


        } else {
            Log.e(TAG, "json data not found");
        }


        return alCats;
    }

    @Override
    protected void onPostExecute(ArrayList<Category> arrayList) {
        super.onPostExecute(arrayList);
//        Log.i(TAG, "al:" + arrayList);
        delegate.processFinish(arrayList);
    }

}