package com.nayan.doggle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Breed {
    private String Name;
    private String Description;
    private String Age;
    private String Height;
    private String Weight;
    private String Characteristics;
    private String Image;

    public Breed() {
    }


    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String Age) {
        this.Age = Age;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String Height) {
        this.Height = Height;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String Weight) {
        this.Weight = Weight;
    }

    public String getCharacteristics() {
        return Characteristics;
    }

    public void setCharacteristics(String Characteristics) {
        this.Characteristics = Characteristics;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public static void getBreedsFromFirebase(final BreedsCallback callback) {
        DatabaseReference breedsRef = FirebaseDatabase.getInstance().getReference().child("Dogs");

        breedsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Breed> breedsList = new ArrayList<>();

                for (DataSnapshot breedSnapshot : dataSnapshot.getChildren()) {
                    Breed breed = breedSnapshot.getValue(Breed.class);
                    breedsList.add(breed);
                }

                callback.onBreedsReceived(breedsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailed(databaseError.getMessage());
            }
        });
    }

    public interface BreedsCallback {
        void onBreedsReceived(List<Breed> breeds);

        void onFailed(String errorMessage);
    }
}
