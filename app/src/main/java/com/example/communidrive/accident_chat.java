package com.example.communidrive;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class accident_chat extends AppCompatActivity {

    EditText etMessage;
    Button btnSend;
    ListView listView;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    String username;
    ArrayAdapter<String> adapter;
    ArrayList<String> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accident_chat);

        etMessage = findViewById(R.id.editTextMessage);
        btnSend = findViewById(R.id.buttonSend);
        listView = findViewById(R.id.listViewMessages);

        // Initialize message list and adapter
        messageList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messageList);
        listView.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("chat").child("details").child("accident");

        username = getIntent().getStringExtra("NAME");

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
            ChatMessage chatMessage = new ChatMessage(username, messageText);
            mDatabase.push().setValue(chatMessage);
            etMessage.setText("");
        }
    }

    private void setupMessageListener() {
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                displayMessage(chatMessage);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void displayMessage(ChatMessage chatMessage) {
        // Assuming you have a simple ChatMessage class with getUsername() and getMessage() methods
        String message = chatMessage.getUsername() + ": " + chatMessage.getMessage();

        // Add the message to your list and update the adapter
        messageList.add(message);
        adapter.notifyDataSetChanged();

        // Scroll the ListView to the bottom to show the latest message
        listView.setSelection(adapter.getCount() - 1);
    }


}
