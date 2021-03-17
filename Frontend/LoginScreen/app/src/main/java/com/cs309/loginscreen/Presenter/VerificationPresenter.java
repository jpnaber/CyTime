package com.cs309.loginscreen.Presenter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.cs309.loginscreen.Model.IServerListener;
import com.cs309.loginscreen.Model.IVerificationListener;
import com.cs309.loginscreen.Model.ServerRequest;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Presenter for the Verification view Object. It handles the server requests
 * for verification, and it implements the IServerListener to listen to the server
 * response. It can verify the user, sending back success of precise error messages,
 * or it can add the user as a new user.
 *
 * @author Bofu
 */
public class VerificationPresenter implements IServerListener {

    Context context;                    // Context of the Verification class
    ServerRequest model;                // ServerRequest model
    IVerificationListener mListener;    // IVerification listener

    /**
     * Constructs a new Server Request object with Context and the IVerification Listener
     * @param context - Context of Verification class
     * @param mListener - IVerification Listener
     */
    public VerificationPresenter(Context context, IVerificationListener mListener) {
        this.context = context;
        setModel(new ServerRequest(context, this));
        this.mListener = mListener;
    }

    /**
     * Sets the Server Request model
     * @param model - Server Request model to use
     */
    public void setModel(ServerRequest model) {
        this.model = model;
    }

    /**
     * Verify the user with the given user name and password. It sends the combined
     * info to server via String Request using ServerRequest model
     * @param userName - user name
     * @param password - password
     */
    public void verifyUser(String userName, String password) {
        model.stringRequest("/verify?userName=" + userName + "&passW=" + password,
                Request.Method.GET);
    }

    /**
     * Adds the user as a new user. Sends this new user request to Server via String
     * Request using ServerRequest model.
     * @param userName - user name
     * @param password - password
     */
    public void signUp(String userName, String password) {
        String status = "online";
        String prompt = "/addU?userName=" + userName + "&passW=" + password + "&uStatus=online";
        model.stringRequest(prompt, Request.Method.POST);
    }

    /**
     * Server request listener. It listens and passes on the success message from the server.
     * *** NOTE ***
     * mListener's OnSuccess is called IF AND ONLY IF server has verified that the user name
     * and passwords are correct, or the user is added as a new user.
     * If verification failed, then it calls mListener's onFailure method.
     * If server returns connection error or other communication errors, this method will not
     * be called.
     * @param s - string of success message
     */
    @Override
    public void onSuccess(String s) {
        if (s.equals("Success!")) {
            mListener.onSuccess(s);
        }
        else mListener.onFailure(s);
    }

    /**
     * This method is not handled. This object does not expect a JSON Array response.
     * @param jArr - JSON array object
     */
    @Override
    public void onSuccess(JSONArray jArr) {
    }

    /**
     * This method is not handled. This object does not expect a JSON Object response.
     * @param jObj - JSON object
     */
    @Override
    public void onSuccess(JSONObject jObj) {
    }

    /**
     * This method is called if there is connection error with the server. However, it
     * is not implemented at the time, as the server already has error logs, and the
     * ServerRequest object has verbose error log system as well.
     * @param s - string of error message
     */
    @Override
    public void onError(String s) {
    }
}
