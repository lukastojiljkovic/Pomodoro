package com.example.pomodoro;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    // Declare variables for UI elements
    private EditText workDurationEditText;
    private EditText shortBreakDurationEditText;
    private EditText longBreakDurationEditText;
    private Button saveButton;
    private Button resetButton;

    // Declare variables for durations
    SessionDurations durations = new SessionDurations();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize UI elements
        workDurationEditText = findViewById(R.id.workDurationEditText);
        shortBreakDurationEditText = findViewById(R.id.shortBreakDurationEditText);
        longBreakDurationEditText = findViewById(R.id.longBreakDurationEditText);
        saveButton = findViewById(R.id.saveButton);
        resetButton = findViewById(R.id.resetButton);

        // Set up button click listener
        saveButton.setOnClickListener(view -> {
            // Save settings
            saveSettings();
        });
        // resets the durations to default
        resetButton.setOnClickListener(view -> {
            // Reset settings to default values
            resetSettings();
        });
    }

    private void saveSettings() {
        // Get durations from EditTexts and convert to int
        int workDuration = Integer.parseInt(workDurationEditText.getText().toString());
        int shortBreakDuration = Integer.parseInt(shortBreakDurationEditText.getText().toString());
        int longBreakDuration = Integer.parseInt(longBreakDurationEditText.getText().toString());

        // Set new durations in the SessionDurations object
        durations.setWorkDuration(workDuration);
        durations.setShortBreakDuration(shortBreakDuration);
        durations.setLongBreakDuration(longBreakDuration);
    }

    private void resetSettings() {
        // Set durations to default values
        durations.setWorkDuration(20);
        durations.setShortBreakDuration(5);
        durations.setLongBreakDuration(15);

        // Update EditTexts with default values
        workDurationEditText.setText(String.valueOf(durations.getWorkDuration()));
        shortBreakDurationEditText.setText(String.valueOf(durations.getShortBreakDuration()));
        longBreakDurationEditText.setText(String.valueOf(durations.getLongBreakDuration()));
    }
}
