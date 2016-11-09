package com.caballero.jorge.mymed.activities;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ListView;

import com.caballero.jorge.mymed.R;
import com.caballero.jorge.mymed.data.My2fieldsRow;
import com.caballero.jorge.mymed.data.MyPills_Row;
import com.caballero.jorge.mymed.db.MedDataBDAdapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

/**
 * Created by P1047 on 01/08/2016.
 */
public class MyBPressure extends ListActivity implements View.OnClickListener{
    private Button newButton;
    private My2Fields_RowAdapter adapter;
    private MedDataBDAdapter dbAdapter =new MedDataBDAdapter(this);
    private LinkedList<My2fieldsRow> list;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pills);

        newButton=(Button)findViewById(R.id.myPills_new_button);
        newButton.setOnClickListener(this);

        ListView listView=getListView();
        adapter=new My2Fields_RowAdapter(this,R.layout.activity_my_pills);
        listView.setAdapter(adapter);
        //Listener para eventos longclick en el listado.
        registerForContextMenu(listView);
        loadData();

    }

    public void loadData()
    {
        adapter.clear();
        try{
            list=dbAdapter.getMyPressureRows();
            for(My2fieldsRow row:list){
                adapter.add(row);
            }
        }
        catch (SQLException e){
            Log.e(e.getClass().getName(), e.getMessage(), e);
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu,v,menuInfo);

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_onlongclick_mypills, menu);
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
                                String date=String.valueOf(c.get(Calendar.DAY_OF_MONTH))+"-"+String.valueOf(c.get(Calendar.MONTH)+1)+"-"+String.valueOf(c.get(Calendar.YEAR));
                                try
                                {
                                    dbAdapter.insertMyPressureRow(date,value);
                                }
                                catch (SQLException e)
                                {
                                    Log.e(e.getClass().getName(), e.getMessage(), e);
                                }
                                loadData();
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

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info;
        try
        {
            info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
            switch (item.getItemId())
            {
                case R.id.edit:
                    AlertDialog.Builder alert=new AlertDialog.Builder(this);
                    final EditText input=new EditText(this);

                    My2fieldsRow row=dbAdapter.getMyPressureRow(adapter.getItem(info.position).getId());
                    final long row_id=row.getId();
                    final String date=row.getDate();
                    String value=row.getValue();

                    input.setText(value);
                    alert.setTitle(R.string.new_button)
                            .setMessage(R.string.dialog_new_pressure)
                            .setView(input)
                            .setPositiveButton(R.string.accept_button,new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int id){
                                    //anadir un registro y actualizar el listado
                                    String value=input.getText().toString().trim();
                                    try
                                    {
                                        dbAdapter.editMyPressureRow(row_id,date,value);
                                        loadData();
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

                    break;
                case R.id.delete:
                    dbAdapter.deleteMyPressureRow(adapter.getItem(info.position).getId());
                    loadData();
                    break;
                default: return super.onContextItemSelected(item);
            }
        }
        catch (ClassCastException e)
        {
            Log.e(e.getClass().getName(),e.getMessage(),e);
            return false;
        }
        catch (SQLException e)
        {
            Log.e(e.getClass().getName(),e.getMessage(),e);
            return false;
        }
        return true;
    }
}
