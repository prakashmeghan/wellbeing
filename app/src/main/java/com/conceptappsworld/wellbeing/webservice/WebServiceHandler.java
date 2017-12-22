package com.conceptappsworld.wellbeing.webservice;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class WebServiceHandler {
    public static final String TAG = "WebServiceHandler";
	static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;
    public final static int PUT = 3;

    final int connectionTimeoutMillis = 5000;
    final int socketTimeoutMillis = 5000;
    
    public WebServiceHandler() {
		// TODO Auto-generated constructor stub
	}
    
    public String makeWebServiceCall(String url, int method) {
        return this.makeWebServiceCall(url, method, null);
    }
    
    public String makeWebServiceCall(String url, int method,
                                     List<NameValuePair> params) {
        Log.d(TAG, "service call url: " + url);
        Log.d(TAG, "service call method: " + method);
        try {
            // http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;
             
            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // adding post params
                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }
 
                httpResponse = httpClient.execute(httpPost);
 
            } else if (method == GET) {
                // appending params to url
                if (params != null) {
                    String paramString = URLEncodedUtils
                            .format(params, "utf-8");
                    url += "?" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);
 
                httpResponse = httpClient.execute(httpGet);
                Log.d(TAG, "httpResponse: " + httpResponse);
 
            } else if(method == PUT){
                HttpPut httpPut = new HttpPut(url);
                httpResponse = httpClient.execute(httpPut);
            }
            httpEntity = httpResponse.getEntity();
            Log.d(TAG, "httpEntity: " + httpEntity);
            response = EntityUtils.toString(httpEntity);
            Log.d(TAG, "response: " + response);
 
        } catch (UnsupportedEncodingException e) {
            Log.d(TAG, "UnsupportedEncodingException: " + e.getMessage());
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Log.d(TAG, "ClientProtocolException: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, "IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e){
            Log.d(TAG, "Exception: " + e.getMessage());
            e.printStackTrace();
        }
         
        return response;
 
    }

    public String makeWebServiceCall(String url, int method,
                                     String entityString, String dummy) {
        try {
            // http client

            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, connectionTimeoutMillis);
            HttpConnectionParams.setSoTimeout(httpParams, socketTimeoutMillis);
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);

//            DefaultHttpClient httpClient = new DefaultHttpClient();

            /*HttpParams params = httpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 5000);
            HttpConnectionParams.setSoTimeout(params, 5000);*/

            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // adding post params
                if (entityString != null) {
//                    httpPost.setEntity(new UrlEncodedFormEntity(params));
//                    httpPost.setEntity(new StringEntity(entityString, "UTF-8"));
                    /*httpPost.setEntity(new ByteArrayEntity(
                            entityString.toString().getBytes("UTF8")));*/
                    StringEntity se = new StringEntity( entityString.toString());
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    httpPost.setEntity(se);
                }

                httpResponse = httpClient.execute(httpPost);

            } else if (method == GET) {
                // appending params to url
                /*if (params != null) {
                    String paramString = URLEncodedUtils
                            .format(params, "utf-8");
                    url += "?" + paramString;
                }*/
                HttpGet httpGet = new HttpGet(url);

                httpResponse = httpClient.execute(httpGet);

            }
            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }
}
