package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.widget.Toast;

public class MyBroadcastReciver extends BroadcastReceiver {
    MediaPlayer mp;
    @Override
    public void onReceive(Context context, Intent intent) {
        mp=MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        mp.start();
        Toast.makeText(context,"Medicine time!",Toast.LENGTH_LONG).show();
        mp.release();
    }


}
