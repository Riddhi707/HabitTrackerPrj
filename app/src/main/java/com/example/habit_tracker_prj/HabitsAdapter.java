package com.example.habit_tracker_prj;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HabitsAdapter extends RecyclerView.Adapter<HabitsAdapter.HabitViewHolder> {

    private List<Habit> habitsList;

    public HabitsAdapter(List<Habit> habitsList) {
        this.habitsList = habitsList;
    }

    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_habit, parent, false);
        return new HabitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        Habit habit = habitsList.get(position);
        holder.habitName.setText(habit.getHabitName());
        holder.habitDetails.setText("Per week: " + habit.getTimesPerWeek() +
                ", Per day: " + habit.getTimesPerDay() + ", Duration: " + habit.getDuration() + " mins, Time: " +
                habit.getHour() + ":" + String.format("%02d", habit.getMinute()));
    }

    @Override
    public int getItemCount() {
        return habitsList.size();
    }

    static class HabitViewHolder extends RecyclerView.ViewHolder {

        TextView habitName, habitDetails;

        public HabitViewHolder(@NonNull View itemView) {
            super(itemView);
            habitName = itemView.findViewById(R.id.tvHabitName);
            habitDetails = itemView.findViewById(R.id.tvHabitDetails);
        }
    }
}
