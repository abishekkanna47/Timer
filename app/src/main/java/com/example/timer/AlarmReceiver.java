package com.example.timer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String ACTION_ALARM = "com.example.timerdemo.ACTION_ALARM";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(ACTION_ALARM)) {
            // Handle the alarm/notification here
            Toast.makeText(context, "Timer completed!", Toast.LENGTH_SHORT).show();
        }
    }
}
