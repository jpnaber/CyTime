package com.cs309.loginscreen.OtherView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cs309.loginscreen.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.cs309.loginscreen.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * @author Josh
 */
public class settings extends AppCompatActivity {

    private BottomNavigationView navBar;
    private Button signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        signOutButton = findViewById(R.id.signOutButton);

        navBar = findViewById(R.id.bottomNav);
        navBar.setOnNavigationItemSelectedListener(bottomNavMethod);
        Menu menu = navBar  .getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }


    public void signOut(){
        secondActivity.mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(settings.this, "Successfully Signed Out", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(settings.this, MainActivity.class));
                        finish();
                    }
                });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod=new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                    switch (item.getItemId()){

                        case R.id.home:
                            Intent intent = new Intent(settings.this, secondActivity.class);
                            startActivity(intent);
                            break;

                        case R.id.friends:
                            Intent intent2 = new Intent(settings.this, friends.class);
                            startActivity(intent2);
                            break;

                        case R.id.settings:
                            Intent intent3 = new Intent(settings.this, settings.class);
                            startActivity(intent3);
                            break;


                    }

                    return true;
                }
            };
}