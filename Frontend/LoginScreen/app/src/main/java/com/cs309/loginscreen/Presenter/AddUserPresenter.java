package com.cs309.loginscreen.Presenter;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.cs309.loginscreen.Model.IServerListener;
import com.cs309.loginscreen.Model.ServerRequest;
import com.cs309.loginscreen.View.IAddTask;
import com.cs309.loginscreen.View.IAddUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Presenter class handling adding new User.
 * It takes the EditText input from the screen,
 * parse them into JSON objects, and send it to
 * the server using ServerRequest.
 *
 * @author Josh
 */
public class AddUserPresenter implements IServerListener {


    IAddUser view;
    Context context;
    ServerRequest model;

    /**
     * Constructs a AddTaskPresenter
     * @param view - AddTask view, the UI controller
     * @param c - Application Context of the UI controller
     */
    public AddUserPresenter(IAddUser view, Context c) {
        this.view = view;
        this.context = c;
        this.model = new ServerRequest(context, this);
    }

    /**
     * Use the ServerRequest model to post a task to server. This method parses
     * the JSON object.
     * @param id - input for google ID
     * @param email - input for google Email
     */
    public void addLoginToServer(String id, String email) {
        // Make a JSON request object
        JSONObject jTask = new JSONObject();
        try {
            jTask.put("user1", id);
            jTask.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        model.postObj(jTask, Request.Method.POST, "/userlogin");
    }

    @Override
    public void onSuccess(String s) {
        view.makeToast(s);
    }

    @Override
    public void onSuccess(JSONArray jArr) {
    }

    @Override
    public void onSuccess(JSONObject jObj) {
    }

    @Override
    public void onError(String s) {
        view.makeToast(s);
    }

    public void setServer(ServerRequest model) {
        this.model = model;
    }
}
