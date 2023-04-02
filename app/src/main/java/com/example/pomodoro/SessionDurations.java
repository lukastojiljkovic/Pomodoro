package com.example.pomodoro;

public class SessionDurations {
    // Declare default values for durations
    long workDuration = 20;
    long shortBreakDuration = 5;
    long longBreakDuration = 15;

    // initialise the constructor with default times for pomodoro timer
    public SessionDurations() {
        workDuration = this.getWorkDuration();
        shortBreakDuration = this.getShortBreakDuration();
        longBreakDuration = this.getLongBreakDuration();
    }

    public void setLongBreakDuration(long longBreakDuration) {
        this.longBreakDuration = longBreakDuration;
    }

    public void setShortBreakDuration(long shortBreakDuration) {
        this.shortBreakDuration = shortBreakDuration;
    }

    public void setWorkDuration(long workDuration) {
        this.workDuration = workDuration;
    }

    public long getWorkDuration() {
        return workDuration;
    }

    public long getShortBreakDuration() {
        return shortBreakDuration;
    }

    public long getLongBreakDuration() {
        return longBreakDuration;
    }


}
