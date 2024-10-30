package com.example.habit_track;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TrackProgressActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private LinearLayout allHabitsLayout, reviewHabitsLayout;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_progress);

        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid()).child("Habits");
        }

        allHabitsLayout = findViewById(R.id.all_habits_layout);
        reviewHabitsLayout = findViewById(R.id.review_habits_layout);
        btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(v -> finish());

        loadHabits();
    }

    private void loadHabits() {
        if (databaseReference != null) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    allHabitsLayout.removeAllViews();
                    reviewHabitsLayout.removeAllViews();

                    String currentDay = new SimpleDateFormat("EEEE", Locale.getDefault()).format(new Date());

                    for (DataSnapshot habitSnapshot : snapshot.getChildren()) {
                        String habitName = habitSnapshot.child("habitName").getValue(String.class);
                        DataSnapshot daysOfWeekSnapshot = habitSnapshot.child("daysOfWeek");
                        Long progress = habitSnapshot.child("progress").getValue(Long.class);

                        if (habitName != null) {
                            // Create a view for displaying the habit in the "All Habits" section
                            View habitView = getLayoutInflater().inflate(R.layout.item_habit, null);
                            TextView habitNameView = habitView.findViewById(R.id.habit_name);
                            habitNameView.setText(habitName);

                            ProgressBar progressBar = habitView.findViewById(R.id.progress_bar);
                            if (progress != null) {
                                progressBar.setProgress(progress.intValue());
                            } else {
                                progressBar.setProgress(0);
                            }

                            // Add the habit to the "All Habits" section
                            allHabitsLayout.addView(habitView);

                            // If the habit should be reviewed today, add it to the "Review Habits" section
                            Boolean shouldReviewToday = daysOfWeekSnapshot.child(currentDay).getValue(Boolean.class);
                            if (Boolean.TRUE.equals(shouldReviewToday)) {
                                View reviewHabitView = getLayoutInflater().inflate(R.layout.item_habit_review, null);
                                TextView reviewHabitNameView = reviewHabitView.findViewById(R.id.habit_name);
                                reviewHabitNameView.setText(habitName);

                                Button btnFollowed = reviewHabitView.findViewById(R.id.btn_followed);
                                Button btnUnfollowed = reviewHabitView.findViewById(R.id.btn_unfollowed);

                                btnFollowed.setOnClickListener(v -> markHabitProgress(habitSnapshot.getKey(), true));
                                btnUnfollowed.setOnClickListener(v -> markHabitProgress(habitSnapshot.getKey(), false));

                                reviewHabitsLayout.addView(reviewHabitView);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(TrackProgressActivity.this, "Failed to load habits: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void markHabitProgress(String habitId, boolean followed) {
        if (databaseReference != null && habitId != null) {
            DatabaseReference habitRef = databaseReference.child(habitId);
            habitRef.child("lastFollowed").setValue(followed ? "Followed" : "Unfollowed")
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            habitRef.child("progress").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Long currentProgress = snapshot.getValue(Long.class);
                                    if (currentProgress == null) {
                                        currentProgress = 0L;
                                    }
                                    long newProgress = followed ? currentProgress + 10 : Math.max(currentProgress - 10, 0);
                                    habitRef.child("progress").setValue(newProgress)
                                            .addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()) {
                                                    Toast.makeText(TrackProgressActivity.this, "Progress updated", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(TrackProgressActivity.this, "Failed to update progress", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(TrackProgressActivity.this, "Failed to load progress", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(TrackProgressActivity.this, "Failed to update progress", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
