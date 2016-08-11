package com.caballero.jorge.mymed.activities;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.caballero.jorge.mymed.R;
import com.caballero.jorge.mymed.data.My2fieldsRow;
import com.caballero.jorge.mymed.db.MedDataBDAdapter;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;

/**
 * Created by P1047 on 01/08/2016.
 */
public class MyBPressure extends ListActivity implements View.OnClickListener{
    private Button newButton;
    private MedDataBDAdapter bdAdapter =new MedDataBDAdapter(this);
    private LinkedList<My2fieldsRow> list;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pills);

        newButton=(Button)findViewById(R.id.myPills_new_button);
        newButton.setOnClickListener(this);

        ListView listView=getListView();
        try
        {
            list=bdAdapter.getMyPressureRows();
        }
        catch (SQLException e)
        {
            Log.e(e.getClass().getName(), e.getMessage(), e);
        }
        My2FieldsCustomAdapter adapter=new My2FieldsCustomAdapter(this,R.layout.my_2fields_custom_row,list);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.myPills_new_button:
                AlertDialog.Builder alert=new AlertDialog.Builder(this);
                final EditText input=new EditText(this);
                alert.setTitle(R.string.new_button)
                        .setMessage(R.string.dialog_new_pressure)
                        .setView(input)
                        .setPositiveButton(R.string.accept_button,new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                //anadir un registro y actualizar el listado
                                String value=input.getText().toString().trim();
                                Calendar c= Calendar.getInstance();
                                String date=String.valueOf(c.get(Calendar.DAY_OF_MONTH)+"-"+String.valueOf(Calendar.MONTH+1)+"-"+String.valueOf(Calendar.YEAR));
                                try
                                {
                                    bdAdapter.insertMyPressureRow(date,value);
                                }
                                catch (SQLException e)
                                {
                                    Log.e(e.getClass().getName(), e.getMessage(), e);
                                }
                            }
                        })
                        .setNegativeButton(R.string.cancel_button,new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                dialog.cancel();
                            }
                        });
                alert.show();
        }
    }
}
