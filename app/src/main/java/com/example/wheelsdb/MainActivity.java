package com.example.wheelsdb;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    StorageReference storageReference;
    LinearProgressIndicator progress;
    Uri image;
    private final ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if (result.getResultCode()==RESULT_OK)
            {

                if (result.getData()!=null)
                {
                    image=result.getData().getData();
                    btnAddImage.setEnabled(true);
                    Glide.with(getApplicationContext()).load(image).into(imageView);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please add image", Toast.LENGTH_SHORT).show();
                }
            }

        }
    });

    ImageView imageView;

    EditText etCarName;
    EditText etPrice, etEngineSpec , etCarStock;
    Spinner spinnerCourses;
    Button btnInsertData, btnAddImage;

    DatabaseReference carsDbRef;

    ArrayAdapter<CharSequence> adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCarName = findViewById(R.id.etCarName);
        etPrice = findViewById(R.id.etPrice);
        etEngineSpec = findViewById(R.id.etEngineSpec);
        etCarStock= findViewById(R.id.etStock);
        spinnerCourses = findViewById(R.id.spinnerCourse);//brand

        btnInsertData = findViewById(R.id.btnInsertData);
        //images
        btnAddImage = findViewById(R.id.btnAddImage);
        imageView=findViewById(R.id.imageView);
        //Spinner spinnerCourse = findViewById(R.id.spinnerCourse);




        adapter = ArrayAdapter.createFromResource(this, R.array.courses, R.layout.spinner_text);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnerCourses.setAdapter(adapter);// Apply the adapter to the spinner

        //todo creating an instance for firebase realtime database
        carsDbRef = FirebaseDatabase.getInstance().getReference().child("cars");
       // carsDbRef.setValue("basil");
        storageReference=FirebaseStorage.getInstance().getReference();



        btnAddImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);

            }
        });
        btnInsertData.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                insertCarData(image);
                Intent intent = new Intent(MainActivity.this, openingPage.class);
                startActivity(intent);

            }
        });

    }


    private void insertCarData(Uri image)
    {
        //todo entering data to firebase realtime base
        String name = etCarName.getText().toString();
        String price = etPrice.getText().toString();
        String engine = etEngineSpec.getText().toString();
        String brand = spinnerCourses.getSelectedItem().toString();
        String stock = etCarStock.getText().toString();
        String id = carsDbRef.push().getKey();//unique id for e
       // String u = "";//imageUri.toString();
       // Log.e("Debug", "Name: " + name + ", Brand: " + brand + ", Price: " + price + ", Engine: " + engine);
        Cars cars = new Cars(name,brand,price,engine,stock,id);
        //Log.e("Debug", "Cars object created: " + cars.toString());


        //carsDbRef.push().setValue(cars);//unique key for each recorded

        carsDbRef.child(id).setValue(cars);

        // todo:entering image to storage
       StorageReference reference=storageReference.child("image/"+ id);
        reference.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(MainActivity.this, "image uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Error uploading image", Toast.LENGTH_SHORT).show();
            }
        });




    }



}

