package com.caballero.jorge.mymed.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.caballero.jorge.mymed.R;

/**
 * Created by P1047 on 01/08/2016.
 */
public class MyBSugar extends ListActivity implements View.OnClickListener
{
    private Button newButton;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pills);

        newButton=(Button)findViewById(R.id.myPills_new_button);
        newButton.setOnClickListener(this);

        ListView listView=getListView();

        registerForContextMenu(listView);
    }

    @Override
    public void onClick(View v) {

    }
}
