package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;


public class setup extends AppCompatActivity {
    private static final String TAG = "Set up" ;
    private FirebaseAuth mAuth;

    private LinearLayout layout;
    private TextInputLayout emailInput;
    private TextInputLayout passwordInput;
    private Button registerBtn;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        emailInput = (TextInputLayout) findViewById(R.id.emailInput);
        passwordInput = (TextInputLayout) findViewById(R.id.passwordInput);
        registerBtn = (Button) findViewById(R.id.setupButton);
        layout = (LinearLayout) findViewById(R.id.layout);

        //Hides keyboard when user clicks on screen.
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (getCurrentFocus() != null) {
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }}});
        //Register user to firestore... eventually will allow user to login
        //Also transfers user to Homepage (Right now it's set up to EventList as homepage is not set up)
        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signup();
            }
        });
    }



    private void signup(){
        if(validateData()){
            String email = emailInput.getEditText().getText().toString();
            String password = passwordInput.getEditText().getText().toString();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(setup.this, setLocation.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                emailInput.setError(getResources().getString(R.string.error_email_format));
                                passwordInput.setError(getResources().getString(R.string.error_password_length));

                            }
                        }
                    });

        }
    }

    //Validating the username and password
    private boolean validateData(){
        boolean verified = false;
        //Validate legal username characters and length (5-20)
        if(!emailInput.getEditText().getText().toString().matches("^(.+)@(.+)$")){
            verified = false;
            emailInput.setError(getResources().getString(R.string.error_email_format));
        }
        //Validate if password is greater than 7
        if(!(passwordInput.getEditText().getText().toString().length() > 7)){
            verified = false;
            passwordInput.setError(getResources().getString(R.string.error_password_length));
        }

        else{
            verified = true;
        }

        return verified;
    }


}
