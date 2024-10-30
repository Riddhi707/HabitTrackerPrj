package com.example.habit_track;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button btnCreateHabit, btnTrackProgress, btnEditDeleteHabit, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateHabit = findViewById(R.id.btn_create_habit);
        btnTrackProgress = findViewById(R.id.btn_track_progress);
        btnEditDeleteHabit = findViewById(R.id.btn_edit_delete_habit);
        btnLogout = findViewById(R.id.btn_logout);

        btnCreateHabit.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CreateHabitActivity.class)));
        btnTrackProgress.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TrackProgressActivity.class)));
        btnEditDeleteHabit.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, EditDeleteHabitActivity.class)));

        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });
    }
}
