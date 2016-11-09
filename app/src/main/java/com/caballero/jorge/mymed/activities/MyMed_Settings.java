package com.caballero.jorge.mymed.activities;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;

import com.caballero.jorge.mymed.R;
import com.caballero.jorge.mymed.db.MedDataSPAdapter;

import java.util.ArrayList;

/**
 * Created by Jorge on 11/05/2016.
 */
public class MyMed_Settings extends Activity implements View.OnClickListener{

    private Bundle extra;
    private Intent data;
    private ListView list;
    private ArrayAdapter<String> listAdapter;
    private MedDataSPAdapter SPAdapter;
    private int hour;
    private int minute;
    private AdapterView.AdapterContextMenuInfo info;
    private String[] dose=MyMed.dose;
    private Button accept;
    private Button cancel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_med_settings);

        extra=getIntent().getExtras();
        if(extra==null)return;
        data=new Intent();

        accept=(Button)findViewById(R.id.accept_button);
        accept.setOnClickListener(this);
        cancel=(Button)findViewById(R.id.cancel_button);
        cancel.setOnClickListener(this);

        SPAdapter=new MedDataSPAdapter(this);

        list = (ListView) findViewById(R.id.settingsList);
        loadData();
        registerForContextMenu(list);
    }

    //Carga los valores del archivo sharedpreferences en el listado

    private void loadData()
    {
        ArrayList<String> hourList = new ArrayList<>();
        for (int i = 0; i < dose.length; i++) {
            hourList.add(i,SPAdapter.getValue(dose[i]));
        }
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hourList);
        list.setAdapter(listAdapter);
    }

    //Listener del menu de pulsacion larga sobre de las filas del listado, lanza un reloj para editar la fila

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        try {
            info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            switch (item.getItemId()) {
                case R.id.edit:
                    String time= SPAdapter.getValue(dose[info.position]);
                    String [] splitTime=time.split(":");
                    hour=Integer.valueOf(splitTime[0]);
                    minute=Integer.valueOf(splitTime[1]);
                    TimePickerDialog datePickerDialog = new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener(){
                        @Override
                        public void onTimeSet(TimePicker timePicker,int selectedHour,int selectedMinute){
                            String time;
                            if(minute<10)
                            {
                                time=new String(String.valueOf(selectedHour)+":0"+String.valueOf(selectedMinute));
                            }
                            else
                            {
                                time=new String(String.valueOf(selectedHour)+":"+String.valueOf(selectedMinute));
                            }
                            SPAdapter.insertValue(dose[info.position],time);
                            loadData();
                        }
                    },hour, minute,true);
                    datePickerDialog.show();
                    data.putExtra("edit",true);
                    break;
                default:
                    return super.onContextItemSelected(item);
            }
        } catch (ClassCastException e) {
            Log.e(e.getClass().getName(), e.getMessage(), e);
            return false;
        }
        return true;
    }

    //Listener de la pulsacion larga sobre un elemento del listado

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_onlongclick_settings, menu);
    }
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.accept_button:
                setResult(RESULT_OK,data);
                this.finish();
                break;
            case R.id.cancel_button:

                setResult(RESULT_CANCELED);
                this.finish();
                break;
        }
    }
}

