package com.example.habit_tracker_prj;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProgressTrackingActivity extends AppCompatActivity {
    private EditText etLog;
    private Button btnLog, btnViewProgress;
    private TextView tvProgress;
    private String habitName, quitGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_tracking);

        etLog = findViewById(R.id.etLog);
        btnLog = findViewById(R.id.btnLog);
        btnViewProgress = findViewById(R.id.btnViewProgress);
        tvProgress = findViewById(R.id.tvProgress);

        // Retrieve the habit data passed from QuitGoalActivity
        habitName = getIntent().getStringExtra("habitName");
        quitGoal = getIntent().getStringExtra("quitGoal");

        btnLog.setOnClickListener(v -> {
            String log = etLog.getText().toString().trim();
            if (!log.isEmpty()) {
                // Save the log (this could be saved in Firestore or locally)
                Toast.makeText(ProgressTrackingActivity.this, "Progress logged!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProgressTrackingActivity.this, "Please enter a log", Toast.LENGTH_SHORT).show();
            }
        });

        btnViewProgress.setOnClickListener(v -> {
            // Display motivational messages or progress
            if (quitGoal.equals("immediate")) {
                tvProgress.setText("You're doing great by quitting immediately!");
            } else if (quitGoal.equals("gradual")) {
                tvProgress.setText("You're making excellent progress towards gradual quitting!");
            }
        });
    }
}