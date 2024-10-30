package com.example.habit_track;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CreateHabitActivity extends AppCompatActivity {

    private EditText inputHabitName, inputWeeklyFrequency, inputDailyFrequency, inputTimeOfDay;
    private CheckBox checkboxMonday, checkboxTuesday, checkboxWednesday, checkboxThursday, checkboxFriday, checkboxSaturday, checkboxSunday;
    private Button btnSubmitHabit, btnBack;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_habit);

        // Initialize Firebase Auth and Database Reference
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid()).child("Habits");
        }

        inputHabitName = findViewById(R.id.habit_name);
        inputWeeklyFrequency = findViewById(R.id.weekly_frequency);
        inputDailyFrequency = findViewById(R.id.daily_frequency);
        inputTimeOfDay = findViewById(R.id.time_of_day);
        checkboxMonday = findViewById(R.id.checkbox_monday);
        checkboxTuesday = findViewById(R.id.checkbox_tuesday);
        checkboxWednesday = findViewById(R.id.checkbox_wednesday);
        checkboxThursday = findViewById(R.id.checkbox_thursday);
        checkboxFriday = findViewById(R.id.checkbox_friday);
        checkboxSaturday = findViewById(R.id.checkbox_saturday);
        checkboxSunday = findViewById(R.id.checkbox_sunday);
        btnSubmitHabit = findViewById(R.id.btn_submit_habit);
        btnBack = findViewById(R.id.btn_back);
        progressBar = findViewById(R.id.progressBar);

        // Check if edit mode
        Intent intent = getIntent();
        String habitId = intent.getStringExtra("habitId");
        boolean isEditMode = intent.getBooleanExtra("editMode", false);

        if (isEditMode && habitId != null) {
            // Load existing habit details
            loadHabitDetails(habitId);
        }

        btnSubmitHabit.setOnClickListener(v -> {
            String habitName = inputHabitName.getText().toString().trim();
            String weeklyFrequency = inputWeeklyFrequency.getText().toString().trim();
            String dailyFrequency = inputDailyFrequency.getText().toString().trim();
            String timeOfDay = inputTimeOfDay.getText().toString().trim();

            if (TextUtils.isEmpty(habitName)) {
                Toast.makeText(getApplicationContext(), "Enter habit name!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(weeklyFrequency)) {
                Toast.makeText(getApplicationContext(), "Enter weekly frequency!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(dailyFrequency)) {
                Toast.makeText(getApplicationContext(), "Enter daily frequency!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(timeOfDay)) {
                Toast.makeText(getApplicationContext(), "Enter time of day!", Toast.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            if (isEditMode) {
                // Update existing habit
                updateHabit(habitId, habitName, weeklyFrequency, dailyFrequency, timeOfDay);
            } else {
                // Add new habit
                addNewHabit(habitName, weeklyFrequency, dailyFrequency, timeOfDay);
            }
        });

        btnBack.setOnClickListener(v -> finish());
    }

    private void loadHabitDetails(String habitId) {
        databaseReference.child(habitId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                inputHabitName.setText(snapshot.child("habitName").getValue(String.class));
                inputWeeklyFrequency.setText(snapshot.child("weeklyFrequency").getValue(String.class));
                inputDailyFrequency.setText(snapshot.child("dailyFrequency").getValue(String.class));
                inputTimeOfDay.setText(snapshot.child("timeOfDay").getValue(String.class));

                HashMap<String, Boolean> daysOfWeek = (HashMap<String, Boolean>) snapshot.child("daysOfWeek").getValue();
                if (daysOfWeek != null) {
                    checkboxMonday.setChecked(Boolean.TRUE.equals(daysOfWeek.get("Monday")));
                    checkboxTuesday.setChecked(Boolean.TRUE.equals(daysOfWeek.get("Tuesday")));
                    checkboxWednesday.setChecked(Boolean.TRUE.equals(daysOfWeek.get("Wednesday")));
                    checkboxThursday.setChecked(Boolean.TRUE.equals(daysOfWeek.get("Thursday")));
                    checkboxFriday.setChecked(Boolean.TRUE.equals(daysOfWeek.get("Friday")));
                    checkboxSaturday.setChecked(Boolean.TRUE.equals(daysOfWeek.get("Saturday")));
                    checkboxSunday.setChecked(Boolean.TRUE.equals(daysOfWeek.get("Sunday")));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CreateHabitActivity.this, "Failed to load habit details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateHabit(String habitId, String habitName, String weeklyFrequency, String dailyFrequency, String timeOfDay) {
        HashMap<String, Object> habitMap = new HashMap<>();
        habitMap.put("habitName", habitName);
        habitMap.put("weeklyFrequency", weeklyFrequency);
        habitMap.put("dailyFrequency", dailyFrequency);
        habitMap.put("timeOfDay", timeOfDay);

        HashMap<String, Boolean> daysOfWeek = new HashMap<>();
        daysOfWeek.put("Monday", checkboxMonday.isChecked());
        daysOfWeek.put("Tuesday", checkboxTuesday.isChecked());
        daysOfWeek.put("Wednesday", checkboxWednesday.isChecked());
        daysOfWeek.put("Thursday", checkboxThursday.isChecked());
        daysOfWeek.put("Friday", checkboxFriday.isChecked());
        daysOfWeek.put("Saturday", checkboxSaturday.isChecked());
        daysOfWeek.put("Sunday", checkboxSunday.isChecked());

        habitMap.put("daysOfWeek", daysOfWeek);

        databaseReference.child(habitId).updateChildren(habitMap).addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                Toast.makeText(CreateHabitActivity.this, "Habit updated successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(CreateHabitActivity.this, "Failed to update habit. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addNewHabit(String habitName, String weeklyFrequency, String dailyFrequency, String timeOfDay) {
        // Create a unique ID for each habit
        String habitId = databaseReference.push().getKey();
        if (habitId != null) {
            HashMap<String, Object> habitMap = new HashMap<>();
            habitMap.put("habitId", habitId);
            habitMap.put("habitName", habitName);
            habitMap.put("weeklyFrequency", weeklyFrequency);
            habitMap.put("dailyFrequency", dailyFrequency);
            habitMap.put("timeOfDay", timeOfDay);

            HashMap<String, Boolean> daysOfWeek = new HashMap<>();
            daysOfWeek.put("Monday", checkboxMonday.isChecked());
            daysOfWeek.put("Tuesday", checkboxTuesday.isChecked());
            daysOfWeek.put("Wednesday", checkboxWednesday.isChecked());
            daysOfWeek.put("Thursday", checkboxThursday.isChecked());
            daysOfWeek.put("Friday", checkboxFriday.isChecked());
            daysOfWeek.put("Saturday", checkboxSaturday.isChecked());
            daysOfWeek.put("Sunday", checkboxSunday.isChecked());

            habitMap.put("daysOfWeek", daysOfWeek);

            // Store habit in Firebase under user's habits
            databaseReference.child(habitId).setValue(habitMap).addOnCompleteListener(task -> {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(CreateHabitActivity.this, "Habit added successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CreateHabitActivity.this, "Failed to add habit. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
