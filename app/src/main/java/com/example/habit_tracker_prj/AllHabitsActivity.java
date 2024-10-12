package com.example.habit_tracker_prj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AllHabitsActivity extends AppCompatActivity {

    private RecyclerView rvAllHabits;
    private FirebaseFirestore db;
    private HabitsAdapter adapter;
    private List<Habit> habitsList;
    private Button btnBackToAddHabit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_habits);

        rvAllHabits = findViewById(R.id.rvAllHabits);
        rvAllHabits.setLayoutManager(new LinearLayoutManager(this));

        btnBackToAddHabit = findViewById(R.id.btnBackToAddHabit);
        btnBackToAddHabit.setOnClickListener(v -> {
            // Navigate back to NewHabitActivity
            finish(); // Ends this activity and goes back to the previous one
        });

        db = FirebaseFirestore.getInstance();
        habitsList = new ArrayList<>();
        adapter = new HabitsAdapter(habitsList);
        rvAllHabits.setAdapter(adapter);

        fetchHabitsFromDatabase();
    }

    private void fetchHabitsFromDatabase() {
        db.collection("habits")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot document : list) {
                            Habit habit = document.toObject(Habit.class);
                            habitsList.add(habit);
                        }
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Error fetching data", e));


        View btnQuitHabit = null;
        btnQuitHabit.setOnClickListener(v -> {
            // Redirect to QuitHabitActivity
            startActivity(new Intent(AllHabitsActivity.this, QuitHabitActivity.class));
        });
    }
}
