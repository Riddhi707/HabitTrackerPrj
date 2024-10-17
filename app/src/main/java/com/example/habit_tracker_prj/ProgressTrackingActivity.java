package com.example.habit_tracker_prj;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProgressTrackingActivity extends AppCompatActivity {

    EditText progressEditText;
    Button logProgressButton;
    TextView progressListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_tracking);

        progressEditText = findViewById(R.id.progressEditText);
        logProgressButton = findViewById(R.id.logProgressButton);
        progressListTextView = findViewById(R.id.progressListTextView);

        logProgressButton.setOnClickListener(v -> logProgress());
    }

    private void logProgress() {
        String progressEntry = progressEditText.getText().toString().trim();

        if (TextUtils.isEmpty(progressEntry)) {
            Toast.makeText(this, "Please enter your progress", Toast.LENGTH_SHORT).show();
            return;
        }

        // Here you could save the progress to a database or other storage
        // For now, we will just display it in a TextView

        // Append the new progress entry to the TextView
        String currentProgress = progressListTextView.getText().toString();
        progressListTextView.setText(currentProgress + "\n" + progressEntry);

        // Clear the EditText after logging
        progressEditText.setText("");
        Toast.makeText(this, "Progress logged!", Toast.LENGTH_SHORT).show();
    }
}
