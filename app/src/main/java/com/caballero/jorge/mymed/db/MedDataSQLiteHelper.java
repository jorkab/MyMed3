package com.caballero.jorge.mymed.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Jorge on 04/04/2016.
 */
public class MedDataSQLiteHelper extends SQLiteOpenHelper
{
    private static final String BD_MEDDATA="MEDDATA";
    private static final int BD_VERSION=2;
    private Context context;

    public MedDataSQLiteHelper(Context context)
    {
        super(context,BD_MEDDATA, null, BD_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        InputStream is=null;
        try
        {
            is=context.getAssets().open("meddata.sql");
            if(is!=null)
            {
                db.beginTransaction();
                BufferedReader reader=new BufferedReader(new InputStreamReader(is));
                String line=reader.readLine();
                while(!TextUtils.isEmpty(line))
                {
                    db.execSQL(line);
                    line=reader.readLine();
                }
                db.setTransactionSuccessful();
            }
        }
        catch (IOException e)
        {
            Log.e(e.getClass().getName(), e.getMessage(), e);
        }
        finally
        {
            db.endTransaction();
            if(is!=null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    Log.e(e.getClass().getName(), e.getMessage(), e);
                }
            }
        }

    }

    //Actualiza la bd si existe una nueva version

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //Metodo simplificado onUpgrade, actualiza a la nueva version de la db pero no conserva los datos antiguos.
        db.execSQL("DROP TABLE IF EXISTS treatment");
        db.execSQL("DROP TABLE IF EXISTS pressure");
        db.execSQL("DROP TABLE IF EXISTS sugar");
        onCreate(db);
    }
}
