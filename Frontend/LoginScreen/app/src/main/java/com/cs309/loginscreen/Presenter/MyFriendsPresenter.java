package com.cs309.loginscreen.Presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.cs309.loginscreen.Model.IServerListener;
import com.cs309.loginscreen.Model.ServerRequest;
import com.cs309.loginscreen.Model.UserInfo;
import com.cs309.loginscreen.R;
import com.cs309.loginscreen.View.Chat;
import com.cs309.loginscreen.View.IAllTasksView;
import com.cs309.loginscreen.View.MyFriends;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Logic handler and Recycler view adapter for the MyFriends class. It extends
 * the recycler view Adapter, and implements the IServerListener interface to
 * listen to server response. It retrieves the user Friends list, displays them
 * in the recycler card view, displays their status, and has a function to say
 * hi to them if they are online
 *
 * ### Main Features ###
 * 1. It displays the real time status of your friends. It can be "online", "offline",
 *      or any status your friend sets to.
 * 2. Status says online IF AND ONLY IF your friend is ACTIVE on the chat window or looking
 *      at the friends page.
 * 3. If online, then the [HI] button will turn into a bright green. It is otherwise
 *      dark grey.
 *
 * @author Bofu
 */
public class MyFriendsPresenter extends RecyclerView.Adapter<MyFriendsPresenter.myHolder>
        implements IServerListener {

    MyFriends view;         // View of MyFriends
    Context context;        // MyFriends view context
    ServerRequest model;    // ServerRequest model

    UserInfo user;      // Stores User Info
    String myName;      // Stores User Name

    private ArrayList<String> friendsList;      // Friends list for recycler view

    static final String TAG = MyFriendsPresenter.class.getName();   // TAG for Log

    /**
     * Constructs a MyFriendsPresenter
     * @param view - View object, IAllTaksView UI
     * @param c - Application Context of that view object
     */
    public MyFriendsPresenter(MyFriends view, Context c) {
        this.view = view;
        this.context = c;
        model = new ServerRequest(c, this);
        friendsList = new ArrayList<>();
        user = new UserInfo();
        refreshFriendsList();
    }

    /**
     * Refreshes the friends list by retrieving complete new friends list
     * from the server.
     */
    public void refreshFriendsList() {
        friendsList.clear();
        model.getResponseArr(Request.Method.GET, "/listAllUser");
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
        // Parse the JSON array
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject friend = response.getJSONObject(i);
                String result = friend.getString("userName") + " \n\n"
                        + "[" + friend.getString("uStatus") + "]";
                friendsList.add(result);
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
    }

    /**
     * Presenter function. It constructs a view holder for the recycler view
     * @param parent - View UI
     * @param viewType
     * @return new View Holder
     */
    @NonNull
    @Override
    public MyFriendsPresenter.myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View singleFriendView = LayoutInflater.from(context)
                .inflate(R.layout.friends_card, parent, false);
        return new myHolder(singleFriendView);
    }

    /**
     * Updates the recycler view from list
     * @param holder - my view holder
     * @param position - position of view card to be updated
     */
    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        holder.friendsCardText.setText(friendsList.get(position));
        String[] friendsTextSplit = friendsList.get(position).split("\n\n");
        String status = friendsTextSplit[1];
        if (!status.equals("[online]")) {
            holder.dmBtn.setBackgroundResource(R.drawable.round_button_dark_grey);
            holder.dmBtn.setText("N/A");
        }
    }

    /**
     * Getter for item count in recycler view
     * @return - int count for items in recycler view
     */
    @Override
    public int getItemCount() {
        return friendsList.size();
    }

    /**
     * View holder to accommodate the done button and the friends card
     */
    public class myHolder extends RecyclerView.ViewHolder {
        TextView friendsCardText;
        Button dmBtn;
        public myHolder(@NonNull View itemView) {
            super(itemView);
            friendsCardText = (TextView)itemView.findViewById(R.id.friendsCardText);
            dmBtn = (Button)itemView.findViewById(R.id.friendsDMBtn);
            dmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Call to start DM
                }
            });
        }
    }
}
