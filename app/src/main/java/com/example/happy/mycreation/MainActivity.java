package com.example.happy.mycreation;

import android.app.DownloadManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.content.pm.PackageInfo;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout llButton=(LinearLayout)findViewById(R.id.SellBackground);
        //Check the Android version
        int currentAndVer = android.os.Build.VERSION.SDK_INT;
        if(currentAndVer>= android.os.Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.CAMERA}, 1000);
            }
        }

        llButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch Camera
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Take a picture and pass results along to onActivityResult
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        });
        //Disable the llButton if the user does not have a camera
        if(!hasCamera()){
            llButton.setEnabled(false);
        }

    }

    //Check if the user has a camera
    private boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }


    //If you want to return the image taken
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
      if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK){
          //Get the photo
          Bundle extras = data.getExtras();
          Bitmap photo = (Bitmap) extras.get("data");
          try{
              Intent i = new Intent(MainActivity.this,Submission.class);
              i.putExtra("img",photo);
              startActivity(i);

          }catch (Exception e){
              System.out.println(e.getMessage());
          }

      }
    }
}
