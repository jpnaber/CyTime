package com.cs309.loginscreen.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cs309.loginscreen.Model.IVerificationListener;
import com.cs309.loginscreen.Presenter.VerificationPresenter;
import com.cs309.loginscreen.R;

/**
 * Verification View class that handles the verification and login actions.
 * It implements the IVerificationListener to listen to server responses.
 * It records the calling class, and upon verification it will take the user
 * back to the calling class, and send user info to that calling class.
 * New user can also sign up here. Upon sign up the new user will be taken back
 * to the calling class as well.
 *
 * @author Bofu
 */
public class Verification extends AppCompatActivity implements View.OnClickListener,
        IVerificationListener {

    Button verify;      // Verify button
    String caller;      // Stores caller info
    String purpose;     // Stores purpose of calling class
    VerificationPresenter presenter;    // Presenter to handle logic

    EditText userNameInput;     // User name input field
    EditText passwordInput;     // Password input field
    TextView verificationResponse;  // Verification response text
    Button vBackBtn;            // Back to Home button
    Button vSignUpBtn;          // Sign up button

    String userName;            // Stores user name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        getSupportActionBar().hide();

        // Register UI components and sets on click listeners
        userNameInput = findViewById(R.id.vUserName);
        passwordInput = findViewById(R.id.vPassword);
        verificationResponse = findViewById(R.id.vResponse);
        vBackBtn = findViewById(R.id.vBack);
        vBackBtn.setOnClickListener(this);
        vSignUpBtn = findViewById(R.id.vSignUp);
        vSignUpBtn.setOnClickListener(this);
        verify = (Button)findViewById(R.id.vBtn);
        verify.setOnClickListener(this);

        // Get the calling method and calling purposes
        Bundle extras = getIntent().getExtras();
        caller = extras.getString("Caller");
        purpose = extras.getString("Purpose");
        if (purpose == null) purpose = "";
        presenter = new VerificationPresenter(getApplicationContext(), this);
    }

    @Override
    public void onClick(View view) {
        userName = userNameInput.getText().toString();
        String password = passwordInput.getText().toString();
        switch (view.getId()) {
            case R.id.vBtn:
                presenter.verifyUser(userName, password);
                break;

            case R.id.vBack:
                Intent goBack = new Intent(this, AllTasks.class);
                goBack.putExtra("Caller", "Verification");
                startActivity(goBack);
                break;

            case R.id.vSignUp:
                presenter.signUp(userName, password);
                Intent newUser;
                if (purpose != null && purpose.equals("toFilter")) {
                    newUser = new Intent(this, AllTasks.class);
                } else {
                    newUser = new Intent(this, Chat.class);
                }
                newUser.putExtra("userName", userName);
                newUser.putExtra("Status", "NewUser");
                newUser.putExtra("Caller", "Verification");
                startActivity(newUser);
                break;
        }
    }

    /**
     * Upon successful verification, it retrieves the calling class and user name
     * info, and takes user back to the calling class that called for verification.
     * @param response - Success message
     */
    @Override
    public void onSuccess(String response) {
        if (purpose != null && purpose.equals("toFilter")) {
            Intent filtered = new Intent(this, AllTasks.class);
            filtered.putExtra("Caller", "Verification");
            filtered.putExtra("Status", "Verified");
            filtered.putExtra("userName", userName);
            startActivity(filtered);
        } else {
            Intent i = new Intent(this, Chat.class);
            i.putExtra("userName", userName);
            i.putExtra("Caller", "Verification");
            startActivity(i);
        }
    }

    /**
     * If unable to verify, then it displays the error message on the screen
     * @param response - failure message
     */
    @Override
    public void onFailure(String response) {
        verificationResponse.setText(response);
    }
}