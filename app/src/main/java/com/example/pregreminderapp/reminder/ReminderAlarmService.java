package com.example.pregreminderapp.reminder;

import android.app.IntentService;
import android.content.Intent;

public class ReminderAlarmService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ReminderAlarmService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent( Intent intent) {

    }
}
