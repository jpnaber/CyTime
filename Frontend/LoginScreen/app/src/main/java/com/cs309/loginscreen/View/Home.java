package com.cs309.loginscreen.View;

import androidx.appcompat.app.AppCompatActivity;
import com.cs309.loginscreen.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Home screen of the app. It displays the current date, user mode info, and
 * has two buttons: to see all tasks, or to chat.
 *
 * @author Bofu
 */
public class Home extends AppCompatActivity implements View.OnClickListener {

    TextView date;          // Date text
    TextView day;           // day of week text
    TextView userInfoText;  // User mode info text
    Button allTaskBtn;      // Button leads to all tasks screen
    Button chatBtn;         // Buttons leads to chat screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        // Register UI components
        date = (TextView)findViewById(R.id.homeDate);
        day = (TextView)findViewById(R.id.homeDay);
        userInfoText = findViewById(R.id.homeUserInfoText);
        allTaskBtn = (Button)findViewById(R.id.homeTask);
        chatBtn = (Button)findViewById(R.id.homeChat);

        // Set user info from extras passed in
        setUserInfo(getIntent().getExtras());

        // Set the date and day of week
        Date currentDate = Calendar.getInstance().getTime();
        String dateString = DateFormat.getDateInstance(DateFormat.FULL).format(currentDate);
        String[] splitDate = dateString.split(",");
        date.setText(splitDate[1] + ", " + splitDate[2]);
        day.setText(splitDate[0]);

        // Register on click listeners
        allTaskBtn.setOnClickListener(this);
        chatBtn.setOnClickListener(this);
    }

    /**
     * Sets user info text that passed in. Originally this differentiates various mode,
     * but now we have decided that this screen is entered only though multi-user mode.
     * Therefore there is only one condition and one text.
     * @param extra - extra in bundle from the calling class
     */
    public void setUserInfo(Bundle extra) {
        String caller = extra.getString("Caller");
        if (caller.equals("MainActivity") || caller.equals("AllTasks")) {
            String userInfoString = "You are in Multi-User Mode. You can view" +
                    " everyone's tasks, but you can only edit your own task. " +
                    "Also, you still need to login before using Chat.";
            userInfoText.setText(userInfoString);
        } else {
            userInfoText.setText("");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.homeTask:
                Intent toAllTask = new Intent(this, AllTasks.class);
                toAllTask.putExtra("Caller", "Home");
                startActivity(toAllTask);
                break;

            case R.id.homeChat:
                Intent toChat = new Intent(this, Chat.class);
                toChat.putExtra("Caller", "Home");
                toChat.putExtra("Purpose", "toChat");
                startActivity(toChat);
                break;
        }
    }
}