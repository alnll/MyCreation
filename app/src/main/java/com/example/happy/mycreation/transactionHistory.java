package com.example.happy.mycreation;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.List;




/* This class is for the sellers' "uploaded items" button. To list out all of the items that they have uploaded
 */

public class transactionHistory extends AppCompatActivity {
    public static ArrayList<Item> itemList;
    private RecyclerView recyclerView;
    private Adapter mAdapter;
    private static final String TAG = "TransactHis";
    DatabaseReference ref;

    protected void onCreate(Bundle savedInstanceState) {
        itemList = new ArrayList<>();
        final String uid =  FirebaseAuth.getInstance().getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Products").child(uid);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_activity);

        mAdapter = new Adapter(itemList);

        recyclerView = (RecyclerView) findViewById(R.id.rvTransact);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        // Retrieving the information from the database (Product Table)

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                itemList.clear();
                for(DataSnapshot pSS :dataSnapshot.getChildren()) {
                    // Get Post object and use the values to update the UI
                    Item item = new Item(pSS.child("title").getValue().toString(),pSS.child("price").getValue().toString(),pSS.child("quantity").getValue().toString(),pSS.child("prodURI").getValue().toString());

                    itemList.add(item);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(transactionHistory.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // For user to go back to the homepage
        Button backButton = (Button) findViewById(R.id.TBButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(transactionHistory.this, MainActivity.class));
            }
        });
    }


}

