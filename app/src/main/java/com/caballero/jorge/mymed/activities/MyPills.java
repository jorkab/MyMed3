package com.caballero.jorge.mymed.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.caballero.jorge.mymed.R;
import com.caballero.jorge.mymed.data.MyPills_Row;
import com.caballero.jorge.mymed.db.MedDataBDAdapter;

import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Created by Jorge on 01/04/2016.
 */
public class MyPills extends ListActivity implements View.OnClickListener
{
    private static final int COD_MYPILLS_ADD=1;
    private static final int COD_MYPILLS_EDIT=2;
    private MyPills_RowAdapter adapter;
    private Button newButton;
    private MedDataBDAdapter dbAdapter;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pills);

        newButton=(Button)findViewById(R.id.myPills_new_button);
        newButton.setOnClickListener(this);

        dbAdapter=new MedDataBDAdapter(this);

        ListView listView=getListView();
        adapter=new MyPills_RowAdapter(this,R.layout.activity_my_pills);
        listView.setAdapter(adapter);
        //Listener para eventos longclick en el listado.
        registerForContextMenu(listView);
        loadData();
    }

    //Actualiza los datos del listado de la aplicacion con los de la base de datos

    public void loadData()
    {
        LinkedList<MyPills_Row>list;
        adapter.clear();
        try
        {
            list=dbAdapter.getMyPillsRows();
            for(MyPills_Row row: list)
            {
                adapter.add(row);
            }
            adapter.notifyDataSetChanged();
        }
        catch (SQLException e)
        {
            Log.e(e.getClass().getName(), e.getMessage(), e);
        }
    }

    //Lanza el intent para crear un nuevo medicamento tras pulsar nuevo

    @Override
    public void onClick(View v)
    {

        if(v.getId()==R.id.myPills_new_button)
        {
            Intent intent=new Intent(this,MyPillsAdd.class);
            intent.putExtra("name","");
            intent.putExtra("duration","");
            intent.putExtra("breakfast",false);
            intent.putExtra("lunch",false);
            intent.putExtra("dinner",false);
            intent.putExtra("sleep",false);

            startActivityForResult(intent, COD_MYPILLS_ADD);
        }
    }

    //Respuesta del intent para crear un nuevo medicamento o editar uno existente

    protected void onActivityResult(int requestCode,int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case COD_MYPILLS_ADD:
                if(resultCode==RESULT_OK)
                {

                    String name = data.getExtras().getString("name");
                    String duration = data.getExtras().getString("duration");
                    Boolean breakfast = data.getExtras().getBoolean("breakfast");
                    Boolean lunch = data.getExtras().getBoolean("lunch");
                    Boolean dinner = data.getExtras().getBoolean("dinner");
                    Boolean sleep = data.getExtras().getBoolean("sleep");
                    try
                    {
                        dbAdapter.insertMyPillsRow(name,duration,breakfast,lunch,dinner,sleep);
                        loadData();
                    }
                    catch (SQLException e)
                    {
                        Log.e(e.getClass().getName(),e.getMessage(),e);
                    }
                }
                break;
            case COD_MYPILLS_EDIT:
                if(resultCode==RESULT_OK)
                {
                    Long id=data.getExtras().getLong(("id"));
                    String name = data.getExtras().getString("name");
                    String duration = data.getExtras().getString("duration");
                    Boolean breakfast = data.getExtras().getBoolean("breakfast");
                    Boolean lunch = data.getExtras().getBoolean("lunch");
                    Boolean dinner = data.getExtras().getBoolean("dinner");
                    Boolean sleep = data.getExtras().getBoolean("sleep");
                    try
                    {
                        dbAdapter.editMyPillsRow(id,name,duration,breakfast,lunch,dinner,sleep);
                        loadData();
                    }
                    catch (SQLException e)
                    {
                        Log.e(e.getClass().getName(),e.getMessage(),e);
                    }
                }
            default:break;
        }
    }

    //Infla el menu de pulsacion larga sobre un elemento del listado

    @Override
    public void onCreateContextMenu(ContextMenu menu,View v,ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu,v,menuInfo);

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_onlongclick_mypills, menu);
    }

    //Realiza la accion seleccionada tras seleccionar uno de los elementos de la pulsacion larga

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
                    MyPills_Row row=dbAdapter.getMyPillsRow(adapter.getItem(info.position).getId());
                    Intent intent = new Intent(this, MyPillsEdit.class);
                    intent.putExtra("id",row.getId());
                    intent.putExtra("name",row.getName());
                    intent.putExtra("duration",row.getDuration());
                    intent.putExtra("breakfast",row.getBreakfast());
                    intent.putExtra("lunch",row.getLunch());
                    intent.putExtra("dinner",row.getDinner());
                    intent.putExtra("sleep", row.getSleep());
                    startActivityForResult(intent, COD_MYPILLS_EDIT);
                    break;
                case R.id.delete:
                    dbAdapter.deleteMyPillsRow(adapter.getItem(info.position).getId());
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
