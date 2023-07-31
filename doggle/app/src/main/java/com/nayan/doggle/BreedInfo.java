package com.nayan.doggle;

public class BreedInfo {
    private String breedName;
    private String size;
    private String energy;
    private String grooming;
    private String children;
    private String otherPets;
    private String trainability;
    private String exercise;
    private String livingSpace;
    private String shedding;
    private String allergenic;
    private String breedingGroup;

    public BreedInfo(String breedName, String size, String energy, String grooming, String children,
                     String otherPets, String trainability, String exercise, String livingSpace,
                     String shedding, String allergenic, String breedingGroup) {
        this.breedName = breedName;
        this.size = size;
        this.energy = energy;
        this.grooming = grooming;
        this.children = children;
        this.otherPets = otherPets;
        this.trainability = trainability;
        this.exercise = exercise;
        this.livingSpace = livingSpace;
        this.shedding = shedding;
        this.allergenic = allergenic;
        this.breedingGroup = breedingGroup;
    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getEnergy() {
        return energy;
    }

    public void setEnergy(String energy) {
        this.energy = energy;
    }

    public String getGrooming() {
        return grooming;
    }

    public void setGrooming(String grooming) {
        this.grooming = grooming;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getOtherPets() {
        return otherPets;
    }

    public void setOtherPets(String otherPets) {
        this.otherPets = otherPets;
    }

    public String getTrainability() {
        return trainability;
    }

    public void setTrainability(String trainability) {
        this.trainability = trainability;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getLivingSpace() {
        return livingSpace;
    }

    public void setLivingSpace(String livingSpace) {
        this.livingSpace = livingSpace;
    }

    public String getShedding() {
        return shedding;
    }

    public void setShedding(String shedding) {
        this.shedding = shedding;
    }

    public String getAllergenic() {
        return allergenic;
    }

    public void setAllergenic(String allergenic) {
        this.allergenic = allergenic;
    }

    public String getBreedingGroup() {
        return breedingGroup;
    }

    public void setBreedingGroup(String breedingGroup) {
        this.breedingGroup = breedingGroup;
    }
}

