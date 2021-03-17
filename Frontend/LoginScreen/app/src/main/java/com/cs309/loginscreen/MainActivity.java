package com.cs309.loginscreen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cs309.loginscreen.OtherView.forgotpasswordActivity;
import com.cs309.loginscreen.OtherView.newaccountActivity;
import com.cs309.loginscreen.OtherView.secondActivity;
import com.cs309.loginscreen.View.AllTasks;
import com.cs309.loginscreen.View.Home;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

/**
 * This is our CyTime app. It is a community based app that allows everyone to see everyone's
 * to do list items, to add, edit, or delete the user's own to do list items, and to chat
 * with registered user in the community.
 *
 * Upon opening of the app, the user can choose either
 * to enter as a logged in user though login function on the main screen, or user can choose
 * to enter in the multi-user mode. In multi-user mode, the user can see everyone's task, but
 * does not have edit or delete privileges. Upon logged in, the user can edit or delete the
 * task that belongs to the user, and to chat with other registered users in the chat room.
 *
 * The chat room broadcasts a message when anyone enters or exits the room. The chat room also
 * has a button that takes the user to My Friends page where the entire friends list (registered
 * users), along with their status are displayed. If a friend is currently active in the chat
 * room, then the [HI] button on the right side of that friend card will turn green. If the friend
 * is not online then the icon turns green and will display [N/A].
 *
 * @author Bofu
 * @author Josh
 * @author Team MC_10
 *
 * Developed by Team MC_10
 * COM S 309, Fall 2020
 */
public class MainActivity extends AppCompatActivity {

    private TextView titleLogin;        // Login title
    private EditText username;          // User name input
    private EditText password;          // Password input
    private Button loginButton;         // Login button
    private TextView forgotPassword;    // Forgot password link
    private TextView notAMember;        // Not a member link
    private Button guestButton;         // Multi-User Mode button
    private Button googleLoginButton;   // Google login button

    private int RC_SIGN_IN = 0;         // Google login field
    private GoogleSignInClient mGoogleSignInClient;     // Google login client

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Register resources
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        titleLogin = (TextView)findViewById(R.id.textView);
        loginButton = (Button)findViewById(R.id.loginButton);
        forgotPassword = (TextView)findViewById(R.id.forgotPassword);
        notAMember = (TextView)findViewById(R.id.notAMember);
        guestButton = (Button)findViewById(R.id.guestButton);
        googleLoginButton = (Button)findViewById(R.id.googleLoginButton);

        // Set up Google login buttons
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        // Set up notification systems
        createNotificationChannel();

        // Registers on click listeners
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(username.getText().toString(), password.getText().toString());
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, forgotpasswordActivity.class);
                startActivity(intent);
            }
        });
        notAMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, newaccountActivity.class);
                startActivity(intent);
            }
        });
        // This button bypasses login screen and goes to Home Screen in Multi-User Mode
        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userNameInput = username.getText().toString();
                Intent toHome = new Intent(MainActivity.this, Home.class);
                toHome.putExtra("Caller", "MainActivity");
                startActivity(toHome);
            }
        });
    }

    /**
     * Google sign in button handler to send info to sign activity
     */
    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * Google sign in system handler helper that handles validation results
     * @param requestCode - request code
     * @param resultCode - result code
     * @param data - data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    /**
     * Google sign in result handler to complete tasks
     * @param completedTask - result
     */
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            startActivity(new Intent(MainActivity.this, AllTasks.class));
        }
        catch(ApiException e){
            Log.w("Google Sign In Error", "signInResult:failed code-" + e.getStatusCode());
            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Google sign in components that handles onStart calls
     */
    protected void onStart(){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            startActivity(new Intent(MainActivity.this, AllTasks.class));
        }
        super.onStart();
    }

    /**
     * Google sign in components that validates the user name and password
     * @param user - user name
     * @param userPassword - password
     */
    private void validate(String user, String userPassword){

        if(user.equals("Admin") && userPassword.equals("1234")){
            Intent intent = new Intent(MainActivity.this, secondActivity.class);
            startActivity(intent);
        }else{
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

            dlgAlert.setMessage("Wrong Username or Password");
            dlgAlert.setTitle("Error");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
    }

    /**
     * Creates notification channel
     */
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "CyTime Reminder Channel";
            String description = "Channel for task reminders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyCyTime", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}