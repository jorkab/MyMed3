package com.caballero.jorge.mymed.data;

/**
 * Created by P1047 on 01/08/2016.
 */
public class My2fieldsRow
{
    //Atributos

    private long id;
    private String date;
    private String value;

    //Getters & Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    //Constructor

    public My2fieldsRow(long id, String date, String value)
    {
        this.date=date;
        this.value=value;
    }
}
