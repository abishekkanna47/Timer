package com.example.timer;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editTextNumber;
    private Button startButton;
    private TextView countdownTextView;
    private CountDownTimer countDownTimer;

    // Create a BroadcastReceiver to handle the alarm when the timer completes
    private BroadcastReceiver alarmReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Handle the alarm/notification here
            countdownTextView.setText("Timer completed!");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumber = findViewById(R.id.editTextNumber);
        startButton = findViewById(R.id.startButton);
        countdownTextView = findViewById(R.id.countdownTextView);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input number from the EditText
                int inputNumber = Integer.parseInt(editTextNumber.getText().toString());
                setupAlarm(inputNumber);

                // Start the countdown timer
                startCountdown(inputNumber);

                // Set up the AlarmManager to notify the user when the timer completes
               // setupAlarm(inputNumber);
            }
        });
    }

    private void startCountdown(int inputNumber) {
        countDownTimer = new CountDownTimer(inputNumber * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                countdownTextView.setText("Time remaining: " + secondsRemaining + " seconds");
            }

            @Override
            public void onFinish() {
                countdownTextView.setText("Timer completed!");
            }
        };

        countDownTimer.start();
    }

    private void setupAlarm(int inputNumber) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Create an intent for the BroadcastReceiver
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        // Calculate the alarm trigger time (in milliseconds)
        long triggerTime = System.currentTimeMillis() + (inputNumber * 1000);

        // Set the alarm
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register the BroadcastReceiver to receive the alarm
        registerReceiver(alarmReceiver, new IntentFilter(AlarmReceiver.ACTION_ALARM));
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the BroadcastReceiver when the activity is paused
        unregisterReceiver(alarmReceiver);
    }
}
