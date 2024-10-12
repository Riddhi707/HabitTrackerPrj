package com.example.habit_tracker_prj;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class QuitGoalActivity extends AppCompatActivity {
    private RadioButton rbImmediate, rbGradual;
    private Button btnNext;
    private String habitName, startDate, quitDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quit_goal);

        // Retrieve the habit data passed from QuitHabitActivity
        habitName = getIntent().getStringExtra("habitName");
        startDate = getIntent().getStringExtra("startDate");
        quitDate = getIntent().getStringExtra("quitDate");

        rbImmediate = findViewById(R.id.rbImmediate);
        rbGradual = findViewById(R.id.rbGradual);
        btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(v -> {
            if (rbImmediate.isChecked()) {
                Intent intent = new Intent(QuitGoalActivity.this, ProgressTrackingActivity.class);
                intent.putExtra("habitName", habitName);
                intent.putExtra("quitGoal", "immediate");
                startActivity(intent);
            } else if (rbGradual.isChecked()) {
                Intent intent = new Intent(QuitGoalActivity.this, ProgressTrackingActivity.class);
                intent.putExtra("habitName", habitName);
                intent.putExtra("quitGoal", "gradual");
                startActivity(intent);
            } else {
                Toast.makeText(QuitGoalActivity.this, "Please select a quit goal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}