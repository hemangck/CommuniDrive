package com.example.communidrive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class home_page extends AppCompatActivity {

    EditText startPoint, destination;
    Button searchBtn;
    TextView homePageName;
    String email,nameUser;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        startPoint = findViewById(R.id.startPoint);
        destination = findViewById(R.id.destination);
        searchBtn = findViewById(R.id.searchBtn);
        homePageName = findViewById(R.id.homePageName);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String startPointText = startPoint.getText().toString();
                String destinationText = destination.getText().toString();

                Intent intent = new Intent(home_page.this, home_page_2.class);
                intent.putExtra("S", startPointText);
                intent.putExtra("D", destinationText);
                intent.putExtra("NAME",nameUser);
                startActivity(intent);
            }
        });

        // Remove the "DatabaseReference" before mDatabase
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        email = getIntent().getStringExtra("USERNAME");

        // Query the database based on the email
        Query query = mDatabase.orderByChild("email").equalTo(email);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Assuming you have a Users class to represent user details
                        Users userDetails = snapshot.getValue(Users.class);

                        if (userDetails != null) {
                            // You can now use userDetails to access user details
                            // For example, userDetails.getName(), userDetails.getAge(), etc.
                            String name = userDetails.getName();
                            homePageName.setText(name);
                            nameUser = name;
                        }
                    }
                } else {
                    // User details not found
                    Toast.makeText(home_page.this, "User not found in the database!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(home_page.this, "Error!", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
