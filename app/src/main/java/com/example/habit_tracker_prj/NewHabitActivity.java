package com.example.habit_tracker_prj;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
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
    private Button btnSubmitHabit, btnAllHabits;
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

        // Set up "Add Habit" button listener
        btnSubmitHabit.setOnClickListener(v -> {
            Toast.makeText(NewHabitActivity.this, "Button clicked", Toast.LENGTH_SHORT).show();
            addNewHabit();
        });

        // Set up "All New Habits" button listener to navigate to AllHabitsActivity
        btnAllHabits.setOnClickListener(v -> {
            Intent intent = new Intent(NewHabitActivity.this, AllHabitsActivity.class);
            startActivity(intent);
        });
    }

    private void addNewHabit() {
        // Get the input values
        String habitName = etHabitName.getText().toString().trim();
        String timesPerWeek = etTimesPerWeek.getText().toString().trim();
        String timesPerDay = etTimesPerDay.getText().toString().trim();
        String duration = etDuration.getText().toString().trim();
        int hour = tpTime.getCurrentHour();
        int minute = tpTime.getCurrentMinute();

        // Validate that fields are not empty
        if (habitName.isEmpty() || timesPerWeek.isEmpty() || timesPerDay.isEmpty() || duration.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a habit map to store in Firestore
        Map<String, Object> habit = new HashMap<>();
        habit.put("habitName", habitName);
        habit.put("timesPerWeek", Integer.parseInt(timesPerWeek));
        habit.put("timesPerDay", Integer.parseInt(timesPerDay));
        habit.put("duration", Integer.parseInt(duration));
        habit.put("hour", hour);
        habit.put("minute", minute);

        // Add the habit to the Firestore database
        db.collection("habits")
                .add(habit)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(NewHabitActivity.this, "Habit added successfully", Toast.LENGTH_SHORT).show();
                    Log.d("Firestore", "DocumentSnapshot written with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(NewHabitActivity.this, "Error adding habit", Toast.LENGTH_SHORT).show();
                    Log.e("Firestore", "Error adding document", e);
                });
    }
}
