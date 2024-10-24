
package com.example.habit_tracker_prj;
import android.os.Bundle;


import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProgressTrackingActivity extends AppCompatActivity {

    EditText progressEditText;
    Button logProgressButton;
    TextView progressListTextView, progressMessageTextView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_tracking);

        progressEditText = findViewById(R.id.progressEditText);
        logProgressButton = findViewById(R.id.logProgressButton);
        progressListTextView = findViewById(R.id.progressMessageTextView);
        progressBar = findViewById(R.id.progressBar);
        progressMessageTextView = findViewById(R.id.progressMessageTextView);

        logProgressButton.setOnClickListener(v -> logProgress());
    }

    private void logProgress() {
        String progressEntry = progressEditText.getText().toString().trim();

        if (TextUtils.isEmpty(progressEntry)) {
            Toast.makeText(this, "Please enter your progress", Toast.LENGTH_SHORT).show();
            return;
        }

        int progressValue;
        try {
            progressValue = Integer.parseInt(progressEntry);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a number between 1 and 10", Toast.LENGTH_SHORT).show();
            return;
        }

        if (progressValue < 1 || progressValue > 10) {
            Toast.makeText(this, "Progress must be between 1 and 10", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update ProgressBar and message based on the entered value
        progressBar.setVisibility(ProgressBar.VISIBLE);
        progressBar.setProgress(progressValue);

        String message;
        if (progressValue == 1) {
            message = "Poor. Try harder!";
        } else if (progressValue <= 5) {
            message = "Do more. Keep going!";
        } else if (progressValue < 10) {
            message = "Good. You're doing well!";
        } else {
            message = "Excellent! You did great!";
        }

        progressMessageTextView.setVisibility(TextView.VISIBLE);
        progressMessageTextView.setText(message);

        // Append the new progress entry to the TextView
        String currentProgress = progressListTextView.getText().toString();
        progressListTextView.setText(currentProgress + "\nProgress: " + progressValue);

        // Clear the EditText after logging
        progressEditText.setText("");
        Toast.makeText(this, "Progress logged!", Toast.LENGTH_SHORT).show();
    }
}
