package com.cs309.loginscreen.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cs309.loginscreen.Presenter.MyFriendsPresenter;
import com.cs309.loginscreen.R;

/**
 * My Friends View that displays all the user's friends. This is a community based
 * app, so if you are a registered user, you ar everyone's friends, and you will
 * be friends with every one. This view class uses a presenter to handle all the
 * logic, and it uses a recycler view to display each friend as a card.
 *
 * @author Bofu
 */
public class MyFriends extends AppCompatActivity implements View.OnClickListener {

    Button backBtn;     // Back to all task screen button
    String userName;    // User name stores here

    MyFriendsPresenter presenter;           // Presenter to handle all the logic
    RecyclerView myFriendsRecyclerView;     // Recycler view component

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);
        getSupportActionBar().hide();
        // Register all the UIs
        myFriendsRecyclerView = (RecyclerView)findViewById(R.id.friendsReView);
        backBtn = findViewById(R.id.friendsBack);

        // Builds presenter and recycler view handler
        presenter = new MyFriendsPresenter(this, getApplicationContext());
        myFriendsRecyclerView.setAdapter(presenter);
        myFriendsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sets on click listener
        backBtn.setOnClickListener(this);

        // Extracts the user name info from the calling class
        // This is a registered-only class, thus it is guaranteed to have user name info
        userName = getIntent().getExtras().getString("userName");
        if (userName == null) userName = "";
    }

    @Override
    public void onClick(View view) {
        Intent goBack = new Intent(this, Chat.class);
        goBack.putExtra("Caller", "MyFriends");
        goBack.putExtra("userName", userName);
        startActivity(goBack);
    }
}