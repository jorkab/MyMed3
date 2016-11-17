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
 * Clase que contiene los métodos para realizar las operaciones de inserción modificación borrado y listado de objetos de la base de datos
 * Created by Jorge on 04/04/2016.
 */
public class MedDataBDAdapter
{
    //Atributos MyPills
    private static final String TABLE_TREATMENT="treatment";
    private static final String FIELD_NAME="name";
    private static final String FIELD_BREAKFAST="breakfast";
    private static final String FIELD_DINNER="dinner";
    private static final String FIELD_SLEEP="sleep";
    private static final String FIELD_DURATION="duration";
    private static final String FIELD_ID="_id";
    private static final String FIELD_LUNCH="lunch";

    //Atributos MyBloodPressure
    private static final String TABLE_PRESSURE="pressure";
    private static final String FIELD_PRESSURE="pressure";

    //Atributos MyBloodGlucose
    private static final String TABLE_GLUCOSE="glucose";
    private static final String FIELD_GLUCOSE="glucose";

    private static final String FIELD_DATE="date";

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
        c.close();
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

    //Metodos para la tabla MyBloodGlucose
    //Inserta un registro en MyBloodGlucose

    public void insertMyGlucoseRow(String date,String glucose)throws SQLException
    {
        db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(FIELD_DATE,date);
        values.put(FIELD_GLUCOSE,glucose);

        db.insert(TABLE_GLUCOSE,null,values);
        db.close();
    }

    //Devuelve todos los registros de la tabla glucose

    public LinkedList<My2fieldsRow> getMyGlucoseRows() throws SQLException
    {
        LinkedList<My2fieldsRow>list=new LinkedList<>();
        db=dbHelper.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT _id, date, glucose FROM "+ TABLE_GLUCOSE+" ",null);
        if(c.moveToFirst())
        {
            do
            {
                My2fieldsRow myBglucose_row=new My2fieldsRow(c.getLong(0),c.getString(1),c.getString(2));
                list.add(myBglucose_row);
            }
            while(c.moveToNext());
            c.close();
            db.close();
        }
        return list;
    }
    //Dado un id y los valores de una nueva fila, actualiza los antiguos valores de la fila del id pasado
    public void editMyGlucoseRow(long id,String date,String value)throws SQLException
    {
        db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(FIELD_DATE,date);
        values.put(FIELD_GLUCOSE,value);

        db.update(TABLE_GLUCOSE, values, FIELD_ID + "=" + id, null);
        db.close();
    }

    public My2fieldsRow getMyGlucoseRow(long id)throws SQLException{
        db=dbHelper.getReadableDatabase();
        My2fieldsRow row=null;
        Cursor c=db.rawQuery("SELECT _id, date, glucose FROM "+ TABLE_GLUCOSE+" ",null);

        if(c.moveToFirst())
        {
            row=new My2fieldsRow(id,c.getString(1),c.getString(2));
        }
        db.close();
        c.close();
        return row;

    }
    //Elimina de la bd la fila pasada por id
    public void deleteMyGlucoseRow(long id)throws SQLException{
        db=dbHelper.getWritableDatabase();
        db.delete(TABLE_GLUCOSE,FIELD_ID+"="+id,null);
        db.close();
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
    //Dado un id y los valores de una nueva fila, actualiza los antiguos valores de la fila del id pasado
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
        c.close();
        return row;

    }
    //Elimina de la bd la fila pasada por id
    public void deleteMyPressureRow(long id)throws SQLException{
        db=dbHelper.getWritableDatabase();
        db.delete(TABLE_PRESSURE,FIELD_ID+"="+id,null);
        db.close();
    }
}
