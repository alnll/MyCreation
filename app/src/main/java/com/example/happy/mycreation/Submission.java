package com.example.happy.mycreation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Submission extends AppCompatActivity {

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);

        iv = (ImageView) findViewById(R.id.productPhoto);

        Intent i = getIntent();
        iv.setImageBitmap((Bitmap) i.getParcelableExtra("img"));

    }
}
