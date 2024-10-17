package com.example.habit_tracker_prj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class QuitHabitActivity extends AppCompatActivity {

    EditText habitNameEditText, startDateEditText, quitDateEditText;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quit_habit);

        habitNameEditText = findViewById(R.id.habitNameEditText);
        startDateEditText = findViewById(R.id.startDateEditText);
        quitDateEditText = findViewById(R.id.quitDateEditText);
        nextButton = findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String habitName = habitNameEditText.getText().toString();
                String startDate = startDateEditText.getText().toString();
                String quitDate = quitDateEditText.getText().toString();

                if (habitName.isEmpty() || startDate.isEmpty() || quitDate.isEmpty()) {
                    Toast.makeText(QuitHabitActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(QuitHabitActivity.this, QuitGoalActivity.class);
                    intent.putExtra("habitName", habitName);
                    intent.putExtra("startDate", startDate);
                    intent.putExtra("quitDate", quitDate);
                    startActivity(intent);
                }
            }
        });
    }
}
