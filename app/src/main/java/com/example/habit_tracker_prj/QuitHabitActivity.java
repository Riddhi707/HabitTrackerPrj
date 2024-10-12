package com.example.habit_tracker_prj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class QuitHabitActivity extends AppCompatActivity {
    private EditText etHabitName, etStartDate, etQuitDate;
    private Button btnSubmit;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quit_habit); // Ensure this layout file exists

        // Initialize UI components
        etHabitName = findViewById(R.id.etHabitName);
        etStartDate = findViewById(R.id.etStartDate);
        etQuitDate = findViewById(R.id.etQuitDate);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Set OnClickListener for the button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("QuitHabitActivity", "Submit button clicked"); // Log message
                String habitName = etHabitName.getText().toString().trim();
                String startDate = etStartDate.getText().toString().trim();
                String quitDate = etQuitDate.getText().toString().trim();

                // Validate input fields
                if (habitName.isEmpty() || startDate.isEmpty() || quitDate.isEmpty()) {
                    Toast.makeText(QuitHabitActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    logQuitHabit(habitName, startDate, quitDate); // Call method to log habit
                }
            }
        });
    }

    private void logQuitHabit(String habitName, String startDate, String quitDate) {
        // Create a map to hold habit data
        Map<String, Object> habitData = new HashMap<>();
        habitData.put("habitName", habitName);
        habitData.put("startDate", startDate);
        habitData.put("quitDate", quitDate);

        // Save the habit data to Firestore
        db.collection("habits")
                .add(habitData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(QuitHabitActivity.this, "Habit logged successfully!", Toast.LENGTH_SHORT).show();

                    // Navigate to QuitGoalActivity after success
                    Intent intent = new Intent(QuitHabitActivity.this, QuitGoalActivity.class);
                    intent.putExtra("habitName", habitName); // Pass the habit name to the next activity
                    intent.putExtra("startDate", startDate); // Pass the start date to the next activity
                    intent.putExtra("quitDate", quitDate); // Pass the quit date to the next activity
                    startActivity(intent);
                    finish(); // Close the current activity
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(QuitHabitActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
