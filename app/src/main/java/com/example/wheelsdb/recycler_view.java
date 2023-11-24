package com.example.wheelsdb;
import com.example.wheelsdb.Myadapter;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

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

        getCarinfo =FirebaseDatabase.getInstance().getReference("cars");///!!!!!!!!!!!!!


        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        List<Cars> list=new ArrayList<>();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));//layout for recycler view
        recyclerView.setHasFixedSize(true);//??

        myadapter = new Myadapter(this,list,recyclerView);
        recyclerView.setAdapter(myadapter);


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
                    list.add(helper1);
                }
                //myadapter.notifyDataSetChanged();//live  change of data when app is closed,without it app need to reopened  .
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(recycler_view.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });







    }
}