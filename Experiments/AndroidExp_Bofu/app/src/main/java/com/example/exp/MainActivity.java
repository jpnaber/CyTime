package com.example.exp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView t1;
    Button b1;
    EditText e1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = (TextView)findViewById(R.id.t1);
        b1 = (Button)findViewById(R.id.b1);
        b1.setOnClickListener(this);
        e1 = (EditText)findViewById(R.id.input1);
    }

    @Override
    public void onClick(View v) {
        Toast toast = Toast.makeText(this, "Showing Your goal!",
                Toast.LENGTH_SHORT);
        toast.show();
        Intent i1 = new Intent(this,Second.class);
        i1.putExtra("goal", e1.getText().toString());
        startActivity(i1);
    }
}
