package com.example.communidrive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class traffic_chat extends AppCompatActivity {

    EditText etMessage;
    Button btnSend;
    ListView listView;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase,mDatabase2;
    String username,name;
    ArrayAdapter<String> adapter;
    ArrayList<String> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_chat);

        etMessage = findViewById(R.id.editTextMessage);

        btnSend = findViewById(R.id.buttonSend);
        listView = findViewById(R.id.listViewMessages);

        // Initialize message list and adapter
        messageList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messageList);
        listView.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("chat").child("details").child("messages").child("traffic");
//        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("users");

        username = getIntent().getStringExtra("USERNAME");
        name = getIntent().getStringExtra("NAME");

//        Intent intent = getIntent();
//        String userEmail = intent.getStringExtra("EMAIL");
//        FirebaseUser user = intent.getParcelableExtra("USER");

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        setupMessageListener();
    }

    private void sendMessage() {
        String messageText = etMessage.getText().toString().trim();

        if (!TextUtils.isEmpty(messageText)) {
            ChatMessage chatMessage = new ChatMessage(name, messageText);
            mDatabase.push().setValue(chatMessage);
            etMessage.setText("");
        }
    }

    private void setupMessageListener() {
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                displayMessage(chatMessage);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void displayMessage(ChatMessage chatMessage) {
        // Assuming you have a simple ChatMessage class with getUsername() and getMessage() methods
        String message = chatMessage.getName() + ": " + chatMessage.getMessage();

        // Add the message to your list and update the adapter
        messageList.add(message);
        adapter.notifyDataSetChanged();

        // Scroll the ListView to the bottom to show the latest message
        listView.setSelection(adapter.getCount() - 1);
    }
}