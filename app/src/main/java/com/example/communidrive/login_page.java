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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class login_page extends AppCompatActivity {

    TextView headingL, registerText,forgotPwdText;
    EditText emailL, pwdL;
    Button loginBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        // Binding UI elements
        emailL = findViewById(R.id.emailL);
        pwdL = findViewById(R.id.pwdL);
        headingL = findViewById(R.id.headingL);
        registerText = findViewById(R.id.registerText);
        loginBtn = findViewById(R.id.loginBtn);
        forgotPwdText = findViewById(R.id.forgotPwdText);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        String emailLText = emailL.getText().toString().trim();

        // Passing intent for register page
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i4 = new Intent(login_page.this, register_page.class);
                startActivity(i4);
            }
        });

        // Log in function
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        // reset password text (button)
        forgotPwdText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailL.getText().toString().trim();

                if (email.isEmpty()) {
                    emailL.setError("Enter your email");
                    emailL.requestFocus();
                } else {
                    resetPassword(email);
                }
            }
        });

    }

    private void resetPassword(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(login_page.this,
                                    "Password reset email sent. Check your email inbox.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(login_page.this,
                                    "Failed to send reset email. Check your email address.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loginUser() {
        String email = emailL.getText().toString().trim();
        String password = pwdL.getText().toString().trim();

        // Sign in with an existing user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Login success
                            FirebaseUser user = mAuth.getCurrentUser();
                            startChatActivity(email);

                            Toast.makeText(login_page.this, "Login successful.", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(login_page.this,homePage.class);
//                            onActivityResult(intent);
                        } else {
                            // Login failed, handle the error
                            Toast.makeText(login_page.this, "Login failed. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void startChatActivity(String emailLText) {
        Intent intent = new Intent(login_page.this, home_page.class);
        intent.putExtra("USERNAME", emailLText);
//        intent.putExtra("USER", user);
        startActivity(intent);
        finish();

        Intent intent2 = new Intent(login_page.this, chat.class);
        intent2.putExtra("USERNAME", emailLText);
//        intent.putExtra("USER", user);
        startActivity(intent2);
        finish();
    }

    private void sendVerificationEmailAndCheckStatus() {
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            // Check if the user's email is already verified
            if (user.isEmailVerified()) {
                // User is signed in, and email is verified
                Toast.makeText(this, "Email is already verified.", Toast.LENGTH_SHORT).show();
            } else {
                // Send verification email
                user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Verification email sent successfully
                            Toast.makeText(login_page.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Failed to send verification email
                            Toast.makeText(login_page.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } else {
            // User is not signed in
            Toast.makeText(this, "User is not signed in.", Toast.LENGTH_SHORT).show();
        }
    }

}
