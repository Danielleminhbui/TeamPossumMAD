package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class setup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        Button buttonEvent = findViewById(R.id.buttonEvents);
        buttonEvent.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v){
                Intent activity2Intent = new Intent(getApplicationContext(),show_events.class);
                startActivity(activity2Intent);
            }
        });
    }
}