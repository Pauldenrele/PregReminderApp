package com.example.pregreminderapp.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Build;

import com.example.pregreminderapp.data.AlarmReminderProvider;

public class AlarmScheduler  {

    public void setAlarm (Context context , long alarmTime , Uri reminderTask){
        AlarmManager alarmManager = AlarmManagerProvider.getAlarmManager(context);
        PendingIntent operation =ReminderAlarmService.getReminderPendingIntent(context , reminderTask);

        if(Build.VERSION.SDK_INT >=23){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP , alarmTime , operation);

        }else if(Build.VERSION.SDK_INT >=19){
         alarmManager.setExact(AlarmManager.RTC_WAKEUP , alarmTime , operation);
        }
        else{
            alarmManager.set(AlarmManager.RTC_WAKEUP , alarmTime , operation);
        }
    }
    public void setRepeatAlarm(Context context , long alarmTime , Uri reminderTask , long RepeatTime){
        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        PendingIntent operation = ReminderAlarmService.getReminderPendingIntent(context , reminderTask);

        manager.setRepeating(AlarmManager.RTC_WAKEUP ,alarmTime ,RepeatTime,  operation );



    }

}
