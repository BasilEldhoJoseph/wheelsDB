package com.example.wheelsdb;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//class to insert a card into each item in recycler view
import java.io.File;
import java.io.IOException;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder>
    {
        @NonNull
        Context context;
        List<Cars> items;
        Button search;

        private RecyclerView recyclerView;
        String carKey;

        public void removeItem(int position) {
            //carKey = items.get(position).getKey();
            items.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }

        public Myadapter(@NonNull Context context, List<Cars> items,RecyclerView recyclerView)
        {
            this.context = context;
            this.items = items;
            this.recyclerView = recyclerView;
        }


        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View V = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);//making into card
            return new MyViewHolder(V);//card returned
        }


        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
        {
            Cars helper1 = items.get(position);
            holder.name.setText(helper1.getName());
            holder.brand.setText(helper1.getBrand());
            holder.price.setText(helper1.getPrice());
            holder.engine.setText(helper1.getEngine());
        }


        @Override
        public int getItemCount() {
            return items.size();
        }//finding number of card required ????????


        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            TextView name, brand,price,engine;
            Button book,viewCar;


            public MyViewHolder(@NonNull View itemView)
            {
                super(itemView);
                name = itemView.findViewById(R.id.name);
                brand=itemView.findViewById(R.id.brand);
                price=itemView.findViewById(R.id.price);
                engine=itemView.findViewById(R.id.engine);


                book= itemView.findViewById(R.id.book);
                viewCar= itemView.findViewById(R.id.viewCar);


                //todo see car
                viewCar.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {

                        //need to get id of the  clicked recycle view item
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            // Get the ID of the clicked item
                            String clickedCarId = items.get(position).getKey();

                            // TODO: Fetch image stored in the clicked car's ID
                            //fetchImageFromFirebase(clickedCarId);

                            StorageReference sf= FirebaseStorage.getInstance().getReference("image/"+ clickedCarId);

                            try
                            {
                                File localFile =File.createTempFile("tempFile",".jpg");
                                sf.getFile(localFile)
                                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot)
                                            {
                                                Bitmap bitmap= BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                                showImageDialog(bitmap);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });
                            } catch (IOException e)
                            {
                                throw new RuntimeException(e);
                            }


                        }
                    }
                });


                // todo BOOK CAR
                book.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)//NO_POSITION is a constant in the RecyclerView class. It's used to indicate that a valid position cannot be determined or does not exist. When getAdapterPosition() is called on an item that is no longer in the adapter (e.g., because it has been removed), or if the adapter has not been set yet, it returns NO_POSITION.
                        {
                            // Remove item from the list

                            ((Myadapter)recyclerView.getAdapter()).removeItem(position);// This method is used to get the position of the item in the adapter (in this case,
                                                                                        // the position of the clicked item in the RecyclerView).
                                                                                        // Calls the removeItem method of the Myadapter to remove the item at the clicked position from the local list that populates the RecyclerView.
                                                                                        // This is a local operation and doesn't affect the data in Firebase yet.

                            // TODO: Remove data from Firebase (you need to implement this)

                            // Example: Remove the data from Firebase
                            //String itemIdToRemove = items.get(position).getName();
                            carKey=items.get(position).getKey();


                            removeDataFromFirebase(carKey);
                        }
                    }
                });

            }
            private void removeDataFromFirebase(String carToDelete)
            {
                // Get a reference to your Firebase database
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("cars");     //.child(carToDelete);
                DatabaseReference carRef = databaseReference.child(carToDelete);
               // databaseReference;
                carRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Item deleted from Firebase", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to delete item from Firebase", Toast.LENGTH_SHORT).show();
                    }
                });

                // Query to find the car with the specified name
               /* Query query = databaseReference.orderByChild("name").equalTo(itemName);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Loop through the results (there should be only one result)
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // Remove the data from Firebase
                            String itemName = snapshot.child("engine").getValue(String.class);
                            Toast.makeText(context, "Deleting item: " + itemName, Toast.LENGTH_SHORT).show();
                            snapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors, if any
                        Toast.makeText(context, "Failed to remove data from Firebase", Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
            private void showImageDialog(Bitmap bitmap) {
                // Create a custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.image_popup_layout);

                // Set up the dialog components
                ImageView imageView = dialog.findViewById(R.id.popupImageView);
                imageView.setImageBitmap(bitmap);


               /* Button closeButton = dialog.findViewById(R.id.closeButton);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });*/

                // Close the dialog when clicking outside the image
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
            }





        }


    }

