package com.caballero.jorge.mymed.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.caballero.jorge.mymed.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jorge on 05/04/2016.
 */
public abstract class MyPillsDialog extends Activity implements View.OnClickListener{

    protected Bundle extra;
    protected Button accept;
    protected Button cancel;
    protected EditText med_name;
    protected TextView datePicker;
    protected ImageButton buttonDatePicker;
    protected CheckBox med_breakfast;
    protected CheckBox med_lunch;
    protected CheckBox med_dinner;
    protected CheckBox med_sleep;
    protected Intent data;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pills_dialog);

        extra=getIntent().getExtras();
        if(extra==null)return;
        data=new Intent();

        accept=(Button)findViewById(R.id.accept_button);
        cancel=(Button)findViewById(R.id.cancel_button);
        med_name=(EditText)findViewById(R.id.med_name);
        med_name.setText(extra.getString("name"));
        datePicker=(TextView) findViewById(R.id.datePicker);
        if(extra.getString("duration").equals(""))
        {
            Calendar c=Calendar.getInstance();
            datePicker.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH))+"-"+String.valueOf(c.get(Calendar.MONTH)+1)+"-"+String.valueOf(c.get(Calendar.YEAR)));
        }
        else
        {
            String[] date=extra.getString("duration").split("-");
            datePicker.setText(String.valueOf(date[0])+"-"+String.valueOf(date[1])+"-"+String.valueOf(date[2]));
        }
        buttonDatePicker=(ImageButton)findViewById(R.id.buttonDatePicker);
        med_breakfast=(CheckBox)findViewById(R.id.breakfast);
        med_breakfast.setChecked(extra.getBoolean("breakfast"));
        med_lunch=(CheckBox)findViewById(R.id.lunch);
        med_lunch.setChecked(extra.getBoolean("lunch"));
        med_dinner=(CheckBox)findViewById(R.id.dinner);
        med_dinner.setChecked(extra.getBoolean("dinner"));
        med_sleep=(CheckBox)findViewById(R.id.sleep);
        med_sleep.setChecked(extra.getBoolean("sleep"));


        accept.setOnClickListener(this);
        cancel.setOnClickListener(this);
        buttonDatePicker.setOnClickListener(this);

    }

    //Muestra un DatePickerDialog al hacer click sobre el icono calendario

    @Override
    public abstract void onClick(View v);

    protected DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int month, int day) {
            datePicker.setText(String.valueOf(day)+"-"+String.valueOf(month+1)+"-"+String.valueOf(year));
        }
    };
    protected void showDatePicker()
    {
        DatePickerDialog datePickerDialog;
        if(extra.getString("duration").equals(""))
        {
            Calendar c=Calendar.getInstance();
            datePickerDialog=new DatePickerDialog(this,mDateSetListener,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        }
        else
        {
            String[] splitDate=datePicker.getText().toString().split("-");
            datePickerDialog=new DatePickerDialog(this,mDateSetListener,Integer.parseInt(splitDate[2]),Integer.parseInt(splitDate[1])-1,Integer.parseInt(splitDate[0]));
        }
        datePickerDialog.show();
    }
}
