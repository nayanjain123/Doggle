package com.nayan.doggle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BreedsFragment extends Fragment {
    private RecyclerView recyclerView;
    private BreedsAdapter breedsAdapter;
    private List<Breed> breedsList;

    public BreedsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_breeds, container, false);

        recyclerView = view.findViewById(R.id.breedslist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        breedsList = new ArrayList<>();
        breedsAdapter = new BreedsAdapter(breedsList);
        recyclerView.setAdapter(breedsAdapter);

        loadBreedsData();

        return view;
    }

    private void loadBreedsData() {
        FirebaseDatabase.getInstance().getReference().child("Dogs")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot breedSnapshot : dataSnapshot.getChildren()) {
                                Breed breed = breedSnapshot.getValue(Breed.class);
                                breedsList.add(breed);
                            }
                            breedsAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle database error if needed
                    }
                });
    }


}
