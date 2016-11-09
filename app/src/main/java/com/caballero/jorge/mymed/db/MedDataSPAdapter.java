package com.caballero.jorge.mymed.db;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * /Manejador del archivo SharedPreferenes que contiene los métodos necesarios para insertar y editar sus valores.
 *  Created by P1047 on 09/08/2016.
 */
public class MedDataSPAdapter
{
    private Context context;
    private static final String MyPREFS = "MySharedPreferences";
    private SharedPreferences sharedPreferences;

    public MedDataSPAdapter(Context context)
    {
        this.context=context;
        sharedPreferences=context.getSharedPreferences(MyPREFS,context.MODE_PRIVATE);
    }
    //Dada una key devuelve true si existe en el fichero XML o false en caso contrario.
    public boolean isKey(String key){
        return sharedPreferences.contains(key);
    }
    //Dada una key, busca en el XML SharedPreferences el valor del elemento, si no existe devuelve "00:00" por defecto
    public String getValue(String key)
    {
        return sharedPreferences.getString(key, "00:00");
    }
    //Dado una key y un valor los inserta en el archivo SharedPreferences si no existe o edita su valor si existe la clave
    public void insertValue(String key,String value)
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

}
