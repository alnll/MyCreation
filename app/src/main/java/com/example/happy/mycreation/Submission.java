package com.example.happy.mycreation;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.security.Timestamp;

import static java.lang.System.currentTimeMillis;

public class Submission extends AppCompatActivity {

    private ImageView iv;
    private StorageReference mStorage;
    private DatabaseReference databaseReference, dbURI;
    private String dbReference;
    private FirebaseUser uid;
    String timeS;
    private static final String TAG = "CreateProduct";
    private EditText ETitle, EPrice, EQuantity;
    private String sURI, sTitle, sQuantity, sPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        long timeStamp = System.currentTimeMillis() / 1000;
        timeS = new Long(timeStamp).toString();

        uid = FirebaseAuth.getInstance().getCurrentUser();
        dbReference = uid.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);

        //To receive the image

        iv = (ImageView) findViewById(R.id.productPhoto);
        Intent i = getIntent();
        iv.setImageBitmap((Bitmap) i.getParcelableExtra("img"));

        mStorage = FirebaseStorage.getInstance().getReference();

        ETitle = (EditText) findViewById(R.id.ETTitle);
        EPrice = (EditText) findViewById(R.id.ETPrice);
        EQuantity = (EditText) findViewById(R.id.ETQuantity);

        Button saveButton = (Button) findViewById(R.id.saveButton);
        //When the user saves the details
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validate()){
                    return;
                }
                if(validate()) {
                    Toast.makeText(Submission.this, "Uploading...", Toast.LENGTH_SHORT).show();

                    //Assign to folder by using the userID and the timestamp as the title of the photo
                    StorageReference productImagesRef = mStorage.child("/" + dbReference + "/" + "/" + timeS + "/");

                    // Upload from data in memory
                    iv.setDrawingCacheEnabled(true);
                    iv.buildDrawingCache();
                    Bitmap bitmap = iv.getDrawingCache();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask = productImagesRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Handle unsuccessful uploads
                            Toast.makeText(Submission.this, "Upload Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(Submission.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                            @SuppressWarnings("VisibleForTests")
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            sURI = downloadUrl.toString();
                            writeNewProduct(dbReference, sTitle, sPrice, sQuantity, sURI);
                            startActivity(new Intent(Submission.this, MainActivity.class));
                        }

                    });

                }
            }


        });

        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        //User press the cancel button and it will bring them back to their "main page"
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Submission.this, MainActivity.class));
            }
        });
    }

    public void initialise() {

        sTitle = ETitle.getText().toString().trim();
        sPrice = EPrice.getText().toString().trim();
        sQuantity = EQuantity.getText().toString().trim();

    }

    public boolean validate() {

        initialise();
        boolean valid = true;
        if (sTitle.isEmpty() || sTitle.length() > 30) {
            ETitle.setError("Please enter a valid name!");
            valid = false;
        }
        if (sPrice.isEmpty() || sPrice.length() > 6) {
            EPrice.setError("Please enter a valid price");
            valid = false;
        }
        if (sQuantity.isEmpty()) {
            EQuantity.setError("Please set the number of items!");
            valid = false;
        }


        return valid;
    }

    private void writeNewProduct(String userID, String Title, String Price, String Quantity, String URI) {

        ProductDetails prodDetails = new ProductDetails(dbReference, Title, Price, Quantity, URI);

        databaseReference.child("Products").child(timeS).setValue(prodDetails);
    }



}