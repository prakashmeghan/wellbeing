package com.conceptappsworld.wellbeing.util;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;


import com.conceptappsworld.wellbeing.R;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Created by Sprim on 13-08-2016.
 */
public class CommonUtil {

    Context _context;
    PrefsManager prefsManager;
    private final Handler handler;
    private final String TAG = "CommonUtil";

    public CommonUtil(Context context) {
        this._context = context;
        prefsManager = new PrefsManager(this._context);
        handler = new Handler(_context.getMainLooper());
    }

    public void showSnack(View view, String msg, int duration){
        Snackbar snackbar = Snackbar.make(view, msg, duration);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(_context.getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

    public void showSnackOnTop(View view, String msg, int duration){
        Snackbar snackbar = Snackbar.make(view, msg, duration);
        View snackbarView = snackbar.getView();
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)snackbarView.getLayoutParams();
        params.gravity = Gravity.TOP;
        snackbarView.setLayoutParams(params);
        snackbarView.setBackgroundColor(_context.getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

    public void showSnackOnTopForCL(View view, String msg, int duration){
        Snackbar snackbar = Snackbar.make(view, msg, duration);
        View snackbarView = snackbar.getView();
        CoordinatorLayout.LayoutParams params =(CoordinatorLayout.LayoutParams)snackbarView.getLayoutParams();
        params.gravity = Gravity.TOP;
        snackbarView.setLayoutParams(params);
        snackbarView.setBackgroundColor(_context.getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

    public void snackDesign(Snackbar snackbar){
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(_context.getResources().getColor(R.color.colorAccent));
//        snackbar.show();
    }

    public String getCurrentDate(){
        Date cDate = new Date();
        String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        return fDate;
    }

    public String getCurrentTime(){
        Date cDate = new Date();
        String fTime = new SimpleDateFormat("HHmmss").format(cDate);
        return fTime;
    }

    public String getRandomImage(String... images){
        String image = "";
        String image1 = images[0];
        String image2 = images[1];
        String image3 = images[2];

        if(image2 == null || image2.equalsIgnoreCase("null")){
            image2 = image1;
        }

        if(image3 == null || image3.equalsIgnoreCase("null")){
            image3 = image1;
        }

        Random rand = new Random();

        int  n = rand.nextInt(3) + 1;

        switch (n){
            case 1:
                image = image1;
                break;
            case 2:
                image = image2;
                break;
            case 3:
                image = image3;
                break;
            default:
                image = image1;
                break;
        }
        return image;
    }

    public String uploadFile(String sourceFileUri, final int imgNumber, final View view) {

        //sourceFileUri.replace(sourceFileUri, "ashifaq");
        //

        String fileName = null;
        final String imagePath = null;
        String upLoadServerUri = "http://www.conceptappsworld.com/projects/dreamtripgo/upload.php";
        int serverResponseCode = 0;

        int day, month, year;
        int second, minute, hour;
        GregorianCalendar date = new GregorianCalendar();

        day = date.get(Calendar.DAY_OF_MONTH);
        month = date.get(Calendar.MONTH);
        year = date.get(Calendar.YEAR);

        second = date.get(Calendar.SECOND);
        minute = date.get(Calendar.MINUTE);
        hour = date.get(Calendar.HOUR);

        String name = (hour+""+minute+""+second+""+day+""+(month+1)+""+year);
        String tag = "profile_pic-" + name + ".jpg";
        fileName = sourceFileUri.replace(sourceFileUri,tag);

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri.substring(6));

        if (!sourceFile.isFile()) {

//            dialog.dismiss();

            Log.e("uploadFile", "Source File not exist :"+imagePath);

            runOnUiThread(new Runnable() {
                public void run() {
                    showSnack(view, "Source File not exist :"+ imagePath, Snackbar.LENGTH_LONG);
//                    messageText.setText("Source File not exist :"+ imagepath);
                }
            });

            return "file not exist";

        }
        else
        {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploads", tag);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploads\";filename=\""
                        + tag + "\"" + lineEnd);

                dos.writeBytes(lineEnd);




                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){

                    runOnUiThread(new Runnable() {
                        public void run() {
                            String msg = "Image " + imgNumber + " uploaded.";
//                            messageText.setText(msg);
                            showSnack(view, msg, Snackbar.LENGTH_LONG);
//                            Toast.makeText(PostActivity.this, "File Upload Complete.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

//                dialog.dismiss();
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
//                        messageText.setText("MalformedURLException Exception : check script url.");
                        showSnack(view, "MalformedURLException Exception : check script url.", Snackbar.LENGTH_LONG);
//                        Toast.makeText(AddAttractionActivity.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

//                dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
//                        messageText.setText("Got Exception : see logcat ");
                        showSnack(view, "Got Exception : see logcat ", Snackbar.LENGTH_LONG);
//                        Toast.makeText(AddAttractionActivity.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e(TAG, "Exception : "  + e.getMessage(), e);
            }
//            dialog.dismiss();
            return fileName;

        }
    }

    private void runOnUiThread(Runnable r) {
        handler.post(r);
    }

}
