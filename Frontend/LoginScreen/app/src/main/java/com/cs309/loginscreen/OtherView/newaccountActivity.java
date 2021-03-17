package com.cs309.loginscreen.OtherView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cs309.loginscreen.MainActivity;
import com.cs309.loginscreen.R;

/**
 * @author Josh
 */
public class newaccountActivity extends AppCompatActivity {

    private TextView title;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private Button button;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newaccount);

        title = (TextView)findViewById(R.id.tVTitle);
        email = (EditText)findViewById(R.id.eTEmail);
        password = (EditText)findViewById(R.id.eTPassword);
        confirmPassword = (EditText)findViewById(R.id.eTPasswordTwo);
        button = (Button)findViewById(R.id.button);
        back = (Button)findViewById(R.id.buttonBack);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check(password.getText().toString(), confirmPassword.getText().toString());
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(newaccountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void check(String password, String confirmPassword){

        if(password.equals(confirmPassword)){
            Intent intent = new Intent(newaccountActivity.this, MainActivity.class);
            startActivity(intent);
        }else{
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

            dlgAlert.setMessage("Passwords Do Not Match");
            dlgAlert.setTitle("Error");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }


    }


}