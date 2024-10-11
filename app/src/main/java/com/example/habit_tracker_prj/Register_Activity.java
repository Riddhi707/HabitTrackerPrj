package com.example.habit_tracker_prj;

import android.os.Bundle;
import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Register_Activity extends AppCompatActivity {
    private TextInputEditText editTextEmail, editTextPassword;
    private Button buttonRegister;
    private TextView textViewLogin;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        // Initialize views
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        textViewLogin = findViewById(R.id.textViewLogin);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Handle "Register" button click
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (!validateEmail(email)) {
                    editTextEmail.setError("Please enter a valid email address");
                    editTextEmail.requestFocus();
                    return;
                }

                if (password.isEmpty() || password.length() < 6) {
                    editTextPassword.setError("Password must be at least 6 characters long");
                    editTextPassword.requestFocus();
                    return;
                }

                // Create an account with Firebase
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                if (user != null) {
                                    Toast.makeText(Register_Activity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                                    // Redirect to LoginActivity after sign-up
                                    startActivity(new Intent(Register_Activity.this, Login_Activity.class));
                                    finish();  // Close the register activity
                                }
                            } else {
                                Toast.makeText(Register_Activity.this, "Account creation failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // Handle "Login here" text click to navigate to login page
        textViewLogin.setOnClickListener(v -> {
            startActivity(new Intent(Register_Activity.this, Login_Activity.class));
        });
    }

    // Validate email format
    private boolean validateEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
