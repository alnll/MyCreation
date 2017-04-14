package com.example.happy.mycreation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registerActivity extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private EditText ETNameR, ETEmailRegis, ETPasswordRegis, ETPhoneNumber;
    private String ETName,ETEmailReg,ETPhone, ETPasswordReg;
    Button regButton, backButton;
    private static final String TAG = "CreateAccount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerlayout);

        mAuth = FirebaseAuth.getInstance();

        ETNameR = (EditText) findViewById(R.id.ETName);
        ETEmailRegis = (EditText) findViewById(R.id.ETEmailReg);
        ETPasswordRegis = (EditText) findViewById(R.id.ETPasswordReg);
        ETPhoneNumber = (EditText) findViewById(R.id.ETPhone);

        backButton=(Button) findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registerActivity.this,logIn.class));
            }
        });
        regButton = (Button) findViewById(R.id.RegButton);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialise();
                createAccount(ETEmailReg, ETPasswordReg); // To validate input when button is called
            }
        });

    }


    public void initialise(){
        ETName =ETNameR.getText().toString().trim();
        ETEmailReg =ETEmailRegis.getText().toString().trim();
        ETPasswordReg =ETPasswordRegis.getText().toString().trim();
        ETPhone =ETPhoneNumber.getText().toString().trim();

    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validate()) {
            return;
        }

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if(task.isSuccessful()){
                            Toast.makeText(registerActivity.this, "Registration Successful",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if (!task.isSuccessful()) {
                            Toast.makeText(registerActivity.this, "Registration Unsuccessful",
                                    Toast.LENGTH_SHORT).show();
                        }



                    }
                });

    }
    public boolean validate(){
        boolean valid = true;
        if(ETName.isEmpty()||ETName.length()>30){
            ETNameR.setError("Please enter a valid name");
            valid = false;
        }

        if(ETEmailReg.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(ETEmailReg).matches()){
            ETEmailRegis.setError("Please enter a valid e-mail address");
            valid = false;
        }
        if(ETPasswordReg.isEmpty()||ETPasswordReg.length()<6){
            ETPasswordRegis.setError("Please enter a valid password that is more than 6 characters");
            valid = false;
        }
        if(ETPhone.isEmpty()){
            ETPhoneNumber.setError("Please enter the correct phone number");
            valid = false;
        }
        return valid;
    }

}