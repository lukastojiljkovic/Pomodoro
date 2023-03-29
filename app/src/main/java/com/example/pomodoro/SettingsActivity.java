package com.example.pomodoro;

import android.content.SharedPreferences;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize UI elements
        workDurationEditText = findViewById(R.id.workDurationEditText);
        shortBreakDurationEditText = findViewById(R.id.shortBreakDurationEditText);
        longBreakDurationEditText = findViewById(R.id.longBreakDurationEditText);
        saveButton = findViewById(R.id.saveButton);

        // Set up button click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save the settings
                saveSettings();
            }
        });
    }

    private void saveSettings() {
        // Get the values entered by the user
        String workDurationString = workDurationEditText.getText().toString();
        String shortBreakDurationString = shortBreakDurationEditText.getText().toString();
        String longBreakDurationString = longBreakDurationEditText.getText().toString();

        // Convert the values to integers
        int workDuration = Integer.parseInt(workDurationString);
        int shortBreakDuration = Integer.parseInt(shortBreakDurationString);
        int longBreakDuration = Integer.parseInt(longBreakDurationString);

        // Save the settings using SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("PomodoroSettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("workDuration", workDuration);
        editor.putInt("shortBreakDuration", shortBreakDuration);
        editor.putInt("longBreakDuration", longBreakDuration);
        editor.apply();

        // Close the settings activity
        finish();
    }
}
