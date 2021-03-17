package com.example.making_buttons_jpnaber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    ImageButton mainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainButton = (ImageButton) findViewById(R.id.imageButton2);

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intentLoadNewActivity = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intentLoadNewActivity);
            }
        });


    }
}