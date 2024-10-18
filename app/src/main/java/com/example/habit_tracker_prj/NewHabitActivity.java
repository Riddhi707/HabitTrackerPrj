package com.example.habit_tracker_prj;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NewHabitActivity extends AppCompatActivity {

    private EditText etHabitName, etTimesPerWeek, etTimesPerDay, etDuration;
    private TimePicker tpTime;
    private Button btnSubmitHabit, btnAllHabits, btnBack;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_habit);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Link UI elements to their XML counterparts
        etHabitName = findViewById(R.id.etHabitName);
        etTimesPerWeek = findViewById(R.id.etTimesPerWeek);
        etTimesPerDay = findViewById(R.id.etTimesPerDay);
        etDuration = findViewById(R.id.etDuration);
        tpTime = findViewById(R.id.tpTime);
        btnSubmitHabit = findViewById(R.id.btnSubmitHabit);
        btnAllHabits = findViewById(R.id.btnAllHabits);
        btnBack = findViewById(R.id.btnBack);

        // Set up "Submit Habit" button listener
        btnSubmitHabit.setOnClickListener(v -> {
            // Validate input fields, including TimePicker
            if (isValidInput()) {
                addNewHabit();
            } else {
                Toast.makeText(NewHabitActivity.this, "Please fill out all fields, including time", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up "All Habits" button listener to navigate to AllHabitsActivity
        btnAllHabits.setOnClickListener(v -> {
            Intent intent = new Intent(NewHabitActivity.this, AllHabitsActivity.class);
            startActivity(intent);
        });

        // Set up "Back" button listener to navigate to MainPageActivity
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(NewHabitActivity.this, MainPageActivity.class);
            startActivity(intent);
            finish(); // Optional: Call finish() to remove this activity from the back stack
        });
    }

    private boolean isValidInput() {
        // Check if the input fields are empty
        String habitName = etHabitName.getText().toString().trim();
        String timesPerWeek = etTimesPerWeek.getText().toString().trim();
        String timesPerDay = etTimesPerDay.getText().toString().trim();
        String duration = etDuration.getText().toString().trim();

        // Handle TimePicker depending on Android version
        int hour, minute;
        if (Build.VERSION.SDK_INT >= 23) {
            hour = tpTime.getHour();
            minute = tpTime.getMinute();
        } else {
            hour = tpTime.getCurrentHour();
            minute = tpTime.getCurrentMinute();
        }

        // Ensure no fields are empty, and time is set
        return !habitName.isEmpty() && !timesPerWeek.isEmpty() && !timesPerDay.isEmpty() && !duration.isEmpty()
                && !(hour == 0 && minute == 0); // TimePicker should not be default 00:00
    }

    private void addNewHabit() {
        // Get the input values
        String habitName = etHabitName.getText().toString().trim();
        String timesPerWeek = etTimesPerWeek.getText().toString().trim();
        String timesPerDay = etTimesPerDay.getText().toString().trim();
        String duration = etDuration.getText().toString().trim();

        // Handle TimePicker depending on Android version
        int hour, minute;
        if (Build.VERSION.SDK_INT >= 23) {
            hour = tpTime.getHour();
            minute = tpTime.getMinute();
        } else {
            hour = tpTime.getCurrentHour();
            minute = tpTime.getCurrentMinute();
        }

        // Validate that numeric fields are valid integers
        int parsedTimesPerWeek;
        int parsedTimesPerDay;
        int parsedDuration;

        try {
            parsedTimesPerWeek = Integer.parseInt(timesPerWeek);
            parsedTimesPerDay = Integer.parseInt(timesPerDay);
            parsedDuration = Integer.parseInt(duration);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers for times per week, times per day, and duration", Toast.LENGTH_SHORT).show();
            return; // Exit the method if parsing fails
        }

        // Create a habit map to store in Firestore
        Map<String, Object> habit = new HashMap<>();
        habit.put("habitName", habitName);
        habit.put("timesPerWeek", parsedTimesPerWeek);
        habit.put("timesPerDay", parsedTimesPerDay);
        habit.put("duration", parsedDuration);
        habit.put("hour", hour);
        habit.put("minute", minute);

        // Add the habit to the Firestore database
        db.collection("habits")
                .add(habit)
                .addOnSuccessListener(documentReference -> {
                    // Show a success toast message
                    Toast.makeText(NewHabitActivity.this, "Habit added successfully!", Toast.LENGTH_SHORT).show();
                    Log.d("Firestore", "DocumentSnapshot written with ID: " + documentReference.getId());

                    // Clear the input fields after success
                    clearFields();

                })
                .addOnFailureListener(e -> {
                    // Show an error toast message
                    Toast.makeText(NewHabitActivity.this, "Error adding habit", Toast.LENGTH_SHORT).show();
                    Log.e("Firestore", "Error adding document", e);
                });
    }

    private void clearFields() {
        // Clear all input fields
        etHabitName.setText("");
        etTimesPerWeek.setText("");
        etTimesPerDay.setText("");
        etDuration.setText("");

        // Reset the TimePicker to default (00:00)
        if (Build.VERSION.SDK_INT >= 23) {
            tpTime.setHour(0);
            tpTime.setMinute(0);
        } else {
            tpTime.setCurrentHour(0);
            tpTime.setCurrentMinute(0);
        }
    }
}
