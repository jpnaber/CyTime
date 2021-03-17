package com.cs309.loginscreen.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cs309.loginscreen.Presenter.ChatPresenter;
import com.cs309.loginscreen.R;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Chat view that uses web sockets to allow users to chat with other
 * registered users. This class uses a chat presenter to handle
 * server requests and other logics.
 *
 * @author Bofu
 */
public class Chat extends AppCompatActivity implements View.OnClickListener {

    String caller;      // Class name that called this object
    String userName;    // User name of user

    TextView chatText;      // Chat top text display
    EditText inputText;     // Chat input window
    TextView outputText;    // Chat output text

    Button logOutBtn;       // Logout button
    Button homeBtn;         // Leave button
    Button sendBtn;         // Send button
    Button myFriendsBtn;    // Button that leads to MyFriends page

    ChatPresenter presenter;    // Chat presenter

    WebSocketClient socket;     // Web sockets for connectivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();

        // Register UI components
        chatText = (TextView)findViewById(R.id.chatText);
        inputText = (EditText)findViewById(R.id.inText);
        outputText = (TextView)findViewById(R.id.outText);
        logOutBtn = (Button)findViewById(R.id.chatLogOut);
        homeBtn = (Button)findViewById(R.id.chatLeave);
        sendBtn = (Button)findViewById(R.id.chatSend);
        myFriendsBtn = (Button)findViewById(R.id.chatFriends);

        // Verify the caller: See if the user has logged in
        Bundle extra = getIntent().getExtras();
        caller = extra.getString("Caller");
        // MyFriends page is logged-in-only. So it's safe to pass
        if (caller == null || (!caller.equals("Verification") && !caller.equals("MyFriends"))) {
            Intent verify = new Intent(this, Verification.class);
            verify.putExtra("Caller", "Chat");
            startActivity(verify);
        } else {
            userName = extra.getString("userName");
            chatText.setText("Welcome, " + userName + "!");
        }
        outputText.setMovementMethod(new ScrollingMovementMethod());

        // Set on click listeners
        logOutBtn.setOnClickListener(this);
        homeBtn.setOnClickListener(this);
        sendBtn.setOnClickListener(this);
        myFriendsBtn.setOnClickListener(this);
        // Verify identity and starts web socket if passed
        verifyPermission(extra);
    }

    /**
     * Verifies the identity of the user and calling class.
     * If called from a logged-in only class then it will pass. Otherwise,
     * it needs to verify the user name info, and text extras to see if the
     * user name has previously passed verification
     * @param extra - extra info in a bundle
     */
    private void verifyPermission(Bundle extra) {
        caller = extra.getString("Caller");
        if (caller == null || (!caller.equals("Verification") && !caller.equals("MyFriends"))) {
            Intent verify = new Intent(this, Verification.class);
            verify.putExtra("Caller", "Chat");
            startActivity(verify);
        } else {
            userName = extra.getString("userName");
            chatText.setText("Welcome, " + userName + "!");
            presenter = new ChatPresenter(getApplicationContext(), userName);
            presenter.login(userName);
            connectSocket(userName);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chatLogOut:
                String info = presenter.logout(userName);
                Intent logOut = new Intent(this, Verification.class);
                logOut.putExtra("Caller", "Chat");
                logOut.putExtra("Purpose", "toVerification");
                socket.close();
                startActivity(logOut);
                break;

            case R.id.chatLeave:
                info = presenter.logout(userName);
                Intent goHome = new Intent(this, Home.class);
                goHome.putExtra("Caller", "Chat");
                goHome.putExtra("Purpose", "toHome");
                startActivity(goHome);
                break;

            case R.id.chatSend:
                String message = inputText.getText().toString();
                if(message != null && message.length() > 0){
                    socket.send(message);
                }
                break;

            case R.id.chatFriends:
                Intent goToFriends = new Intent(this, MyFriends.class);
                goToFriends.putExtra("Caller", "Chat");
                goToFriends.putExtra("userName", userName);
                startActivity(goToFriends);
                break;
        }
    }

    /**
     * Closes web socket connections to prevent resources leak
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.close();
    }

    /**
     * Connects the web sockets
     * @param userName - User name to display
     */
    public void connectSocket(String userName) {
        URI uri;
        try {
            /*
             * To test the clientside without the backend, simply connect to an echo server such as:
             *  "ws://echo.websocket.org"
             */
//            uri = new URI("ws://192.168.1.3:8080/web/" + userName); // 10.0.2.2 = localhost
            uri = new URI("ws://10.24.226.75:8080/web/" + userName); // 10.0.2.2 = localhost
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        socket = new WebSocketClient(uri) {

            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("WebSocket", "Opened");
            }

            @Override
            public void onMessage(String msg) {
                Log.i("WebSocket", "Message Received");
                // Appends the message received to the previous messages
                outputText.append("\n" + msg);
            }

            @Override
            public void onClose(int errorCode, String reason, boolean remote) {
                Log.i("WebSocket", "Closed " + reason);
            }

            @Override
            public void onError(Exception e) {
                Log.i("WebSocket", "Error " + e.getMessage());
            }
        };
        socket.connect();
    }
}