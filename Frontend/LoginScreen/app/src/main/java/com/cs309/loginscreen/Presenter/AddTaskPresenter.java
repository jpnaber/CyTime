package com.cs309.loginscreen.Presenter;

import android.content.Context;

import com.android.volley.Request;
import com.cs309.loginscreen.Model.IServerListener;
import com.cs309.loginscreen.Model.ServerRequest;
import com.cs309.loginscreen.View.IAddTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Handles data, logic, and sets view for AddTask screen
 * It implements the IServerListener to listen to the server response.
 * It uses ServerRequest to send new Task Object to store in the server.
 * It takes the edit texts on screen, parse them into JSON object,
 * and send it to server using ServerRequest.
 *
 * @author Bofu
 */
public class AddTaskPresenter implements IServerListener {

    IAddTask view;          // IAddTask View taken as input
    Context context;        // Context taken as input
    ServerRequest model;    // Constructs a ServerRequest model

    /**
     * Constructs a AddTaskPresenter
     * @param view - AddTask view, the UI controller
     * @param c - Application Context of the UI controller
     */
    public AddTaskPresenter(IAddTask view, Context c) {
        this.view = view;
        this.context = c;
        this.model = new ServerRequest(context, this);
    }

    /**
     * Use the ServerRequest model to post a task to server. This method parses
     * the JSON object.
     * @param userName - input for user name
     * @param taskName - input for task name
     */
    public void addTaskToServer(String userName, String taskName, String dueDate, String dueTime) {
        // Make a JSON request object
        JSONObject jTask = new JSONObject();
        try {
            jTask.put("userName", userName);
            jTask.put("taskName", taskName);
            jTask.put("dueDate", dueDate);
            jTask.put("dueTime", dueTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        model.postObj(jTask, Request.Method.POST, "/addTask");
    }

    /**
     * Make a toast on view, if operation was successful
     * @param s - string of success message
     */
    @Override
    public void onSuccess(String s) {
        view.makeToast(s);
    }

    /**
     * Does nothing. This method should not be called here. If called
     * it will be ignored. This class does not handle JSON array return.
     * @param jArr - JSON array object
     */
    @Override
    public void onSuccess(JSONArray jArr) {
    }

    /**
     * Does nothing. This method should not be called here. If called
     * it will be ignored. This class does not handle JSON object return.
     * @param jObj - JSON object
     */
    @Override
    public void onSuccess(JSONObject jObj) {
    }

    /**
     * On error, it makes a text on view, displaying error messages.
     * @param s - string of error message
     */
    @Override
    public void onError(String s) {
        view.makeText(s);
    }
}
