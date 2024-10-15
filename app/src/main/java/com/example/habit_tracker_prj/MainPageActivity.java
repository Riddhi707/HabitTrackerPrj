package com.example.habit_tracker_prj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_page);

        // Initialize buttons
        Button btnBuildHabit = findViewById(R.id.btnBuildHabit1); // Make sure this ID matches your layout
        Button btnQuitHabit = findViewById(R.id.btnQuitHabit1); // Make sure this ID matches your layout

        // Set click listener for Build Habit button
        btnBuildHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, NewHabitActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for Quit Habit button
        btnQuitHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, QuitHabitActivity.class);
                startActivity(intent);
            }
        });
    }
}
