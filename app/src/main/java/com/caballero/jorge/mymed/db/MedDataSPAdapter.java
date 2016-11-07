package com.caballero.jorge.mymed.db;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by P1047 on 09/08/2016.
 */
public class MedDataSPAdapter
{
    private Context context;
    public static final String MyPREFS = "MySharedPreferences";

    public MedDataSPAdapter(Context context)
    {
        this.context=context;
    }

    public String getValue(String key)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "00:00");
    }

    public void insertValue(String key,String value)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

}
