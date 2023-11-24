package com.example.wheelsdb;

import static com.example.wheelsdb.R.id.btnsrch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class openingPage extends AppCompatActivity {

    Button add,search;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_page);
        Button add = findViewById(R.id.btnAdd);
        Button search=findViewById(R.id.btnsrch);

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
                Intent intent=new Intent(openingPage.this,recycler_view.class);
                startActivity(intent);
            }
        });


    }
}