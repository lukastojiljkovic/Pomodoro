package com.example.pomodoro;

import android.os.Bundle;
import android.view.View;
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
    private int workDuration = 20;
    private int shortBreakDuration = 5;
    private int longBreakDuration = 15;

    // Declare default values for durations
    private static final int DEFAULT_WORK_DURATION = 20;
    private static final int DEFAULT_SHORT_BREAK_DURATION = 5;
    private static final int DEFAULT_LONG_BREAK_DURATION = 15;

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
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save settings
                saveSettings();
            }
        });
        // resets the durations to default
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Reset settings to default values
                resetSettings();
            }
        });
    }

    private void saveSettings() {
        // Get durations from EditTexts and convert to int
        workDuration = Integer.parseInt(workDurationEditText.getText().toString());
        shortBreakDuration = Integer.parseInt(shortBreakDurationEditText.getText().toString());
        longBreakDuration = Integer.parseInt(longBreakDurationEditText.getText().toString());
    }

    private void resetSettings() {
        // Set durations to default values
        workDuration = DEFAULT_WORK_DURATION;
        shortBreakDuration = DEFAULT_SHORT_BREAK_DURATION;
        longBreakDuration = DEFAULT_LONG_BREAK_DURATION;

        // Update EditTexts with default values
        workDurationEditText.setText(String.valueOf(DEFAULT_WORK_DURATION));
        shortBreakDurationEditText.setText(String.valueOf(DEFAULT_SHORT_BREAK_DURATION));
        longBreakDurationEditText.setText(String.valueOf(DEFAULT_LONG_BREAK_DURATION));
    }

    public int getWorkDuration() {
        return workDuration;
    }

    public int getShortBreakDuration() {
        return shortBreakDuration;
    }

    public int getLongBreakDuration() {
        return longBreakDuration;
    }
}
