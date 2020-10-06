package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mad.data.LoginRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class start extends AppCompatActivity {


    private LinearLayout layout;
    private FirebaseAuth mAuth;
    private Button signUp;
    private Button Login;
    private TextInputLayout emailInput;
    private TextInputLayout passwordInput;
    private LoginRepository AuthUI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        //Initializing inputs
        emailInput = (TextInputLayout) findViewById(R.id.emailInput);
        passwordInput = (TextInputLayout) findViewById(R.id.passwordInput);
        signUp = (Button) findViewById(R.id.signUpBtn);
        Login = (Button) findViewById(R.id.signInButton);
        layout = (LinearLayout) findViewById(R.id.layout);

        mAuth = FirebaseAuth.getInstance();

        //Hides keyboard when user clicks on screen.
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if(getCurrentFocus() != null) {
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

            }
        });
        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signup();
            }
        });
        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                login();
            }
        });
    }


    //Checks if user is already signed in when app starts
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser); //need to do this
    }


    public void updateUI(FirebaseUser user)
    {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(start.this, homepage.class);
            startActivity(intent);
        }
    }


    public void login() {
        String email = emailInput.getEditText().getText().toString();
        String password = passwordInput.getEditText().getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG);
                            Intent intent = new Intent(start.this, homepage.class);
                            startActivity(intent);


                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Login failed! Please try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    //Transfers to setup page
    public void signup(){
        Intent intent = new Intent(start.this, setup.class);
        startActivity(intent);

    }


}