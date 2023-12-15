package com.example.wheelsdb;
import com.example.wheelsdb.Myadapter;


import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

public class recycler_view extends AppCompatActivity {
    DatabaseReference getCarinfo;
    Myadapter myadapter;
    Button book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        //TODO initializing shared variables for use
        SharedPreferences sp = getApplicationContext().getSharedPreferences("PREFERANCE", Context.MODE_PRIVATE);
        String sortbrand = sp.getString("sortbrand", "");
        String sortname = sp.getString("sortname","");
        String sortprice = sp.getString("sortprice","");
        String sortengine = sp.getString("sortengine","");


        MaterialButton sort =(MaterialButton) findViewById(R.id.sort);

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(recycler_view.this, SORT.class);
                startActivity(next);
            }
        });

        getCarinfo =FirebaseDatabase.getInstance().getReference("cars");///!!!!!!!!!!!!!


        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        List<Cars> list=new ArrayList<>();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));//layout for recycler view
        recyclerView.setHasFixedSize(true);//Set fixed size

        myadapter = new Myadapter(this,list,recyclerView);
        recyclerView.setAdapter(myadapter);
        myadapter.setOnItemClickLisener(new Myadapter.OnItemClickLisener() {
            @Override
            public void onItemClick() {
                Intent intent=getIntent();
                finish();
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(myadapter);//???
        getCarinfo.addValueEventListener(new ValueEventListener() //check value in DB ???
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())//data pulled from DB coverted into item of Recycler view
                {
                     Cars helper1=dataSnapshot.getValue(Cars.class);
                    // String eng=helper1.getBrand();///sorting?????
                     //if(eng.equals("w16")){
                        // list.add(helper1);
                    // }
                    //list.add(helper1);
                    //TODO Sorting code
                    if(helper1.getName().equals(sortname) || sortname.equals("")){
                        if(helper1.getBrand().equals(sortbrand) || sortbrand.equals("")) {
                            if(helper1.getEngine().equals(sortengine) || sortengine.equals("")){
                                if(sortprice.equals("")) {
                                     list.add(helper1);
                                }
                                else if (Integer.valueOf(helper1.getPrice())<=Integer.valueOf(sortprice)) {
                                     list.add(helper1);
                                }
                            }
                        }
                    }
                }
                myadapter.notifyDataSetChanged();//live  change of data when app is closed,without it app need to reopened  .
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(recycler_view.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });



    }
}