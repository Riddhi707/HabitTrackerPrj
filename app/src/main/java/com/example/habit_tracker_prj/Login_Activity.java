package com.example.habit_tracker_prj;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Activity extends AppCompatActivity {

    private TextInputEditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewRegister = findViewById(R.id.textViewRegister);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Login button click listener
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    editTextEmail.setError("Email is required.");
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editTextEmail.setError("Please enter a valid email.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    editTextPassword.setError("Password is required.");
                    return;
                }

                // Authenticate the user without checking email verification
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Move to NewHabitActivity if login is successful
                                startActivity(new Intent(Login_Activity.this, NewHabitActivity.class));
                                finish();
                            } else {
                                Toast.makeText(Login_Activity.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // Register here click listener
        textViewRegister.setOnClickListener(v -> {
            // Go to RegisterActivity when "Register here" is clicked
            startActivity(new Intent(Login_Activity.this, Register_Activity.class));
        });
    }
}
