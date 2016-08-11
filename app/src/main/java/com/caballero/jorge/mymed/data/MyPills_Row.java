package com.caballero.jorge.mymed.data;

/**
 * Created by Jorge on 01/04/2016.
 */
public class MyPills_Row
{
    //Atributos

    private long id;
    private String name;
    private String duration;
    private boolean breakfast=false;
    private boolean lunch=false;
    private boolean dinner=false;
    private boolean sleep=false;

    //Getters & Setters

    public long getId(){return id;}

    public void setId(int id){this.id=id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public boolean getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(boolean breakfast) {
        this.breakfast = breakfast;
    }

    public boolean getLunch() {
        return lunch;
    }

    public void setLunch(boolean lunch) {
        this.lunch = lunch;
    }

    public boolean getDinner() {
        return dinner;
    }

    public void setDinner(boolean dinner) {
        this.dinner = dinner;
    }

    public boolean getSleep() {
        return sleep;
    }

    public void setSleep(boolean sleep) {
        this.sleep = sleep;
    }

    //Constructor

    public MyPills_Row(long id, String name, String duration, boolean breakfast, boolean lunch, boolean dinner, boolean sleep) {
        this.id=id;
        this.name = name;
        this.duration = duration;
        this.breakfast=breakfast;
        this.lunch=lunch;
        this.dinner=dinner;
        this.sleep=sleep;

    }
    public MyPills_Row(long id, String name, String duration, int breakfast, int lunch, int dinner, int sleep)
    {
        this.id=id;
        this.name = name;
        this.duration = duration;
        this.breakfast=convertToBoolean(breakfast);
        this.lunch=convertToBoolean(lunch);
        this.dinner=convertToBoolean(dinner);
        this.sleep=convertToBoolean(sleep);
    }
    //Metodos
    private boolean convertToBoolean(int number)
    {
        if(number==1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
