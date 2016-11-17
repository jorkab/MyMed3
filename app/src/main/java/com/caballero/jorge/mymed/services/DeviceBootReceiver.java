package com.caballero.jorge.mymed.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.caballero.jorge.mymed.db.MedDataSPAdapter;

import java.util.Calendar;

/**
 * Created by Jorge on 07/04/2016.
 */
public class DeviceBootReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Alarm alarm=new Alarm();
            alarm.setAlarms(context);
        }
    }
}
