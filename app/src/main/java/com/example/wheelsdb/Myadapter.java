package com.example.wheelsdb;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//class to insert a card into each item in recycler view
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder>
    {
        @NonNull
        Context context;
        List<Cars> items;
        Button search;

        private RecyclerView recyclerView;

        public void removeItem(int position) {
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
            Button book;


            public MyViewHolder(@NonNull View itemView)
            {
                super(itemView);
                name = itemView.findViewById(R.id.name);
                brand=itemView.findViewById(R.id.brand);
                price=itemView.findViewById(R.id.price);
                engine=itemView.findViewById(R.id.engine);

                book= itemView.findViewById(R.id.book);
                book.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            // Remove item from the list
                            ((Myadapter)recyclerView.getAdapter()).removeItem(position);

                            // TODO: Remove data from Firebase (you need to implement this)

                            // Example: Remove the data from Firebase
                            String itemIdToRemove = items.get(position).getName();
                            removeDataFromFirebase("a4");
                        }
                    }
                });

            }
            private void removeDataFromFirebase(String itemName) {
                // Get a reference to your Firebase database
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("cars");

                // Query to find the car with the specified name
                Query query = databaseReference.orderByChild("cars").equalTo(itemName);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Loop through the results (there should be only one result)
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // Remove the data from Firebase
                            snapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors, if any
                        Toast.makeText(context, "Failed to remove data from Firebase", Toast.LENGTH_SHORT).show();
                    }
                });
            }





        }


    }
