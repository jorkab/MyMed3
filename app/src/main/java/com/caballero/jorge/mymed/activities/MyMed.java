package com.caballero.jorge.mymed.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


import com.caballero.jorge.mymed.R;
import com.caballero.jorge.mymed.db.MedDataSPAdapter;
import com.caballero.jorge.mymed.services.*;

import java.util.Calendar;

public class MyMed extends Activity implements OnClickListener  {

    private Button myPills;
    private Button myBPresure;
    private Button myBSugar;
    public final static String[] dose={"breakfast","lunch","dinner","sleep"};
    private final static int COD_SETTINGS_EDIT=1;
    private SharedPreferences sharedPreferences;
    MedDataSPAdapter medDataSPAdapter=new MedDataSPAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_med);

        myPills=(Button)findViewById(R.id.myPills);
        myPills.setOnClickListener(this);
        myBPresure=(Button)findViewById(R.id.myBpresure);
        myBPresure.setOnClickListener(this);
        myBSugar=(Button)findViewById(R.id.myBsugar);
        myBSugar.setOnClickListener(this);
        sharedPreferences=getSharedPreferences(MedDataSPAdapter.MyPREFS,Context.MODE_PRIVATE);
        /*try{
            sharedPreferences.contains(dose[0]);
        }
        catch (NullPointerException ex){
            String[] defaultValues={"7:00","14:00","20:00","22:00"};
            for(int i=0;i<dose.length-1;i++) {
                sharedPreferences.edit().putString(dose[i], defaultValues[i]);
                sharedPreferences.edit().commit();
            }
        }*/
        String[] defaultValues={"7:00","14:00","20:00","22:00"};
        for(int i=0;i<dose.length-1;i++) {
            medDataSPAdapter.insertValue(dose[i],defaultValues[i]);
        }
        this.setAlarms();

    }

    //Listener de los botones de la aplicacion, lanza los intents para cada boton.

    @Override
    public void onClick(View view)
    {
        Intent intent;
        switch (view.getId())
        {
            case R.id.myPills:
                intent=new Intent(this,MyPills.class);
                startActivity(intent);
                break;
            case R.id.myBpresure:
                intent=new Intent(this,MyBPressure.class);
                startActivity(intent);
                break;
            case R.id.myBsugar:
                intent=new Intent(this,MyBSugar.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    //Inflar el menu de opciones '...'

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_my_med, menu);
        return true;
    }

    //Lanza el intent de opciones tras hacer click en en el menu de opciones, si hay cambios devuelve true sino false

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
       int id=item.getItemId();

        if(id==R.id.action_settings)
        {
            Intent intent=new Intent(this,MyMed_Settings.class);
            intent.putExtra("edit",false);
            startActivityForResult(intent,COD_SETTINGS_EDIT);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode,int resultCode, Intent data) {
        switch (requestCode) {
            case COD_SETTINGS_EDIT:
                if (resultCode == RESULT_OK) {
                    Boolean edit=data.getExtras().getBoolean("edit");
                    if(edit)
                    {
                        this.setAlarms();
                    }
                }
                break;
            default:
                break;
        }
    }

    //Crea los intents de las alarmas

    public void setAlarms()
    {
        //cambiar el interval para las pruebas 1000*60*60*24
        int interval=1000*60*24;
        Calendar[] date=new Calendar[4];


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
            Intent intent=new Intent(this,Alarm.class);
            //Flag que permite arrancar el servicio de alarmas desde el estado Stopped
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            intent.putExtra("dose", dose[i]);
            PendingIntent pendingIntent= PendingIntent.getBroadcast(this.getApplicationContext(),i,intent,0);
            AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,date[i].getTimeInMillis(),interval,pendingIntent);
        }
    }
}
