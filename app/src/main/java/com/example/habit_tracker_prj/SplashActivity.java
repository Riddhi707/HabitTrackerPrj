package com.example.habit_tracker_prj;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {


    // Duration of the splash screen in milliseconds
    private static final int SPLASH_SCREEN_DURATION = 5000; // 3 seconds
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        // Use a Handler to delay the transition to the main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the MainActivity
                Intent intent = new Intent(SplashActivity.this, Register_Activity.class); // Change to your main activity
                startActivity(intent);
                // Finish the splash activity
                finish();
            }
        }, SPLASH_SCREEN_DURATION);
    }


}











