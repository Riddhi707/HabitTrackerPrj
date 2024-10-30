package com.example.habit_track;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class EditDeleteHabitActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private LinearLayout habitsLayout;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_habit);

        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid()).child("Habits");
        }

        habitsLayout = findViewById(R.id.habits_layout);
        btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(v -> finish());

        loadHabits();
    }

    private void loadHabits() {
        if (databaseReference != null) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    habitsLayout.removeAllViews();

                    for (DataSnapshot habitSnapshot : snapshot.getChildren()) {
                        String habitId = habitSnapshot.getKey();
                        String habitName = habitSnapshot.child("habitName").getValue(String.class);

                        if (habitId != null && habitName != null) {
                            View habitView = getLayoutInflater().inflate(R.layout.item_edit_delete_habit, null);
                            TextView habitNameView = habitView.findViewById(R.id.habit_name);
                            habitNameView.setText(habitName);

                            Button btnEdit = habitView.findViewById(R.id.btn_edit);
                            Button btnDelete = habitView.findViewById(R.id.btn_delete);

                            btnEdit.setOnClickListener(v -> {
                                Intent intent = new Intent(EditDeleteHabitActivity.this, CreateHabitActivity.class);
                                intent.putExtra("habitId", habitId);
                                intent.putExtra("editMode", true);
                                startActivity(intent);
                            });

                            btnDelete.setOnClickListener(v -> deleteHabit(habitId));

                            habitsLayout.addView(habitView);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(EditDeleteHabitActivity.this, "Failed to load habits: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void deleteHabit(String habitId) {
        if (databaseReference != null && habitId != null) {
            databaseReference.child(habitId).removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(EditDeleteHabitActivity.this, "Habit deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditDeleteHabitActivity.this, "Failed to delete habit", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
