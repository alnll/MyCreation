package com.example.happy.mycreation;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class logIn extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private static  EditText ETEmail, ETPassword;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private String cusID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        ETEmail = (EditText) findViewById(R.id.ETEmail);
        ETPassword = (EditText) findViewById(R.id.ETPassword);
        TextView newUser = (TextView) findViewById(R.id.TVRegister);
        Button login = (Button) findViewById(R.id.logInButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ETEmail.getText().toString().contains("@")){
                    signIn(ETEmail.getText().toString(),ETPassword.getText().toString());

                }else{
                    Toast errorMessage= Toast.makeText(logIn.this, "Please enter the correct email address.", Toast.LENGTH_SHORT);
                    errorMessage.setGravity(Gravity.CENTER,0,0);
                    errorMessage.show();

                }
            }
        });

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(logIn.this,RegisterActivity.class));
                finish();

            }
        });

    }

    private void signIn(String email, String password){
        Log.d(TAG, "signIn"+email);
        if (!validateForm()){
            return;
        }
        // Start with signing in with email and password
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "User Logged In",Toast.LENGTH_SHORT).show();

                            user = FirebaseAuth.getInstance().getCurrentUser();
                            cusID = user.getUid();

                            databaseReference.child("Seller").child(cusID).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    if (snapshot.getValue() != null) {
                                        startActivity(new Intent(logIn.this, MainActivity.class));
                                        finish();
                                    } else {
                                        //user does not exist, go to Buyer's page
                                        Toast.makeText(getApplicationContext(), "Buyer", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError arg0) {
                                }
                            });
                        }

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(getApplicationContext(), "Login failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        // [END sign_in_with_email]
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = ETEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            ETEmail.setError("Required.");
            valid = false;
        } else {
            ETEmail.setError(null);
        }

        String password = ETPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            ETPassword.setError("Required.");
            valid = false;
        } else {
            ETPassword.setError(null);
        }

        return valid;
    }




}