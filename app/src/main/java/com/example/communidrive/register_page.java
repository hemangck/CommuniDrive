package com.example.communidrive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class register_page extends AppCompatActivity {

    EditText nameR, ageR, genderR, cityR, phoneR, emailR, pwdR, cPwdR;
    Button registerBtn;
    TextView loginText,headingR;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        // binding ui elements

        // EditText
        nameR = findViewById(R.id.nameR);
        ageR = findViewById(R.id.ageR);
        genderR = findViewById(R.id.genderR);
        cityR = findViewById(R.id.cityR);
        phoneR = findViewById(R.id.phoneR);
        emailR = findViewById(R.id.emailR);
        pwdR = findViewById(R.id.pwdR);
        cPwdR = findViewById(R.id.cPwdR);

        // Button
        registerBtn = findViewById(R.id.registerBtn);

        // TextView
        headingR = findViewById(R.id.headingR);
        loginText = findViewById(R.id.loginText);

        // passing intent for login page
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(register_page.this, login_page.class);
                startActivity(i1);
            }
        });

        // Reference to the Firebase Realtime Database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        // Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Adding values to the database

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });



    }

    // making function for registering an user
    public void registerUser() {
        // Collecting user data
        String name = nameR.getText().toString().trim();
        int age = Integer.parseInt(ageR.getText().toString().trim());
        String gender = genderR.getText().toString().trim();
        String city = cityR.getText().toString().trim();
        String phone = phoneR.getText().toString().trim();
        String email = emailR.getText().toString().trim();
        String pwd = pwdR.getText().toString().trim();
        String cPwd = cPwdR.getText().toString().trim();

        // Validating password match
        if (!pwd.equals(cPwd)) {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Creating a user object
        Users users = new Users(name, age, gender, city, phone, email, cPwd);

        // Registering the user in Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, cPwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registration success
                            FirebaseUser user = mAuth.getCurrentUser();

                            // Adding the user details to the database
                            mDatabase.child(user.getUid()).setValue(users);
                            Toast.makeText(register_page.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(register_page.this, login_page.class);
                            startActivity(intent);
                        } else {
                            // If registration fails, display a message to the user.
                            Toast.makeText(register_page.this, "Registration failed. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
    }