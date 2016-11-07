package com.caballero.jorge.mymed.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.caballero.jorge.mymed.data.My2fieldsRow;
import com.caballero.jorge.mymed.data.MyPills_Row;

import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Created by Jorge on 04/04/2016.
 */
public class MedDataBDAdapter
{
    //Atributos MyPills
    public static final String TABLE_TREATMENT="treatment";
    public static final String FIELD_NAME="name";
    public static final String FIELD_BREAKFAST="breakfast";
    public static final String FIELD_LUNCH="lunch";
    public static final String FIELD_DINNER="dinner";
    public static final String FIELD_SLEEP="sleep";
    public static final String FIELD_DURATION="duration";
    public static final String FIELD_ID="_id";

    //Atributos MyBloodPressure
    public static final String TABLE_PRESSURE="pressure";
    public static final String FIELD_PRESSURE="pressure";

    //Atributos MyBloodGlucose
    public static final String TABLE_GLUCOSE="glucose";
    public static final String FIELD_GLUCOSE="glucose";

    public static final String FIELD_DATE="date";

    private SQLiteDatabase db;
    private MedDataSQLiteHelper dbHelper;

    public MedDataBDAdapter(Context context)
    {
        dbHelper=new MedDataSQLiteHelper(context);
    }

    //Metodos para la tabla MyPills
    //Inserta una fila en MyPills

    public void insertMyPillsRow(String name,String duration,boolean breakfast,boolean lunch, boolean dinner, boolean sleep)throws SQLException
    {
        db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(FIELD_NAME,name);
        values.put(FIELD_DURATION,duration);
        values.put(FIELD_BREAKFAST, breakfast);
        values.put(FIELD_LUNCH, lunch);
        values.put(FIELD_DINNER,dinner);
        values.put(FIELD_SLEEP,sleep);

        db.insert(TABLE_TREATMENT,null,values);
        db.close();
    }

    //Devuelve una fila de MyPills dado un id

    public MyPills_Row getMyPillsRow(long id)
    {
        db=dbHelper.getReadableDatabase();
        MyPills_Row row=null;
        Cursor c=db.rawQuery("SELECT name, duration, breakfast, lunch, dinner, sleep FROM treatment WHERE _id="+id+"",null);

        if(c.moveToFirst())
        {
            row=new MyPills_Row(id,c.getString(0),c.getString(1),c.getInt(2), c.getInt(3),c.getInt(4),c.getInt(5));
        }
        db.close();
        return row;
    }

    //Dado un id y los valores de una nueva fila, actualiza los antiguos valores de la fila del id pasado

    public void editMyPillsRow(long id,String name,String duration,boolean breakfast,boolean lunch, boolean dinner, boolean sleep)throws SQLException
    {
        db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(FIELD_NAME,name);
        values.put(FIELD_DURATION,duration);
        values.put(FIELD_BREAKFAST, breakfast);
        values.put(FIELD_LUNCH, lunch);
        values.put(FIELD_DINNER,dinner);
        values.put(FIELD_SLEEP,sleep);

        db.update(TABLE_TREATMENT, values, FIELD_ID + "=" + id, null);
        db.close();
    }

    //Elimina de la bd la fila pasada por id

    public void deleteMyPillsRow(long id)throws SQLException
    {
        db=dbHelper.getWritableDatabase();
        db.delete(TABLE_TREATMENT,FIELD_ID+"="+id,null);
        db.close();

    }

    //Devuelve un listado de todas las filas de MyPills

    public LinkedList<MyPills_Row> getMyPillsRows() throws SQLException
    {
        LinkedList<MyPills_Row>list=new LinkedList<>();
        db=dbHelper.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT _id, name, duration, breakfast, lunch, dinner, sleep FROM treatment ",null);
        if(c.moveToFirst())
        {
            do
            {
                MyPills_Row myPills_row=new MyPills_Row(c.getLong(0),c.getString(1),c.getString(2),c.getInt(3),c.getInt(4),c.getInt(5),c.getInt(6));
                list.add(myPills_row);
            }
            while(c.moveToNext());
            c.close();
            db.close();
        }
        return list;
    }

    //Metodos para la tabla MyBloodPressure
    //Inserta un registro en MyBloodPressure

    public void insertMyPressureRow(String date,String pressure)throws SQLException
    {
        db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(FIELD_DATE,date);
        values.put(FIELD_PRESSURE,pressure);

        db.insert(TABLE_PRESSURE,null,values);
        db.close();
    }

    //Devuelve todos los registros de la tabla presure

    public LinkedList<My2fieldsRow> getMyPressureRows() throws SQLException
    {
        LinkedList<My2fieldsRow>list=new LinkedList<>();
        db=dbHelper.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT _id, date, pressure FROM "+ TABLE_PRESSURE+" ",null);
        if(c.moveToFirst())
        {
            do
            {
                My2fieldsRow myBPressure_row=new My2fieldsRow(c.getLong(0),c.getString(1),c.getString(2));
                list.add(myBPressure_row);
            }
            while(c.moveToNext());
            c.close();
            db.close();
        }
        return list;
    }
    public void editMyPressureRow(long id,String date,String value)throws SQLException
    {
        db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(FIELD_DATE,date);
        values.put(FIELD_PRESSURE,value);

        db.update(TABLE_PRESSURE, values, FIELD_ID + "=" + id, null);
        db.close();
    }

    public My2fieldsRow getMyPressureRow(long id)throws SQLException{
        db=dbHelper.getReadableDatabase();
        My2fieldsRow row=null;
        Cursor c=db.rawQuery("SELECT _id, date, pressure FROM "+ TABLE_PRESSURE+" ",null);

        if(c.moveToFirst())
        {
            row=new My2fieldsRow(id,c.getString(1),c.getString(2));
        }
        db.close();
        return row;

    }
    public void deleteMyPressureRow(long id)throws SQLException{
        db=dbHelper.getWritableDatabase();
        db.delete(TABLE_PRESSURE,FIELD_ID+"="+id,null);
        db.close();
    }
}
