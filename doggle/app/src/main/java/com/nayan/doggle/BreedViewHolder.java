package com.nayan.doggle;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class BreedViewHolder extends RecyclerView.ViewHolder {
    private ImageView breedImage;
    private TextView breedName;
    private TextView breedDescription;
    private TextView breedAgeExpectancy;
    private TextView breedAverageHeight;
    private TextView breedAverageWeight;
    private TextView breedCharacteristics;
    private Button wikibtn;

    public BreedViewHolder(View itemView) {
        super(itemView);
        breedImage = itemView.findViewById(R.id.imageView);
        breedName = itemView.findViewById(R.id.breedname);
        breedDescription = itemView.findViewById(R.id.breeddesc);
        breedAgeExpectancy = itemView.findViewById(R.id.age);
        breedAverageHeight = itemView.findViewById(R.id.height);
        breedAverageWeight = itemView.findViewById(R.id.weight);
        breedCharacteristics = itemView.findViewById(R.id.charac);
        wikibtn=itemView.findViewById(R.id.wikibtn);
    }

    public void bind(Breed breed) {
        Glide.with(itemView.getContext()).load(breed.getImage()).into(breedImage);
        breedName.setText(breed.getName());
        breedDescription.setText(breed.getDescription());
        breedAgeExpectancy.setText(breed.getAge());
        breedAverageHeight.setText(breed.getHeight());
        breedAverageWeight.setText(breed.getWeight());
        String[] characteristicsArray = breed.getCharacteristics().split(",");
        StringBuilder characteristicsText = new StringBuilder();
        for (String characteristic : characteristicsArray) {
            characteristicsText.append("- ").append(characteristic.trim()).append("\n");
        }
        breedCharacteristics.setText(characteristicsText.toString());
        wikibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Wikipedia page for the breed
                String wikiUrl = "https://en.wikipedia.org/wiki/" + breed.getName();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl));
                itemView.getContext().startActivity(intent);
            }
        });
    }
}
