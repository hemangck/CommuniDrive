package com.example.communidrive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class home_page_2 extends AppCompatActivity {

    String start, end,name;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page2);


        start = getIntent().getStringExtra("S");
        end = getIntent().getStringExtra("D");

        name = getIntent().getStringExtra("NAME");

        // Reference to the Firebase Realtime Database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("chat").child("details");

//        handleButtonClick("traffic");

        // Example button click listeners, you can add others as needed
        findViewById(R.id.trafficBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                handleButtonClick("traffic");
                Intent i1 = new Intent(home_page_2.this,traffic_chat.class);
                i1.putExtra("NAME",name);
                startActivity(i1);
            }
        });

        findViewById(R.id.punctureBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              handleButtonClick("puncture");
                Intent i2 = new Intent(home_page_2.this,puncture_chat.class);
                i2.putExtra("NAME",name);
                startActivity(i2);
            }
        });

        findViewById(R.id.accidentBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                handleButtonClick("accident");
                Intent i3 = new Intent(home_page_2.this,accident_chat.class);
                i3.putExtra("NAME",name);
                startActivity(i3);
            }
        });

        findViewById(R.id.womenSafetyBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                handleButtonClick("women-safety");
                Intent i4 = new Intent(home_page_2.this,women_safety_chat.class);
                i4.putExtra("NAME",name);
                startActivity(i4);
            }
        });
    }

//    private void handleButtonClick(String field) {
//        Query query = mDatabase.orderByChild("start").equalTo(start);
//
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        chatDataModel chatDataModel = snapshot.getValue(chatDataModel.class);
//
//                        // Assuming you have a field named "messages" in ChatDataModel
//                        String messages = chatDataModel.getMessages();
//
//                        // Check if the messages field matches your condition
//                        if (field.equals(messages)) {
//                            String name = chatDataModel.getName();
//                            String start = chatDataModel.getStart();
//                            String end = chatDataModel.getEnd();
//                            String members = chatDataModel.getMembers();
//                            nameCommu.setText(name);
//                            startCommu.setText(start);
//                            endCommu.setText(end);
//                            noOfmembers.setText(members);
//                            return; // Exit the loop since you found a matching record
//                        }
//                    }
//                    // If you reached here, no matching record was found
//                    Toast.makeText(home_page_2.this, "Not found in the database!", Toast.LENGTH_SHORT).show();
//                } else {
//                    // User details not found
//                    Toast.makeText(home_page_2.this, "Not found in the database!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(home_page_2.this, "Error!", Toast.LENGTH_SHORT).show();
//            }
//        });


//        @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(home_page_2.this, "Error !", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
