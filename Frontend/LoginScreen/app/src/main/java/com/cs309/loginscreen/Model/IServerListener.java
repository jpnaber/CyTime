package com.cs309.loginscreen.Model;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Listener interface for Presenter classes.
 * Implement this if you want to use the API to talk
 * to the server. It handles all the success, error messages,
 * and it works with our debug system.
 *
 * @author Bofu
 */
public interface IServerListener {
    /**
     * Listens to response
     * @param s - string of success message
     */
    void onSuccess(String s);

    /**
     * Listens to response
     * @param jArr - JSON array object
     */
    void onSuccess(JSONArray jArr);

    /**
     * Listens to response
     * @param jObj - JSON object
     */
    void onSuccess(JSONObject jObj);

    /**
     * Listens to error response
     * @param s - string of error message
     */
    void onError(String s);
}
