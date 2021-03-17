package com.cs309.loginscreen.Model;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Handles GET, POST request to and from the server.
 * This implementation handles:
 * --> JSON Array <-- (@RequestBody in SpringBoot)
 * --> JSON Object <-- (@RequestBody in SpringBoot)
 * --> String Request <-- (@RequestParam in SpringBoot)
 *
 * @author Bofu
 */
public class ServerRequest {
    RequestQueue reqQueue;      // Volley Request Queue
    JsonArrayRequest jArrReq;   // JSON Array Request
    JsonObjectRequest jObjReq;  // Json Object Request
    StringRequest stringReq;    // String Request
    IServerListener mListener;  // IServerListener taken as input
    Context context;            // Context taken as input

    static final String TAG = ServerRequest.class.getName();

//    String LOCAL_URL = "http://192.168.1.3:8080";
    String SERVER = "http://10.24.226.75:8080";
//    String SERVER = "http://192.168.1.3:8080";

    public ServerRequest(Context c, IServerListener l) {
        this.context = c;
        this.mListener = l;
        reqQueue = Volley.newRequestQueue(context);
    }

    /**
     * Gets a Json object with data from the server
     * @param method - GET
     * @param prompt - url extension
     */
    public void getResponseArr(int method, String prompt) {
        jArrReq = new JsonArrayRequest(method, SERVER + prompt, null,
                new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mListener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG,"Error: " + error.toString());
                mListener.onError("Error: " + error.toString());
                error.printStackTrace();
            }
        });
        reqQueue.add(jArrReq);
    }

    /**
     * Post JSON object to server
     * @param reqObj - JSON obj to post
     * @param method - GET / POST / DELETE
     * @param prompt - url extension
     */
    public void postObj(JSONObject reqObj, int method, String prompt) {
        jObjReq = new JsonObjectRequest(method,
                SERVER + prompt, reqObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                mListener.onSuccess("Success!");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error :" + error.toString());
                mListener.onError("Error! " + error.toString());
                error.printStackTrace();
            }
        });
        reqQueue.add(jObjReq);
    }

    /**
     * This is used for String request
     *
     * @param prompt - url and extensions
     * @param method - GET / POST / DELETE
     */
    public void stringRequest(String prompt, int method) {
        stringReq = new StringRequest(method, SERVER + prompt,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mListener.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error :" + error.toString());
                mListener.onError("Error! " + error.toString());
                error.printStackTrace();
            }
        });
        reqQueue.add(stringReq);
    }
}
