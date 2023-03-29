package com.example.pomodoro;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    // Declare variables for UI elements
    private TextView sessionNameTextView;
    private TextView timeRemainingTextView;
    private ProgressBar progressBar;
    private ImageButton playButton;
    private ImageButton pauseButton;
    private ImageButton stopButton;
    private ImageButton resetButton;

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
        resetButton = findViewById(R.id.resetButton);

        // Initialize timer logic
        Context context = getApplicationContext();
        pomodoroTimer = new PomodoroTimer(context);

        // Set up button click listeners
        resetButton.setOnClickListener(view -> {
            // reset the number of completed pomodoros
            resetCompletedPomodoros(MainActivity.this);
        });

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
                runOnUiThread(this::updateUI);

                // Sleep for a short time to avoid using too much CPU
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void resetCompletedPomodoros(Context context) {
        try {
            String filename = "completedPomodoros.txt";
            String fileContents = "0";

            // Write to the file
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(fileContents.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Update the UI based on the current state of the timer
    private void updateUI() {
        // Update session name text view
        sessionNameTextView.setText(pomodoroTimer.getCurrentSessionName());

        // Update time remaining text view
        timeRemainingTextView.setText(pomodoroTimer.getTimeRemaining());

        // Update progress bar
        progressBar.setProgress(pomodoroTimer.getProgress());

        // Update gradient overlay based on current session
        View gradientOverlay = findViewById(R.id.gradient_overlay);
        Drawable gradientDrawable = getColorForSession(pomodoroTimer.getCurrentSession());
        gradientOverlay.setBackground(gradientDrawable);
        gradientOverlay.setAlpha(0.5f); // Set alpha value to control transparency of overlay

        // Read the value of completedPomodoros from completedPomodoros.txt
        int completedPomodoros = 0;
        try {
            FileInputStream inputStream = openFileInput("completedPomodoros.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            if (line != null) {
                completedPomodoros = Integer.parseInt(line);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Update completed pomodoros text view
        TextView completedPomodorosTextView = findViewById(R.id.completedPomodorosTextView);
        completedPomodorosTextView.setText(String.valueOf(completedPomodoros));
    }


    // Get the appropriate background color for the given session
    private Drawable getColorForSession(PomodoroTimer.Session session) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        switch (session) {
            case WORK:
                drawable.setColors(new int[]{Color.argb(150, 0, 155, 0), Color.argb(25, 155, 255, 0)});
                break;
            case SHORT_BREAK:
                drawable.setColors(new int[]{Color.argb(150, 0, 120, 240), Color.argb(25, 32, 50, 150)});
                break;
            case LONG_BREAK:
                drawable.setColors(new int[]{Color.argb(150, 230, 15, 25), Color.argb(25, 180, 45, 23)});
                break;
            default:
                drawable.setColor(Color.TRANSPARENT);
                break;
        }
        return drawable;
    }
}
