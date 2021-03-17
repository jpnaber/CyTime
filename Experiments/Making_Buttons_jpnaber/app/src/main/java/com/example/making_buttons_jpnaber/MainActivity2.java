package com.example.making_buttons_jpnaber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity2 extends AppCompatActivity {
    Button secondButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        secondButton = (Button) findViewById(R.id.button);

        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intentLoadNewActivity = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });
    }
}