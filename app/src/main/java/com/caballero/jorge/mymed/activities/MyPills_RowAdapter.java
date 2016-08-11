package com.caballero.jorge.mymed.activities;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.caballero.jorge.mymed.R;
import com.caballero.jorge.mymed.data.MyPills_Row;

/**
 * Created by Jorge on 01/04/2016.
 */
public class MyPills_RowAdapter extends ArrayAdapter<MyPills_Row>
{
    //Atributos
    Activity activity;

    //Constructor
    public MyPills_RowAdapter(Activity activity,int resource)
    {
        super(activity,resource);
        this.activity=activity;
    }

    //Metodos
    public View getView(final int position,View convertView,ViewGroup parent)
    {
        View item=convertView;
        VistaTag vistaTag;

        if(item==null)
        {
            LayoutInflater inflater=activity.getLayoutInflater();
            item=inflater.inflate(R.layout.my_pills_row,null);

            vistaTag=new VistaTag();
            vistaTag.name=(TextView)item.findViewById(R.id.name);
            vistaTag.duration=(TextView)item.findViewById(R.id.duration);
            vistaTag.breakfast=(CheckBox)item.findViewById(R.id.breakfast);
            vistaTag.lunch=(CheckBox)item.findViewById(R.id.lunch);
            vistaTag.dinner=(CheckBox)item.findViewById(R.id.dinner);
            vistaTag.sleep=(CheckBox)item.findViewById(R.id.sleep);

            item.setTag(vistaTag);
        }
        else
        {
            vistaTag=(VistaTag) item.getTag();
        }
        vistaTag.name.setText(this.getItem(position).getName());
        vistaTag.duration.setText(String.valueOf(this.getItem(position).getDuration()));
        vistaTag.breakfast.setChecked(this.getItem(position).getBreakfast());
        vistaTag.lunch.setChecked(this.getItem(position).getLunch());
        vistaTag.dinner.setChecked(this.getItem(position).getDinner());
        vistaTag.sleep.setChecked(this.getItem(position).getSleep());

        return item;
    }
}
class VistaTag
{
    TextView name;
    TextView duration;
    CheckBox breakfast;
    CheckBox lunch;
    CheckBox dinner;
    CheckBox sleep;
}
