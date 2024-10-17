package com.example.habit_tracker_prj;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

public class AchievementActivity extends AppCompatActivity {

    TextView achievementTextView;
    Button viewReportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        achievementTextView = findViewById(R.id.achievementTextView);
        viewReportButton = findViewById(R.id.viewReportButton);

        // Display achievement message
        achievementTextView.setText("Congratulations! You have successfully quit your habit!");

        viewReportButton.setOnClickListener(v -> {

            // Sample report data (You can modify this according to your app's logic)
            String reportData = "You quit your habit on: " + "2024-10-17" + "\n" +
                    "Goals: Immediate Quit\n" +
                    "Progress: 30 days without the habit.";

            Intent intent = new Intent(AchievementActivity.this, ReportActivity.class);
            intent.putExtra("REPORT_DATA", reportData);
            startActivity(intent);
        });


    }
}