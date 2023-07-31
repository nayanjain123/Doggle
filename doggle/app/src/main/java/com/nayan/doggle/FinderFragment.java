package com.nayan.doggle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

public class FinderFragment extends Fragment {

    private List<String[]> breedData;

    public FinderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        breedData = CSVFileReader.readCSV(requireContext(), "dogfinder.csv");

        // Find UI elements
        CheckBox smallSizeCheckBox = view.findViewById(R.id.smallSize);
        CheckBox mediumSizeCheckBox = view.findViewById(R.id.mediumSize);
        CheckBox largeSizeCheckBox = view.findViewById(R.id.largeSize);
        CheckBox lowEnergyCheckBox = view.findViewById(R.id.lowEnergy);
        CheckBox mediumEnergyCheckBox = view.findViewById(R.id.mediumEnergy);
        CheckBox highEnergyCheckBox = view.findViewById(R.id.highEnergy);
        CheckBox lowGroomingCheckBox = view.findViewById(R.id.lowGrooming);
        CheckBox mediumGroomingCheckBox = view.findViewById(R.id.mediumGrooming);
        CheckBox highGroomingCheckBox = view.findViewById(R.id.highGrooming);
        RadioButton hasChildrenCheckBox = view.findViewById(R.id.yesChildren);
        RadioButton hasOtherPetsCheckBox = view.findViewById(R.id.yesPets);
        CheckBox lowTrainabilityCheckBox = view.findViewById(R.id.lowTrain);
        CheckBox mediumTrainabilityCheckBox = view.findViewById(R.id.moderateTrain);
        CheckBox highTrainabilityCheckBox = view.findViewById(R.id.highTrain);
        CheckBox lowExerciseCheckBox = view.findViewById(R.id.lowExercise);
        CheckBox mediumExerciseCheckBox = view.findViewById(R.id.moderateExercise);
        CheckBox highExerciseCheckBox = view.findViewById(R.id.highExercise);
        RadioButton apartmentSpaceCheckBox = view.findViewById(R.id.apartmentSpace);
        RadioButton spaciousSpaceCheckBox = view.findViewById(R.id.spaciousSpace);
        CheckBox lowSheddingCheckBox = view.findViewById(R.id.lowShedding);
        CheckBox mediumSheddingCheckBox = view.findViewById(R.id.moderateShedding);
        CheckBox highSheddingCheckBox = view.findViewById(R.id.highShedding);
        RadioButton hasAllergiesCheckBox = view.findViewById(R.id.yesAllergies);

        Button findButton = view.findViewById(R.id.findbtn);
        TextView resultTextView = view.findViewById(R.id.resultTextView);

        findButton.setOnClickListener(v -> {
            boolean isSmallSizeSelected = smallSizeCheckBox.isChecked();
            boolean isMediumSizeSelected = mediumSizeCheckBox.isChecked();
            boolean isLargeSizeSelected = largeSizeCheckBox.isChecked();
            boolean isLowEnergySelected = lowEnergyCheckBox.isChecked();
            boolean isMediumEnergySelected = mediumEnergyCheckBox.isChecked();
            boolean isHighEnergySelected = highEnergyCheckBox.isChecked();
            boolean isLowGroomingSelected = lowGroomingCheckBox.isChecked();
            boolean isMediumGroomingSelected = mediumGroomingCheckBox.isChecked();
            boolean isHighGroomingSelected = highGroomingCheckBox.isChecked();
            boolean hasChildren = hasChildrenCheckBox.isChecked();
            boolean hasOtherPets = hasOtherPetsCheckBox.isChecked();
            boolean isLowTrainabilitySelected = lowTrainabilityCheckBox.isChecked();
            boolean isMediumTrainabilitySelected = mediumTrainabilityCheckBox.isChecked();
            boolean isHighTrainabilitySelected = highTrainabilityCheckBox.isChecked();
            boolean isLowExerciseSelected = lowExerciseCheckBox.isChecked();
            boolean isMediumExerciseSelected = mediumExerciseCheckBox.isChecked();
            boolean isHighExerciseSelected = highExerciseCheckBox.isChecked();
            boolean isApartmentSpaceSelected = apartmentSpaceCheckBox.isChecked();
            boolean isSpaciousSpaceSelected = spaciousSpaceCheckBox.isChecked();
            boolean isLowSheddingSelected = lowSheddingCheckBox.isChecked();
            boolean isMediumSheddingSelected = mediumSheddingCheckBox.isChecked();
            boolean isHighSheddingSelected = highSheddingCheckBox.isChecked();
            boolean hasAllergies = hasAllergiesCheckBox.isChecked();

            BreedMatcher breedMatcher = new BreedMatcher(breedData);
            BreedMatcher.MatchResult matchResult = breedMatcher.matchBreeds(
                    isSmallSizeSelected, isMediumSizeSelected, isLargeSizeSelected,
                    isLowEnergySelected, isMediumEnergySelected, isHighEnergySelected,
                    isLowGroomingSelected, isMediumGroomingSelected, isHighGroomingSelected,
                    hasChildren, hasOtherPets,
                    isLowTrainabilitySelected, isMediumTrainabilitySelected, isHighTrainabilitySelected,
                    isLowExerciseSelected, isMediumExerciseSelected, isHighExerciseSelected,
                    isApartmentSpaceSelected, isSpaciousSpaceSelected,
                    isLowSheddingSelected, isMediumSheddingSelected, isHighSheddingSelected,
                    hasAllergies
            );

            String resultText = " " + matchResult.getHighestMatchBreed() +" -> " + matchResult.getMatchPercentage() + "%";
            resultTextView.setText(resultText);
        });

        return view;
    }
}
