package com.cs309.loginscreen.Presenter;

import android.content.Context;

import com.android.volley.Request;
import com.cs309.loginscreen.Model.IServerListener;
import com.cs309.loginscreen.Model.IVerificationListener;
import com.cs309.loginscreen.Model.ServerRequest;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Chat presenter class that handles the logic for the Chat class.
 * It handles the ServerRequest to send Login info, logout info,
 * and change of status for the Chat user. It implements IServerListener
 * for listening to the server responses.
 *
 * @author Bofu
 */
public class ChatPresenter implements IServerListener {

    Context context;        // Context of the Chat view
    ServerRequest model;    // Server Request model
    String userName;        // Stores User Name

    /**
     * Constructs a new ServerRequest Object, taking Chat Context and user name
     * as input.
     * @param c - Chat context
     * @param userName - User Name
     */
    public ChatPresenter(Context c, String userName) {
        this.context = c;
        this.userName = userName;
        setModel(new ServerRequest(context, this));
    }

    /**
     * Sets the ServerRequest model
     * @param model - Server Request model
     */
    public void setModel(ServerRequest model) {
        this.model = model;
    }

    /**
     * Sets the user status to "online" after verified login
     * @param userName - User name to set status for
     * @return - Welcome message
     */
    public String login(String userName) {
        String prompt = "/setStatus?userName=" + userName + "&uStatus=online";
        model.stringRequest(prompt, Request.Method.POST);
        return "Welcome back, " + userName;
    }

    /**
     * Sets the user status to "offline" after clicking logout button
     * @param userName - user name to set the status for
     * @return - Goodbye message
     */
    public String logout(String userName) {
        String prompt = "/setStatus?userName=" + userName + "&uStatus=offline";
        model.stringRequest(prompt, Request.Method.POST);
        return "We will miss you, " + userName;
    }

    /**
     * Sets the user status to any string. Takes a stats String as
     * input, and sets the user status to that input.
     * @param userName - user name
     * @param status - any status to set to
     * @return String of new status message
     */
    public String anyStatus(String userName, String status) {
        String prompt = "/setStatus?userName=" + userName + "&uStatus=" + status;
        model.stringRequest(prompt, Request.Method.POST);
        return "Set " + status;
    }

    /**
     * Server response listener. It is not implemented. This object
     * does not listen to any server response.
     * @param s - string of success message
     */
    @Override
    public void onSuccess(String s) {
    }

    /**
     * Server response listener. It is not implemented. This object
     * does not listen to any server response.
     * @param jArr - JSON array object
     */
    @Override
    public void onSuccess(JSONArray jArr) {
    }

    /**
     * Server response listener. It is not implemented. This object
     * does not listen to any server response.
     * @param jObj - JSON object
     */
    @Override
    public void onSuccess(JSONObject jObj) {

    }

    /**
     * Server response listener. It is not implemented. This object
     * does not listen to any server response.
     * @param s - string of error message
     */
    @Override
    public void onError(String s) {

    }
}
