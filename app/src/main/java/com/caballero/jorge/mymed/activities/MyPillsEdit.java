package com.caballero.jorge.mymed.activities;

import android.app.DatePickerDialog;
import android.view.View;

import com.caballero.jorge.mymed.R;

import java.util.Calendar;

/**
 * Created by Jorge on 05/04/2016.
 */
public class MyPillsEdit extends MyPillsDialog
{
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.accept_button:
                data.putExtra("id",extra.getLong("id"));
                data.putExtra("name",med_name.getText().toString());
                data.putExtra("duration",datePicker.getText().toString());
                data.putExtra("breakfast",med_breakfast.isChecked());
                data.putExtra("lunch",med_lunch.isChecked());
                data.putExtra("dinner",med_dinner.isChecked());
                data.putExtra("sleep",med_sleep.isChecked());
                setResult(RESULT_OK, data);
                this.finish();
                break;
            case R.id.cancel_button:
                this.finish();
                break;
            case R.id.buttonDatePicker:
                showDatePicker();
                break;
            default:break;

        }
    }
}
