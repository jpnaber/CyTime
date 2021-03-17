package com.cs309.loginscreen.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cs309.loginscreen.OtherView.Notification_receiver;
import com.cs309.loginscreen.Presenter.AddTaskPresenter;
import com.cs309.loginscreen.R;

import java.util.Calendar;

/**
 * The screen where user adds a task to server. It handles the user name input,
 * password input, due date input, and due time input. It also sets a reminder
 * on the device when due date and due time is set and the task is added to the
 * server.
 *
 * @author Bofu
 * @author Josh
 */
public class AddTask extends AppCompatActivity
        implements View.OnClickListener, IAddTask, DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    Button addBtn;          // Add button
    EditText userText;      // User name input
    EditText taskText;      // Task name input
    Button backToAllBtn;    // Back to all task screen button
    Button addDate;         // add date button
    Button addTime;         // add time button
    AddTaskPresenter presenter;     // Presenter to handle logic
    TextView displayTime;   // time display
    TextView displayDate;   // date display

    String date;    // date set
    String time;    // time set

    public int taskDay;     // parsed day
    public int taskMonth;   // parsed month
    public int taskYear;    // parsed year

    public int taskHour;    // parsed hour
    public int taskMinutes; // parsed minutes

    /**
     * Constructs a Add Task View UI controller
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        getSupportActionBar().hide();

        // Register all UI components
        addBtn = findViewById(R.id.addBtn);
        userText = findViewById(R.id.userNameEditTxt);
        taskText = findViewById(R.id.taskNameEditTxt);
        backToAllBtn = findViewById(R.id.backToAllBtn);
        addDate = findViewById(R.id.setDateButton);
        addTime = findViewById(R.id.setTimeButton);
        displayTime = findViewById(R.id.displayTimeTextView);
        displayDate = findViewById(R.id.displayDateTextView);

        // Sets all buttons with on click listeners
        addBtn.setOnClickListener(this);
        backToAllBtn.setOnClickListener(this);
        addDate.setOnClickListener(this);
        addTime.setOnClickListener(this);

        // Construct a new presenter to handle logic
        presenter = new AddTaskPresenter(this, getApplicationContext());
    }

    /**
     * Sets the buttons on screen. It has add, and back. Each has its literal function.
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setTimeButton:
                showTimePickerDialog();
                break;

            case R.id.setDateButton:
                showDatePickerDialog();
                break;

            case R.id.backToAllBtn:
                Intent backToAll = new Intent(this, AllTasks.class);
                startActivity(backToAll);
                break;

            case R.id.addBtn:
                String userName = userText.getText().toString();
                String taskName = taskText.getText().toString();

                // Sets notifications
                Intent notifyIntent = new Intent(AddTask.this,
                        Notification_receiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddTask.this,
                        0, notifyIntent, 0);

                // Parse and sets date and time
                long today = System.currentTimeMillis();
                Calendar myAlarmDate = Calendar.getInstance();
                //get time of most recent task, create an alarmdate
                // for it and set into alarmmanager
                myAlarmDate.setTimeInMillis(System.currentTimeMillis());
                myAlarmDate.set(Calendar.YEAR, taskYear);
                myAlarmDate.set(Calendar.MONTH, taskMonth-1);
                myAlarmDate.set(Calendar.DAY_OF_MONTH, taskDay);
                myAlarmDate.set(Calendar.HOUR_OF_DAY, taskHour);
                myAlarmDate.set(Calendar.MINUTE, taskMinutes);
                myAlarmDate.set(Calendar.SECOND, 0);
                myAlarmDate.set(Calendar.MILLISECOND, 0);

                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,myAlarmDate.getTimeInMillis(),
                        pendingIntent);

                // Sends all the info to server request to handle
                presenter.addTaskToServer(userName, taskName, date, time);

                // Make a toast when every thing is set
                Toast.makeText(this, "Notification set for: "+ taskDay +"/"+
                        (taskMonth) +"/"+ taskYear, Toast.LENGTH_SHORT).show();

                // Returns to all task view
                Intent back = new Intent(this, AllTasks.class);
                startActivity(back);
                break;
        }
    }

    /**
     * Handles success or error messages, should the presenter class want to call
     * @param s - error or success messages
     */
    @Override
    public void makeToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    /**
     * Handles longer messages, and display them in a text box,
     * should the presenter class want to call
     * @param s - messages presenter class want to call
     */
    @Override
    public void makeText(String s) {
    }

    /**
     * Shows the date picker dialog
     */
    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * Shows the tiem picker dialog
     */
    private void showTimePickerDialog(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.HOUR),
                Calendar.getInstance().get(Calendar.MINUTE),
                android.text.format.DateFormat.is24HourFormat(this)
        );
        timePickerDialog.show();
    }

    /**
     * Sets the date picker
     * @param datePicker - the date picker to set
     * @param year - year of input
     * @param month - month of input
     * @param day - day of input
     */
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        taskDay = day;
        taskMonth = month + 1;
        taskYear = year;
        date = taskMonth + "/" + day + "/" + year; //MM/DD/YYYY
        displayDate.setText(date);
    }

    /**
     * Sets the time picker
     * @param timePicker - time picker to set
     * @param hour - hour of input
     * @param minute - minute of input
     */
    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        taskHour = hour;
        taskMinutes = minute;
        time = hour + ":" + minute;
        displayTime.setText(time);
    }
}
