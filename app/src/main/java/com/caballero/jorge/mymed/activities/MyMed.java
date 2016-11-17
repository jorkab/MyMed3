package com.caballero.jorge.mymed.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


import com.caballero.jorge.mymed.R;
import com.caballero.jorge.mymed.db.MedDataSPAdapter;
import com.caballero.jorge.mymed.services.*;

public class MyMed extends Activity implements OnClickListener  {

    private Button myPills;
    private Button myBPresure;
    private Button myBSugar;
    private Alarm alarm;
    public final static String[] dose={"breakfast","lunch","dinner","sleep"};
    private final static int COD_SETTINGS_EDIT=1;
    MedDataSPAdapter medDataSPAdapter;

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
        //Realizar comprobacion de si existe la primera key para en caso negativo inicializar el archivo con los valores por defecto.
        alarm=new Alarm();
        medDataSPAdapter=new MedDataSPAdapter(this);
        if(!medDataSPAdapter.isKey(dose[0])){
            for(int i=0;i<dose.length;i++) {
                String[] defaultValues={"7:00","14:00","20:00","22:00"};
                medDataSPAdapter.insertValue(dose[i],defaultValues[i]);
            }
            alarm.setAlarms(this);
        }

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
                        alarm.setAlarms(this);
                    }
                }
                break;
            default:
                break;
        }
    }
}
