package com.example.habit_tracker_prj;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AchievementActivity extends AppCompatActivity {
    private TextView tvAchievement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        tvAchievement = findViewById(R.id.tvAchievement);
        tvAchievement.setText("Congratulations! You've successfully quit your habit.");
    }
}