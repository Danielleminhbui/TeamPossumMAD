package com.example.mad.ui.viewEvent;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mad.R;
import com.example.mad.add_event;
import com.example.mad.homepage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class show_events extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_events);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        openFragment(new scheduled_eventsFragment());

        //This file is actually the bottom navbar and it holds the fragment of each (e.g. Scheduled, Past)
        //I'm just too scared to rename the file!!! (just in case)
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.navigation_upcoming:
                        openFragment(new scheduled_eventsFragment());
                        return true;

                    case R.id.navigation_past:
                        openFragment(new past_eventsFragment());
                        return true;

                    case R.id.navigation_return:
                        Intent returnBtn = new Intent(getApplicationContext(), homepage.class);
                        startActivity(returnBtn);


                }


                return false;
            }
        });
    }

    void openFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
