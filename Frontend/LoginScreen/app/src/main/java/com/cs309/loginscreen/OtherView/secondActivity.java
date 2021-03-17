package com.cs309.loginscreen.OtherView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.cs309.loginscreen.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

/**
 * @author Josh
 */
public class secondActivity extends AppCompatActivity implements View.OnClickListener {

    private static ListView list;
    private static ArrayList<String> tasks = new ArrayList<String>();
    private static String email;
    private String ID;
    public static GoogleSignInClient mGoogleSignInClient;
    private BottomNavigationView navBar;
    private TextView taskText;
    private Button refresh;
    private ImageButton addTask;

    String task;

    RequestQueue reqQueue;
    JsonArrayRequest jsonReq;

    static final String TAG = secondActivity.class.getName();

    String LOCAL_URL = "http://10.30.55.214:8080/";
    String SERVER = "http://coms-309-mc-10.cs.iastate.edu:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        list = findViewById(R.id.lvlist);
        addTask = findViewById(R.id.buttonAdd);
        refresh = findViewById(R.id.buttonRefresh);


        //getResponse();

        navBar = findViewById(R.id.bottomNav);
        navBar.setOnNavigationItemSelectedListener(bottomNavMethod);
        Menu menu = navBar  .getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        refresh.setOnClickListener(this);
        addTask.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(secondActivity.this);
        if(acct != null){
            email = acct.getEmail();
            ID = acct.getId();
        }
        
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAdd:
                /*
                ###### This is commented out because it does not compile ######
                */
//                Intent addTask = new Intent(this, AddTask.class);
//                startActivity(addTask);
                break;
            case R.id.buttonRefresh:
                getResponse();
                break;
        }
    }


    private void getResponse() {
        reqQueue = Volley.newRequestQueue(this);

        jsonReq = new JsonArrayRequest(Request.Method.GET,
                LOCAL_URL + "/alltasks", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(getApplicationContext(),
                        "Response :" + response.toString(), Toast.LENGTH_LONG).show();
                try {
                    task = "";
                    taskText.setText("");
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject task = response.getJSONObject(i);
                        taskText.append("ID: " + task.getString("id") + "\n" +
                                "User: " + task.getString("user") + "\n" +
                                "Task: " + task.getString("task") + "\n\n");
                    }
                } catch (JSONException e) {
                    Log.i(TAG,"Error :" + e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG,"Error :" + error.toString());
                error.printStackTrace();
            }
        });

        task = taskText.toString();
        tasks.add(task);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, tasks);
        reqQueue.add(jsonReq);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod=new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                    switch (item.getItemId()){

                        case R.id.home:
                            Intent intent = new Intent(secondActivity.this, secondActivity.class);
                            startActivity(intent);
                            break;

                        case R.id.friends:
                            Intent intent2 = new Intent(secondActivity.this, friends.class);
                            startActivity(intent2);
                            break;

                        case R.id.settings:
                            Intent intent3 = new Intent(secondActivity.this, settings.class);
                            startActivity(intent3);
                            break;


                    }

                    return true;
                }
            };



}
