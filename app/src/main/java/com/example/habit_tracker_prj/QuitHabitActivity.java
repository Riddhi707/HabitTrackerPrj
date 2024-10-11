package com.example.habit_tracker_prj;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class QuitHabitActivity extends AppCompatActivity {

    private EditText etHabitName, etStartDate, etQuitDate;
    private TextView tvProgress;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quit_habit);

        etHabitName = findViewById(R.id.etHabitName);
        etStartDate = findViewById(R.id.etStartDate);
        etQuitDate = findViewById(R.id.etQuitDate);
        Button btnSubmit = findViewById(R.id.btnSubmit);
        tvProgress = findViewById(R.id.tvProgress);

        db = FirebaseFirestore.getInstance();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String habitName = etHabitName.getText().toString().trim();
                String startDate = etStartDate.getText().toString().trim();
                String quitDate = etQuitDate.getText().toString().trim();

                if (habitName.isEmpty() || startDate.isEmpty() || quitDate.isEmpty()) {
                    Toast.makeText(QuitHabitActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    logQuitHabit(habitName, startDate, quitDate);
                }
            }
        });
    }

    private void logQuitHabit(String habitName, String startDate, String quitDate) {
        // Create a map to hold habit data
        Map<String, Object> habitData = new HashMap<>();
        habitData.put("habitName", habitName);
        habitData.put("startDate", startDate);
        habitData.put("quitDate", quitDate);

        // Save the habit data to Firestore
        db.collection("habits")
                .add(habitData)
                .addOnSuccessListener(documentReference -> {
                    tvProgress.setText(getString(R.string.success_quit_message));
                })
                .addOnFailureListener(e -> {
                    tvProgress.setText(getString(R.string.error_logging_quit_habit, e.getMessage()));
                });
    }
}
