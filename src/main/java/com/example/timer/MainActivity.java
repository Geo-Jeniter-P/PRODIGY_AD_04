package com.example.timer;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView timerText;
    private Button startButton, pauseButton, resetButton;

    private long startTime = 0L;
    private long timeInMilliseconds = 0L;
    private long updateTime = 0L;
    private long timeSwap = 0L;
    private int seconds = 0;
    private int minutes = 0;
    private int milliseconds = 0;
    private boolean running;

    private Handler handler;
    private final Runnable runnable = new Runnable() {
        public void run() {
            milliseconds = (int) (SystemClock.uptimeMillis() - startTime);
            updateTime = timeSwap + milliseconds;
            seconds = (int) (updateTime / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            milliseconds = (int) (updateTime % 1000);

            String formattedTime = String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
            timerText.setText(formattedTime);

            handler.postDelayed(this, 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerText = findViewById(R.id.timer_text);
        startButton = findViewById(R.id.button_start);
        pauseButton = findViewById(R.id.button_pause);
        resetButton = findViewById(R.id.button_reset);

        handler = new Handler();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }

    public void startTimer() {
        startTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);
        running = true;
    }

    public void pauseTimer() {
        timeSwap += timeInMilliseconds;
        handler.removeCallbacks(runnable);
        running = false;
    }

    public void resetTimer() {
        timeInMilliseconds = 0L;
        startTime = 0L;
        updateTime = 0L;
        timeSwap = 0L;
        seconds = 0;
        minutes = 0;
        milliseconds = 0;
        timerText.setText("00:00:00");
    }
}