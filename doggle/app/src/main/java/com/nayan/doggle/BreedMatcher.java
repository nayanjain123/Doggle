package com.nayan.doggle;

import java.util.List;

public class BreedMatcher {

    private final List<String[]> breedData;

    public BreedMatcher(List<String[]> breedData) {
        this.breedData = breedData;
    }

    public MatchResult matchBreeds(boolean isSmallSizeSelected, boolean isMediumSizeSelected, boolean isLargeSizeSelected,
                                   boolean isLowEnergySelected, boolean isMediumEnergySelected, boolean isHighEnergySelected,
                                   boolean isLowGroomingSelected, boolean isMediumGroomingSelected, boolean isHighGroomingSelected,
                                   boolean hasChildren, boolean hasOtherPets, boolean isLowTrainabilitySelected,
                                   boolean isMediumTrainabilitySelected, boolean isHighTrainabilitySelected,
                                   boolean isLowExerciseSelected, boolean isMediumExerciseSelected, boolean isHighExerciseSelected,
                                   boolean isApartmentSpaceSelected, boolean isSpaciousSpaceSelected, boolean isLowSheddingSelected,
                                   boolean isMediumSheddingSelected, boolean isHighSheddingSelected, boolean hasAllergies) {

        int totalQuestions = 11; // Total number of questions

        int numMatches;
        double matchPercentage;

        String highestMatchBreed = "";
        int maxMatches = 0;

        for (String[] breed : breedData) {
            numMatches = 0;

            // Question 1: Size
            if ((isSmallSizeSelected && breed[1].equals("Small"))
                    || (isMediumSizeSelected && breed[1].equals("Medium"))
                    || (isLargeSizeSelected && breed[1].equals("Large"))) {
                numMatches++;
            }

            // Question 2: Energy Level
            if ((isLowEnergySelected && breed[2].equals("Low"))
                    || (isMediumEnergySelected && breed[2].equals("Moderate"))
                    || (isHighEnergySelected && breed[2].equals("High"))) {
                numMatches++;
            }

            // Question 3: Grooming
            if ((isLowGroomingSelected && breed[3].equals("Low"))
                    || (isMediumGroomingSelected && breed[3].equals("Moderate"))
                    || (isHighGroomingSelected && breed[3].equals("High"))) {
                numMatches++;
            }

            // Question 4: Children
            if ((hasChildren && breed[4].equals("Yes")) || (!hasChildren && breed[4].equals("No"))) {
                numMatches++;
            }

            // Question 5: Other Pets
            if ((hasOtherPets && breed[5].equals("Yes")) || (!hasOtherPets && breed[5].equals("No"))) {
                numMatches++;
            }

            // Question 6: Trainability
            if ((isLowTrainabilitySelected && breed[6].equals("Low"))
                    || (isMediumTrainabilitySelected && breed[6].equals("Moderate"))
                    || (isHighTrainabilitySelected && breed[6].equals("High"))) {
                numMatches++;
            }

            // Question 7: Exercise
            if ((isLowExerciseSelected && breed[7].equals("Low"))
                    || (isMediumExerciseSelected && breed[7].equals("Moderate"))
                    || (isHighExerciseSelected && breed[7].equals("High"))) {
                numMatches++;
            }

            // Question 8: Living Space
            if ((isApartmentSpaceSelected && breed[8].equals("Apartment"))
                    || (isSpaciousSpaceSelected && breed[8].equals("Spacious Yard"))) {
                numMatches++;
            }

            // Question 9: Shedding
            if ((isLowSheddingSelected && breed[9].equals("Low"))
                    || (isMediumSheddingSelected && breed[9].equals("Moderate"))
                    || (isHighSheddingSelected && breed[9].equals("High"))) {
                numMatches++;
            }

            // Question 10: Allergies
            if ((hasAllergies && breed[10].equals("Yes")) || (!hasAllergies && breed[10].equals("No"))) {
                numMatches++;
            }

            // Calculate the match percentage for the breed
            matchPercentage = (numMatches / (double) totalQuestions) * 100;

            // Find the breed with the highest match percentage
            if (numMatches > maxMatches) {
                maxMatches = numMatches;
                highestMatchBreed = breed[0]; // Assuming the breed name is stored in the first column
            }
        }

        return new MatchResult(highestMatchBreed, maxMatches, totalQuestions);
    }

    public static class MatchResult {
        private final String highestMatchBreed;
        private final int numMatches;
        private final int totalQuestions;

        public MatchResult(String highestMatchBreed, int numMatches, int totalQuestions) {
            this.highestMatchBreed = highestMatchBreed;
            this.numMatches = numMatches;
            this.totalQuestions = totalQuestions;
        }

        public String getHighestMatchBreed() {
            return highestMatchBreed;
        }

        public double getMatchPercentage() {
            return (numMatches / (double) totalQuestions) * 100;
        }
    }
}
