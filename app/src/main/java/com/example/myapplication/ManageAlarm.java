package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Random;

import static android.content.Context.ALARM_SERVICE;

public class ManageAlarm {
    int id;
    int hour;
    int minute;
    int sec;
    long milisec;
    Context context;


    public ManageAlarm(){

    }

    public ManageAlarm(int id,int hour,int min,int sec){
        this.id=id;
        this.hour=hour;
        this.minute=min;
        this.sec=sec;
    }

    int SetBajna(int hour, int min, int sec, Context cnt)
    {
        Random rand=new Random();
        this.id=rand.nextInt(1000);
        this.context=cnt;
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,hour);
        cal.set(Calendar.MINUTE,min);
        cal.set(Calendar.SECOND,sec);
        if((cal.before(Calendar.getInstance())))
        {
            cal.add(Calendar.DATE,1);
        }

        milisec=cal.getTimeInMillis();

        AlarmManager alarmManager=(AlarmManager)cnt.getSystemService(ALARM_SERVICE);
        Intent myIntent=new Intent(cnt,MyBroadcastReciver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(cnt,id,myIntent,0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,milisec,AlarmManager.INTERVAL_DAY,pendingIntent);
        Toast.makeText(cnt,"Alarm Set",Toast.LENGTH_LONG).show();
        return id;
    }


}
