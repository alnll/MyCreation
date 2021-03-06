package com.example.happy.mycreation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private EditText ETNameR, ETEmailRegis, ETPasswordRegis, ETPhoneNumber;
    private String ETName,ETEmailReg,ETPhone, ETPasswordReg,cusID;
    Button regButton, backButton;
    private static final String TAG = "CreateAccount";
    private DatabaseReference databaseReference;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerlayout);

        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        ETNameR = (EditText) findViewById(R.id.ETName);
        ETEmailRegis = (EditText) findViewById(R.id.ETEmailReg);
        ETPasswordRegis = (EditText) findViewById(R.id.ETPasswordReg);
        ETPhoneNumber = (EditText) findViewById(R.id.ETPhone);


        backButton=(Button) findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,logIn.class));
            }
        });
        regButton = (Button) findViewById(R.id.RegButton);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // To validate input when button is called
                initialise();
                createAccount(ETEmailReg, ETPasswordReg);
            }
        });

    }


    public void initialise(){
        ETName =ETNameR.getText().toString().trim();
        ETEmailReg =ETEmailRegis.getText().toString().trim();
        ETPasswordReg =ETPasswordRegis.getText().toString().trim();
        ETPhone =ETPhoneNumber.getText().toString().trim();

    }
    private void writeNewUser(String userId, String name, String email, String Phone) {
        CustomerUsers cusUser = new CustomerUsers(cusID,name, email, Phone);

        databaseReference.child("Customers").child(userId).setValue(cusUser);
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
                            Toast.makeText(RegisterActivity.this, "Registration Successful",
                                    Toast.LENGTH_SHORT).show();
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            cusID = user.getUid();
                            writeNewUser(cusID,ETName,ETEmailReg,ETPhone);
                            //TODO buyer layout
                            //            startActivity(new Intent(RegisterActivity.this, ));
                        }
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Registration Unsuccessful",
                                    Toast.LENGTH_SHORT).show();
                        }



                    }
                });

    }
    public boolean validate(){

        boolean valid = true;
        if(ETName.isEmpty()||ETName.length()>30){
            ETNameR.setError("Please enter a valid name.");
            valid = false;
        }

        if(ETEmailReg.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(ETEmailReg).matches()){
            ETEmailRegis.setError("Please enter a valid e-mail address.");
            valid = false;
        }
        if(ETPasswordReg.isEmpty()||ETPasswordReg.length()<6){
            ETPasswordRegis.setError("Please enter a valid password that is more than 6 characters.");
            valid = false;
        }
        if(ETPhone.isEmpty()|| ETPhone.length()<8 || ETPhone.length()>8){
            ETPhoneNumber.setError("Please enter phone number (8 digits).");
            valid = false;
        }
        return valid;
    }

}