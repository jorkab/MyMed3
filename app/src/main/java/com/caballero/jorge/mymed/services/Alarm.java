package com.caballero.jorge.mymed.services;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import com.caballero.jorge.mymed.R;
import com.caballero.jorge.mymed.data.MyPills_Row;
import com.caballero.jorge.mymed.db.MedDataBDAdapter;
import com.caballero.jorge.mymed.db.MedDataSPAdapter;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;

/**
 * Created by Jorge on 07/04/2016.
 */
public class Alarm extends BroadcastReceiver
{
    private Context context;
    private MedDataBDAdapter dbAdapter;
    public final static String[] dose={"breakfast","lunch","dinner","sleep"};
    MedDataSPAdapter medDataSPAdapter;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        this.context=context;
        dbAdapter=new MedDataBDAdapter(context);
        LinkedList<MyPills_Row>list=new LinkedList<>();
        try
        {
            list=dbAdapter.getMyPillsRows();
        }
        catch (SQLException e)
        {
            Log.e(e.getClass().getName(), e.getMessage(), e);
        }
        catch (NullPointerException e)
        {
            Log.e(e.getClass().getName(),e.getMessage(),e);
        }

        for (MyPills_Row row: list)
        {
            String dose=intent.getExtras().getString("dose");
            switch (dose)
            {
                case "breakfast":
                    if(row.getBreakfast())
                    {
                        checkDuration(row);
                    }
                    break;
                case "lunch":
                    if(row.getLunch())
                    {
                        checkDuration(row);
                    }
                    break;
                case "dinner":
                    if(row.getDinner())
                    {
                        checkDuration(row);
                    }
                    break;
                case "sleep":
                    if(row.getSleep())
                    {
                        checkDuration(row);
                    }
                    break;
                default:break;
            }
        }
    }

    //Comprueba si la fecha actual es menor a la de la alarma, lanza una notificacion si es true o borra el registro en la bd si es false

    private void checkDuration(MyPills_Row row)
    {
        Calendar actualDate=Calendar.getInstance();
        actualDate.setTimeInMillis(System.currentTimeMillis());
        Calendar rowDate=Calendar.getInstance();
        //La cadena de fecha tiene formato dd-mm-yyyy
        String[] splitDate=row.getDuration().split("-");
        //El mes de la fecha guardada en el servidor esta en base 1 y android trabaja en base 0
        rowDate.set(Integer.parseInt(splitDate[2]),Integer.parseInt(splitDate[1])-1,Integer.parseInt(splitDate[0]));
        actualDate.getTimeInMillis();
        rowDate.getTimeInMillis();

        if(actualDate.getTimeInMillis()>(rowDate.getTimeInMillis()))
        {
            try
            {
                dbAdapter.deleteMyPillsRow(row.getId());
            }
            catch (SQLException e)
            {
                Log.e(e.getClass().getName(),e.getMessage(),e);
            }
        }
        else
        {
            notification(row);
        }

    }

    //Construye una notificacion en base a una fila

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void notification(MyPills_Row row)
    {
        NotificationManager notManager=(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification notification=new Notification.Builder(context)
                .setContentTitle("MyPills")
                .setContentText(row.getName())
                .setSmallIcon(R.drawable.pill)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .build();

        notManager.notify((int)Math.random(),notification);
    }
    //Crea los intents de las alarmas

    public void setAlarms(Context context)
    {
        //cambiar el interval para las pruebas 1000*60*60*24
        int interval=1000*60*60*24;
        Calendar[] date=new Calendar[4];
        medDataSPAdapter=new MedDataSPAdapter(context);

        //Busca en el archivo de shared preferences si existen datos de las alarmas si no existe aplica valores por defecto.

        for(int i=0;i<dose.length;i++)
        {
            String hour= medDataSPAdapter.getValue(dose[i]);
            String[] splittedhour=hour.split(":");
            Calendar c=Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY,Integer.valueOf(splittedhour[0]));
            c.set(Calendar.MINUTE,Integer.valueOf(splittedhour[1]));
            date[i]=c;
        }

        //Crea un intent para cada alarma con los datos obtenidos del archivo de sharedpreferences.

        for(int i=0;i<dose.length;i++)
        {
            Intent intent=new Intent(context,Alarm.class);
            //Flag que permite arrancar el servicio de alarmas desde el estado Stopped
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            intent.putExtra("dose", dose[i]);
            PendingIntent pendingIntent= PendingIntent.getBroadcast(context,i,intent,0);
            AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,date[i].getTimeInMillis(),interval,pendingIntent);
        }
    }
}
