import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class WeightedWords {

    // Have a HashMap to store all of the intensities of the given vocabulary
    private static HashMap<String,Double> wordIntensities = new HashMap<>();

    // Construct the weighted word
    public WeightedWords() {
        wordIntensities = new HashMap<>();
    }

    // Construct a set of Word Intensities given a file with each word and intensity on each line
    public WeightedWords(File file) throws FileNotFoundException {
        wordIntensities = new HashMap<>();

        // Scan the inputted file
        Scanner scan = new Scanner(file);

        // Go through each line of the inputted file
        while (scan.hasNextLine()) {
            // Each line should start with the given word
            String nextWord;
            // Each line should have its own weight between the two limits set
            double nextWeight;

            // Add the next word
            if (scan.hasNext()) {
                nextWord = scan.next();
            } else {
                // Break if we are done
                break;
            }

            // Add the next weight
            if (scan.hasNextDouble()) {
                nextWeight = scan.nextDouble();
                // Checks to make sure that the weights are in the right limits
                if (nextWeight < 0 || nextWeight > 1) throw new IllegalArgumentException("Weight should be [0,1]");
            } else {
                // Break if done
                break;
            }

            // Encode the word's weight to the HashMap
            wordIntensities.put(nextWord, nextWeight);
        }

    }

    // Return the HashMap of weighted words
    public HashMap<String,Double> getIntensities() {
        return wordIntensities;
    }

    // Set the intensity of a new word or change a current one
    public void setIntensity(String word, double intensity) {
        wordIntensities.put(word, intensity);
    }

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File(args[0]);

        WeightedWords set = new WeightedWords(file);

        for (String s : set.getIntensities().keySet()) {
            System.out.print(s + " " + set.getIntensities().get(s));
            System.out.println();
        }
    }
}
