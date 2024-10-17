package com.example.habit_tracker_prj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;

public class QuitGoalActivity extends AppCompatActivity {

    RadioButton immediateGoal, gradualGoal;
    Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quit_goal);

        immediateGoal = findViewById(R.id.immediateGoal);
        gradualGoal = findViewById(R.id.gradualGoal);
        continueButton = findViewById(R.id.continueButton);

        String habitName = getIntent().getStringExtra("habitName");
        String startDate = getIntent().getStringExtra("startDate");
        String quitDate = getIntent().getStringExtra("quitDate");

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String goalType = immediateGoal.isChecked() ? "Immediate" : "Gradual";

                Intent intent = new Intent(QuitGoalActivity.this, ProgressTrackingActivity.class);
                intent.putExtra("habitName", habitName);
                intent.putExtra("startDate", startDate);
                intent.putExtra("quitDate", quitDate);
                intent.putExtra("goalType", goalType);
                startActivity(intent);
            }
        });
    }
}
