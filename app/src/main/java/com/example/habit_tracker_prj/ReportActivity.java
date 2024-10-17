package com.example.habit_tracker_prj;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ReportActivity extends AppCompatActivity {

    TextView reportTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        reportTextView = findViewById(R.id.reportTextView);

        // Get data passed from AchievementActivity
        String reportData = getIntent().getStringExtra("REPORT_DATA");
        reportTextView.setText(reportData != null ? reportData : "No report data available.");
    }
}
