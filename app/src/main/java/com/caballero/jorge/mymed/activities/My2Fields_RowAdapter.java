package com.caballero.jorge.mymed.activities;

import android.app.Activity;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.caballero.jorge.mymed.R;
import com.caballero.jorge.mymed.data.My2fieldsRow;


/**
 * Created by P1047 on 11/08/2016.
 */
public class My2Fields_RowAdapter extends ArrayAdapter<My2fieldsRow>
{
    //Atributos
    private Activity activity;

    //Constructor
    public My2Fields_RowAdapter(Activity activity, int resource)
    {
        super(activity,resource);
        this.activity=activity;
    }

    //Metodos
    public View getView(final int position,View convertView,ViewGroup parent)
    {
        View item=convertView;
        Row2fields row;

        if(item==null)
        {
            LayoutInflater inflater=activity.getLayoutInflater();
            item=inflater.inflate(R.layout.my_2fields_custom_row,null);

            row=new Row2fields();
            row.date=(TextView)item.findViewById(R.id.date);
            row.value=(TextView)item.findViewById(R.id.value);

            item.setTag(row);
        }
        else
        {
            row=(Row2fields) item.getTag();
        }
        row.date.setText(this.getItem(position).getDate());
        row.value.setText(this.getItem(position).getValue());

        return item;
    }
}
class Row2fields
{
    TextView date;
    TextView value;
}

