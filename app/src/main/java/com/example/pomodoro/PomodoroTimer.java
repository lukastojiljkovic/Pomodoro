package com.example.pomodoro;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class PomodoroTimer {
    // Declare constants for session durations
    private static final long WORK_DURATION = 20 * 60 * 1000; // 20 minutes in milliseconds
    private static final long SHORT_BREAK_DURATION = 5 * 60 * 1000; // 5 minutes in milliseconds
    private static final long LONG_BREAK_DURATION = 15 * 60 * 1000; // 15 minutes in milliseconds
    private final Context context;

    // Declare enum for session types
    public enum Session {
        WORK,
        SHORT_BREAK,
        LONG_BREAK
    }

    // Declare variables for timer logic
    private Session currentSession;
    private long timeRemaining;
    private int longBreaksTaken;
    private boolean isRunning;
    private int completedPomodoros;
    private int workSessionsTaken;

    public PomodoroTimer(Context context) {
        this.context = context;
        // Initialize timer logic
        currentSession = Session.WORK;
        timeRemaining = WORK_DURATION;
        longBreaksTaken = 0;
        isRunning = false;
        completedPomodoros = 0;
        workSessionsTaken = 0;
    }

    private void incrementCompletedPomodoros() {
        try {
            String filename = "completedPomodoros.txt";

            // Read the current count from the file
            FileInputStream fis = context.openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String fileContents = reader.readLine();
            int count = Integer.parseInt(fileContents);
            fis.close();

            // Increment the count by 1
            count++;

            // Write the updated count to the file
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            String updatedContents = Integer.toString(count);
            fos.write(updatedContents.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            // If the file doesn't exist, create it with a default value of 0 and increment it
            try {
                String filename = "completedPomodoros.txt";
                int count = 1;

                // Write the initial count to the file
                FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
                String initialContents = Integer.toString(count);
                fos.write(initialContents.getBytes());
                fos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        // Start or resume the timer
        isRunning = true;

        // Create a new thread to update the timer
        new Thread(() -> {
            while (isRunning) {
                // Update the timer
                updateTimer();

                // Sleep for a short time to avoid using too much CPU
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void pause() {
        // Pause the timer
        isRunning = false;
    }

    public void stop() {
        // Stop and reset the timer
        currentSession = Session.WORK;
        timeRemaining = WORK_DURATION;
        longBreaksTaken = 0;
        isRunning = false;
    }

    private void updateTimer() {
        // Decrement the time remaining
        timeRemaining -= 100;

        // Check if the session has ended
        if (timeRemaining <= 0) {
            // Transition to the next session
            transitionToNextSession();
        }
    }

    private void transitionToNextSession() {
        switch (currentSession) {
            case WORK:
                workSessionsTaken++;
                if (workSessionsTaken % 4 == 0) {
                    longBreaksTaken++;
                    if (longBreaksTaken < 4) {
                        // Transition from work to long break
                        currentSession = Session.LONG_BREAK;
                        timeRemaining = LONG_BREAK_DURATION;
                    } else {
                        // The pomodoro is finished
                        incrementCompletedPomodoros();
                        stop();
                    }
                    workSessionsTaken = 0;
                } else {
                    // Transition from work to short break
                    currentSession = Session.SHORT_BREAK;
                    timeRemaining = SHORT_BREAK_DURATION;
                }
                break;
            case SHORT_BREAK:
                // Transition from short break to work
                currentSession = Session.WORK;
                timeRemaining = WORK_DURATION;
                break;
            case LONG_BREAK:
                // Transition from long break to work
                currentSession = Session.WORK;
                timeRemaining = WORK_DURATION;
                break;
            default:
                break;
        }
    }

    public int getLongBreaksTaken() {
        return longBreaksTaken;
    }

    public int getCompletedPomodoros() {
        return completedPomodoros;
    }

    public String getCurrentSessionName() {
        // Return the name of the current session
        switch (currentSession) {
            case WORK:
                return "Work";
            case SHORT_BREAK:
                return "Short Break";
            case LONG_BREAK:
                return "Long Break";
            default:
                return "";
        }
    }

    public String getTimeRemaining() {
        // Return the time remaining in the current session as a string
        int minutes = (int) (timeRemaining / (60 * 1000));
        int seconds = (int) ((timeRemaining / 1000) % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }

    public int getProgress() {
        // Return the progress of the current session as a percentage
        long sessionDuration;
        switch (currentSession) {
            case WORK:
                sessionDuration = WORK_DURATION;
                break;
            case SHORT_BREAK:
                sessionDuration = SHORT_BREAK_DURATION;
                break;
            case LONG_BREAK:
                sessionDuration = LONG_BREAK_DURATION;
                break;
            default:
                sessionDuration = 0;
                break;
        }
        return (int) (((sessionDuration - timeRemaining) / (double) sessionDuration) * 100);
    }

    public Session getCurrentSession() {
        // Return the current session
        return currentSession;
    }
}