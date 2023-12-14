package com.example.wheelsdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SORT extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        EditText editbrand =(EditText) findViewById(R.id.editTextBrand);
        EditText editname =(EditText) findViewById(R.id.editTextName);
        EditText editprice =(EditText) findViewById(R.id.editTextPrice);
        EditText editengine =(EditText) findViewById(R.id.editTextEngine);
        Button search =(Button) findViewById(R.id.btnSearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String brand="",name="",price="",engine="";
                brand = editbrand.getText().toString();
                name = editname.getText().toString();
                price = editprice.getText().toString();
                engine = editengine.getText().toString();

                //TODO editing Shared Variables
                SharedPreferences preferences= getSharedPreferences("PREFERANCE",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("sortbrand",brand);
                editor.putString("sortname",name);
                editor.putString("sortprice",price);
                editor.putString("sortengine",engine);
                editor.apply();
                Intent next = new Intent(SORT.this, recycler_view.class);
                startActivity(next);
            }
        });
    }
}