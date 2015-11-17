package br.com.mowa.timesheet.network;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.json.JSONObject;

/**
 * Created by walky on 14/09/2015.
 */
public class CallJsonNetwork {

    public void callJsonObjectGet(String url, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        RequestQueue mRequestQueue = VolleySingleton.getInstance().getRequestQueue();
        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Request.Method.GET, url, responseListener, errorListener);

        mRequestQueue.add(request);
    }


    public void callJsonObjectPost(String url, JSONObject requestBody ,Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        RequestQueue mRequestQueue = VolleySingleton.getInstance().getRequestQueue();
        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Request.Method.POST, url, requestBody, responseListener, errorListener);

        mRequestQueue.add(request);
    }

    public void callJsonImagePut(String url, JSONObject requestBody, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        RequestQueue mRequestQueue = VolleySingleton.getInstance().getRequestQueue();
        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Request.Method.PUT, url, requestBody, responseListener, errorListener);

        mRequestQueue.add(request);
    }


    public void callJsonObjectDelete(String url, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        RequestQueue mRequestQueue = VolleySingleton.getInstance().getRequestQueue();
        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Request.Method.DELETE, url, responseListener, errorListener);

        mRequestQueue.add(request);
    }

    public void callJsonImagePut(String url, String requestBody, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        RequestQueue mRequestQueue = VolleySingleton.getInstance().getRequestQueue();
        CustomJsonImageRequest request = new CustomJsonImageRequest(Request.Method.PUT, url, requestBody, responseListener, errorListener);

        mRequestQueue.add(request);
    }
}
