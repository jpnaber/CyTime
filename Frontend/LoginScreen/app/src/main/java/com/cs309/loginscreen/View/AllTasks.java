package com.cs309.loginscreen.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cs309.loginscreen.Presenter.AddUserPresenter;
import com.cs309.loginscreen.Presenter.AllTaskPresenter;
import com.cs309.loginscreen.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

/**
 * The screen showing all tasks. It has five buttons, users can refresh the page
 * and sync with the server, can add tasks, go back to home screen, login to be able
 * to manage personal tasks, and logout to enter view-only mode for all tasks.
 * This class utilizes a presenter to handle all the logics.
 *
 * @author Bofu
 * @author Josh
 */
public class AllTasks extends AppCompatActivity
        implements View.OnClickListener, IAllTasksView, IAddUser {

    TextView userNameText;  // User name display
    TextView taskText;      // Task text display
    Button refreshButton;   // Refresh button
    Button addTaskButton;   // Add task button
    Button addHomeButton;   // Go back home button
    Button allTaskLogin;    // Log in button
    Button allTaskLogout;   // Log out button
    RecyclerView allTasksRecyclerView;  // Recycler view for task cards
    AddUserPresenter presenterLogin;    // Add user logic handler

    String userName;        // User name stores here

    AllTaskPresenter presenter; // Presenter to handle the view class

    private static String email;    // Stores email
    private String ID;              // Stores id
    public static GoogleSignInClient mGoogleSignInClient;   // Stores google login info

    /**
     * View UI controller for the All Tasks screen. This one has 2 buttons
     * and a big recycler view to display all tasks.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        // Register all UI components
        setContentView(R.layout.activity_all_tasks);
        userNameText = findViewById(R.id.allTaskUserNameText);
        allTaskLogin = findViewById(R.id.allTaskLogin);
        allTaskLogout = findViewById(R.id.allTaskLogout);
        refreshButton = (Button)findViewById(R.id.refreshButton);
        addTaskButton = (Button)findViewById(R.id.addTaskButton);
        addHomeButton = (Button)findViewById(R.id.addHome);
        allTasksRecyclerView = (RecyclerView)findViewById(R.id.allTasksReView);

        // If logged in, then go into logged in mode that has more privilege
        userName = filterUser(getIntent().getExtras());

        // Build presenters to handle logics
        presenter = new AllTaskPresenter(this, getApplicationContext(), userName);
        presenterLogin = new AddUserPresenter(this, getApplicationContext());

        // Sets the recycler view
        allTasksRecyclerView.setAdapter(presenter);
        allTasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Handles Google login components
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(AllTasks.this);
        if(acct != null) {
            email = acct.getEmail();
            ID = acct.getId();
            presenterLogin.addLoginToServer(ID, email);
        }

        // Registers on click listeners
        allTaskLogin.setOnClickListener(this);
        allTaskLogout.setOnClickListener(this);
        refreshButton.setOnClickListener(this);
        addTaskButton.setOnClickListener(this);
        addHomeButton.setOnClickListener(this);
    }

    /**
     * Filters each task with user name input. If logged in then the user name gets
     * extracted, and the task with correct user name will be displayed.
     * @param extra
     * @return
     */
    private String filterUser(Bundle extra) {
        if (extra == null) return "";
        String caller = extra.getString("Caller");
        String status = extra.getString("Status");
        if (status == null) status = "";
        if (caller == null) userName = "";
        if (caller.equals("Verification") &&
                (status.equals("Verified")) || (status.equals("NewUser"))) {
            userName = extra.getString("userName");
            String userInfoText = "Hi, " + userName;
            userNameText.setText(userInfoText);
        } else {
            userName = "";
            userNameText.setText("General User");
        }
        return userName;
    }

    /**
     * Sets buttons on the screen. It has a add task button, and a refresh
     * button to update all tasks from database.
     * @param view - this View object, UI controller
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addTaskButton:
                Intent addTask = new Intent(this, AddTask.class);
                startActivity(addTask);
                break;
            case R.id.refreshButton:
                presenter = new AllTaskPresenter(this, getApplicationContext(), userName);
                presenter.refreshTasks();
                break;

            case R.id.addHome:
                Intent goHome = new Intent(this, Home.class);
                goHome.putExtra("Caller", "AllTasks");
                startActivity(goHome);
                break;

            case R.id.allTaskLogin:
                Intent toFilter = new Intent(this, Verification.class);
                toFilter.putExtra("Caller", "AllTasks");
                toFilter.putExtra("Purpose", "toFilter");
                startActivity(toFilter);
                break;

            case R.id.allTaskLogout:
                Intent goToMyself = new Intent(this, AllTasks.class);
                goToMyself.putExtra("Caller", "AllTasks");
                startActivity(goToMyself);
                break;
        }
    }

    /**
     * Call this if you want to display success or error messages, in a toast.
     * @param s - success or error messages
     */
    public void makeToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    /**
     * Call this if you want to display success or error messages, in a medium
     * sized text box.
     * @param s - success or error messages
     */
    public void makeText(String s) {
        taskText.setText(s);
    }
}