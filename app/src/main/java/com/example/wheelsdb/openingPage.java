package com.example.wheelsdb;

import static com.example.wheelsdb.R.id.btnsrch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class openingPage extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_page);
        Button add = findViewById(R.id.btnAdd);
        Button search=findViewById(R.id.btnsrch);
        //TODO initializing Shared variables
        SharedPreferences preferences= getSharedPreferences("PREFERANCE",MODE_PRIVATE);//declare variables that is
                                                                                            // used in other activities
        String sortbrand = preferences.getString("sortbrand", "");
        String sortname = preferences.getString("sortname","");
        String sortprice = preferences.getString("sortprice","");
        String sortengine = preferences.getString("sortengine","");
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(openingPage.this,MainActivity.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //TODO editing Shared variables
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("sortbrand","");
                editor.putString("sortname","");
                editor.putString("sortprice","");
                editor.putString("sortengine","");
                editor.apply();
                Intent intent=new Intent(openingPage.this,recycler_view.class);
                startActivity(intent);
            }
        });


    }
}