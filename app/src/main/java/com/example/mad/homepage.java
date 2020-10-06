package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mad.ui.viewEvent.show_events;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class homepage extends AppCompatActivity {
    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        getName();

        welcomeText = (TextView) findViewById(R.id.welcome_text);

        CardView addEventBtn = findViewById(R.id.addEventBtn);
        addEventBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent eventViewerIntent = new Intent(getApplicationContext(), add_event.class);
                startActivity(eventViewerIntent);
            }
        });

        CardView mapsBtn = findViewById(R.id.mapBtn);
        mapsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mapsIntent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(mapsIntent);
            }
        });


        CardView viewEventBtn = findViewById(R.id.viewEventBtn);
        viewEventBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mapsIntent = new Intent(getApplicationContext(), show_events.class);
                startActivity(mapsIntent);
            }
                });
        }

    private void getName(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("user", userid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    private static final String TAG = "";

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                welcomeText.setText("Welcome " + (CharSequence) document.get("Name"));
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Intent setup = new Intent(getApplicationContext(), setLocation.class);
                            startActivity(setup);
                            Toast.makeText(getApplicationContext(),"Please set up your details",Toast.LENGTH_LONG).show();
                        }
                    }
                });




    }

}



       /* TO BE COMPLETED :
            firebase.auth().onAuthStateChanged(function(user) {
  if (user) {
    // User is signed in.
  } else {
    // No user is signed in.
  }
});*/