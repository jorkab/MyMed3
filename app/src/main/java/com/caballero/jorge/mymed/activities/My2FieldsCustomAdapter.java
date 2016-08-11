package com.caballero.jorge.mymed.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.caballero.jorge.mymed.R;
import com.caballero.jorge.mymed.data.My2fieldsRow;

import java.util.LinkedList;

/**
 * Created by P1047 on 11/08/2016.
 */
public class My2FieldsCustomAdapter extends ArrayAdapter<String>
{
    private final Context context;
    private final LinkedList<My2fieldsRow> values;

    public My2FieldsCustomAdapter(Context context,int textViewResourceId, LinkedList<My2fieldsRow> values) {
        super(context,textViewResourceId, values.size());
        this.context=context;
        this.values=values;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.my_2fields_custom_row,parent,false);
        TextView textView;
        textView=(TextView)rowView.findViewById(R.id.date);
        textView.setText(values.get(position).getDate());
        textView=(TextView)rowView.findViewById(R.id.value);
        textView.setText(values.get(position).getValue());

        return rowView;
    }
}
