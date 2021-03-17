package com.cs309.loginscreen.Presenter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.cs309.loginscreen.Model.IServerListener;
import com.cs309.loginscreen.Model.ServerRequest;
import com.cs309.loginscreen.Model.Task;
import com.cs309.loginscreen.Model.UserInfo;
import com.cs309.loginscreen.R;
import com.cs309.loginscreen.View.IAllTasksView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Handles data, logic, and sets view for AllTask screen.
 * It doubles as the AllTaskAdapter that manages and inflates
 * the AllTask Recycler view. It also handles server request to
 * get All Tasks from the server using ServerRequest model.
 * It implements IServerListener to listen to the server response.
 * It parses the JSON Array response from the server and inflate the
 * AllTask card recycler view.
 *
 * ### Additional Features ###
 * 1. If NOT logged in, then the [DONE] button will say [NOT YOURS], meaning that you
 *      cannot delete this task. You can only view.
 * 2. If logged in, then you will be able to hit [DONE] and delete the task that
 *      list YOU as the user.
 * 3. If logged in, your task will say "*** YOUR TASK ***" at the bottom of that task.
 *
 * @author Bofu
 */
public class AllTaskPresenter extends RecyclerView.Adapter<AllTaskPresenter.myHolder>
        implements IServerListener {

    IAllTasksView view;     // AllTaskView taken as input, the view that this class manages
    Context context;        // Context of the AllTaskView object
    ServerRequest model;    // ServerRequest model it uses to handle server requests
    String userName;        // Stores the User Name info

    UserInfo user;          // Stores additional User Info

    private ArrayList<String> taskList;     // Task list for the recycler view

    static final String TAG = AllTaskPresenter.class.getName(); // Debug TAG

    /**
     * Constructs a AllTaskPresenter
     * @param view - View object, IAllTaksView UI
     * @param c - Application Context of that view object
     */
    public AllTaskPresenter(IAllTasksView view, Context c, String userName) {
        this.view = view;
        this.context = c;
        model = new ServerRequest(c, this);
        taskList = new ArrayList<>();
        user = new UserInfo();
        this.userName = userName;
        refreshTasks();
    }

    /**
     * Uses ServerRequest to pull tasks list from server. This method handles
     * url extensions. Then it starts listening to responses.
     * ### NOTE ###
     * This returns all tasks in the database
     */
    public void refreshTasks() {
        taskList.clear();
        model.getResponseArr(Request.Method.GET, "/listAllTask");
    }

    /**
     * Deletes the task by content. It takes task content as input, iterate through
     * the task array list, find the one that matches with the task content exactly,
     * delete it from the list. Then, it parses the text, extracts the ID, and
     * sends server request to delete the task by ID.
     * @param taskContent - Content of the task list
     */
    public void deleteTaskByContent(String taskContent) {
        String taskId = getIdFromTaskContent(taskContent);
        String[] taskContentSplit = taskContent.split("\n\n");
        String shortTaskContent = taskContentSplit[0];
        taskList.remove(shortTaskContent);
        String prompt = "/del?" + "id=" + taskId;
        model.stringRequest(prompt, Request.Method.POST);
        notifyDataSetChanged();
    }

    /**
     * On success, it is capable of receiving the success message.
     * @param s - string of success message
     */
    @Override
    public void onSuccess(String s) {
    }

    /**
     * On success, ServerRequest returns a response JSON array, and this
     * method parses the JSON array, and saves it into a task list.
     * @param response - returned JSON array
     */
    @Override
    public void onSuccess(JSONArray response) {
        view.makeToast("Synced with server!");
        // Parse the JSON array
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject task = response.getJSONObject(i);
                String result = "ID: " + task.getString("id") + "\n" +
                        "User: " + task.getString("userName") + "\n" +
                        "Task: " + task.getString("taskName") + "\n" +
                        "Due Date: " + task.getString("dueDate") + "\n" +
                        "Due Time: " + task.getString("dueTime");
                taskList.add(result);
            }
        } catch (JSONException e) {
            Log.i(TAG,"Error :" + e.toString());
            e.printStackTrace();
        }
        notifyDataSetChanged();
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
        view.makeToast(s);
    }

    public String getUserNameFromTaskContent(String taskContent) {
        String[] taskContentSplit = taskContent.split("\n");
        if (taskContentSplit.length > 1) {
            String taskUserName = taskContentSplit[1].substring(6).trim();
            return taskUserName;
        } else {
            return "";
        }
    }

    /**
     * Helper method to extract ID from the task text
     * @param taskContent - Content of the task
     * @return - Task ID
     */
    public String getIdFromTaskContent(String taskContent) {
        String[] taskContentSplit = taskContent.split("\n");
        if (taskContentSplit.length > 1) {
            String taskId = taskContentSplit[0].substring(4).trim();
            return taskId;
        } else {
            return "";
        }
    }

    /**
     * Presenter function. It constructs a view holder for the recycler view
     * @param parent - View UI
     * @param viewType
     * @return new View Holder
     */
    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View singleTaskView = LayoutInflater.from(context)
                .inflate(R.layout.task_card, parent, false);
        return new myHolder(singleTaskView);
    }

    /**
     * Updates the recycler view from list
     * @param holder - my view holder
     * @param position - position of view card to be updated
     */
    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        String taskContent = taskList.get(position);
        String taskUserName = getUserNameFromTaskContent(taskContent);
        if (userName != null && userName.toUpperCase().equals(taskUserName.toUpperCase())) {
            taskContent += "\n\n*** YOUR TASK ***";
            holder.doneBtn.setText("Done");
        } else {
            holder.doneBtn.setText("Not Yours");
        }
        holder.taskCardText.setText(taskContent);
    }

    /**
     * Getter for item count in recycler view
     * @return - int count for items in recycler view
     */
    @Override
    public int getItemCount() {
        return taskList.size();
    }

    /**
     * View holder to accommodate the done button and the task card
     */
    public class myHolder extends RecyclerView.ViewHolder {
        TextView taskCardText;
        Button doneBtn;
        public myHolder(@NonNull View itemView) {
            super(itemView);
            taskCardText = itemView.findViewById(R.id.taskCardText);
            doneBtn = itemView.findViewById(R.id.doneBtn);
            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String taskContent = taskCardText.getText().toString();
                    String taskName = getUserNameFromTaskContent(taskContent);
                    if (userName != null
                            && taskName.toUpperCase().equals(userName.toUpperCase())) {
                        deleteTaskByContent(taskContent);
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }
}
