package com.example.exp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Second extends AppCompatActivity implements View.OnClickListener {

    TextView t3;
    Button b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        b3 = (Button) findViewById(R.id.b3);
        b3.setOnClickListener(this);
        t3 = (TextView) findViewById(R.id.t3);
        String goalString = getIntent().getExtras().getString("goal");
        t3.setText(goalString);
    }

    @Override
    public void onClick(View v) {
        Toast toast = Toast.makeText(this, "You are going back to main!",
                Toast.LENGTH_SHORT);
        toast.show();
        Intent i2 = new Intent(this, MainActivity.class);
        startActivity(i2);
    }
}