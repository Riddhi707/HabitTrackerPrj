package com.example.habit_tracker_prj;

public class Habit {
    private String habitName;
    private int timesPerWeek;
    private int timesPerDay;
    private int duration;
    private int hour;
    private int minute;

    // Empty constructor required for Firestore
    public Habit() {}

    public Habit(String habitName, int timesPerWeek, int timesPerDay, int duration, int hour, int minute) {
        this.habitName = habitName;
        this.timesPerWeek = timesPerWeek;
        this.timesPerDay = timesPerDay;
        this.duration = duration;
        this.hour = hour;
        this.minute = minute;
    }

    // Getters
    public String getHabitName() {
        return habitName;
    }

    public int getTimesPerWeek() {
        return timesPerWeek;
    }

    public int getTimesPerDay() {
        return timesPerDay;
    }

    public int getDuration() {
        return duration;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }
}
