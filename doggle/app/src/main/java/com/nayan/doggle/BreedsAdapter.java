package com.nayan.doggle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BreedsAdapter extends RecyclerView.Adapter<BreedViewHolder> {
    private List<Breed> breedsList;

    public BreedsAdapter(List<Breed> breedsList) {
        this.breedsList = breedsList;
    }

    @NonNull
    @Override
    public BreedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.breed_card, parent, false);
        return new BreedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BreedViewHolder holder, int position) {
        Breed breed = breedsList.get(position);
        holder.bind(breed);
    }

    @Override
    public int getItemCount() {
        return breedsList.size();
    }
}

