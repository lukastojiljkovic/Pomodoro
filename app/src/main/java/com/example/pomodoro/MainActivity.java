package com.example.pomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // Declare variables for UI elements
    private TextView sessionNameTextView;
    private TextView timeRemainingTextView;
    private ProgressBar progressBar;
    private Button playButton;
    private Button pauseButton;
    private Button stopButton;

    // Declare variables for timer logic
    private PomodoroTimer pomodoroTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        sessionNameTextView = findViewById(R.id.sessionNameTextView);
        timeRemainingTextView = findViewById(R.id.timeRemainingTextView);
        progressBar = findViewById(R.id.progressBar);
        playButton = findViewById(R.id.playButton);
        pauseButton = findViewById(R.id.pauseButton);
        stopButton = findViewById(R.id.stopButton);

        // Initialize timer logic
        pomodoroTimer = new PomodoroTimer();

        // Set up button click listeners
        playButton.setOnClickListener(view -> {
            // Start or resume the timer
            pomodoroTimer.start();
        });

        pauseButton.setOnClickListener(view -> {
            // Pause the timer
            pomodoroTimer.pause();
        });

        stopButton.setOnClickListener(view -> {
            // Stop and reset the timer
            pomodoroTimer.stop();
        });

        // Create a new thread to update the UI
        new Thread(() -> {
            while (true) {
                // Update the UI on the main thread
                runOnUiThread(() -> updateUI());

                // Sleep for a short time to avoid using too much CPU
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // Update the UI based on the current state of the timer
    private void updateUI() {
        // Update session name text view
        sessionNameTextView.setText(pomodoroTimer.getCurrentSessionName());

        // Update time remaining text view
        timeRemainingTextView.setText(pomodoroTimer.getTimeRemaining());

        // Update progress bar
        progressBar.setProgress(pomodoroTimer.getProgress());

        // Update background color based on current session
        Drawable backgroundColor = getColorForSession(pomodoroTimer.getCurrentSession());
        getWindow().getDecorView().setBackground(backgroundColor);
    }

    // Get the appropriate background color for the given session
    private Drawable getColorForSession(PomodoroTimer.Session session) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        switch (session) {
            case WORK:
                drawable.setColors(new int[]{Color.rgb(0, 155, 0), Color.rgb(155, 255, 0)});
                break;
            case SHORT_BREAK:
                drawable.setColors(new int[]{Color.rgb(0, 120, 240), Color.rgb(32, 50, 150)});
                break;
            case LONG_BREAK:
                drawable.setColors(new int[]{Color.rgb(230, 15, 25), Color.rgb(180, 45, 23)});
                break;
            default:
                drawable.setColor(Color.WHITE);
                break;
        }
        return drawable;
    }
}
