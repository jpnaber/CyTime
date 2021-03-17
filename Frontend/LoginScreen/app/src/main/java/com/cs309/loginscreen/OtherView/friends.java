package com.cs309.loginscreen.OtherView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cs309.loginscreen.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Josh
 */
public class friends extends AppCompatActivity implements View.OnClickListener{

    private BottomNavigationView navBar;
    private EditText search;
    private Button addFriend;


    RequestQueue reqQueueAdd;
    JsonObjectRequest jsonReq;
    RequestQueue reqQueue;
    TextView taskText;

    static final String TAG = friends.class.getName();

    String LOCAL_URL = "http://192.168.1.3:8080";
    String SERVER = "http://coms-309-mc-10.cs.iastate.edu:8080";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        search = findViewById(R.id.editTextSearchFriend);
        addFriend = findViewById(R.id.buttonAddFriend);

        navBar = findViewById(R.id.bottomNav);
        navBar.setOnNavigationItemSelectedListener(bottomNavMethod);
        Menu menu = navBar  .getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        addFriend.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAddFriend:
                //searchRequest
                //sendRequest
                //receiveRequest and update friends page
                break;
        }


    }
    /**
     * check to see if given name is valid, aso
     * see if the given user name is in the database already.
     * If both are true, return true
     * @return result - boolean result
     */
    private boolean searchRequest(){
        return false;
    }
    /**
     * Takes userName and friendName as String, wrap them into a JSON object, and
     * sends a POST request to server with this JSON object, in the format
     * {"user" : "your_name", "task" : "task_name"}
     *
     * Shows server response in Toast for debugging purposes.
     *
     * @param userName - String of user name
     * @param friendName - String of second user name
     */
    private void sendRequest(final String userName, final String friendName) {

        JSONObject reqObj = new JSONObject();
        try {
            reqObj.put("user1", userName);
            reqObj.put("user2", friendName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonReq = new JsonObjectRequest(Request.Method.POST,
                LOCAL_URL + "/mytask", reqObj, new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(),
                        "Response :" + response.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error :" + error.toString());
                error.printStackTrace();
            }
        }) {

        };
        reqQueueAdd.add(jsonReq);
    }

//    /**
//     * Sends a GET request to server, and parse the JSON object returned by the server.
//     * Shows ID, userName, taskName of every task on screen upon clicking refresh.
//     *
//     * Shows JSON package in String in Toast for debugging purposes.
//     */
//    private void getResponse() {
//        reqQueue = Volley.newRequestQueue(this);
//
//        jsonReq = new JsonArrayRequest(Request.Method.GET,
//                LOCAL_URL + "/alltasks", null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                Toast.makeText(getApplicationContext(),
//                        "Response :" + response.toString(), Toast.LENGTH_LONG).show();
//                try {
//                    taskText.setText("");
//                    for (int i = 0; i < response.length(); i++) {
//                        JSONObject task = response.getJSONObject(i);
//                        taskText.append("ID: " + task.getString("id") + "\n" +
//                                "User: " + task.getString("user") + "\n" +
//                                "Task: " + task.getString("task") + "\n\n");
//
//                        taskText.append(task.getString("id"));
//
//                    }
//                } catch (JSONException e) {
//                    Log.i(TAG,"Error :" + e.toString());
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.i(TAG,"Error :" + error.toString());
//                error.printStackTrace();
//            }
//        });
//        reqQueue.add(jsonReq);
//    }


    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod=new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){

                        case R.id.home:
                            Intent intent = new Intent(friends.this, secondActivity.class);
                            startActivity(intent);
                            break;

                        case R.id.friends:
                            Intent intent2 = new Intent(friends.this, friends.class);
                            startActivity(intent2);
                            break;

                        case R.id.settings:
                            Intent intent3 = new Intent(friends.this, settings.class);
                            startActivity(intent3);
                            break;

                    }

                    return true;
                }
            };
}