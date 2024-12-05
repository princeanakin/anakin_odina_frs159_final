import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class FrequencyCounter {

    // Create HashMaps to hold the frequency counts
    private final HashMap<String, Integer> positiveFrequencies;
    private final HashMap<String, Integer> negativeFrequencies;
    private final HashMap<String, Integer> neutralFrequencies;
    private final HashMap<String, Boolean> uniqueWords;

    // Create ints to store the total number of words of each type of tweet
    private int totalPositive;
    private int totalNegative;
    private int totalNeutral;

    // Create the constructor for the FrequencyCounter from the given tweet training data
    public FrequencyCounter(File file) throws FileNotFoundException {

        // Initialize the frequency HashMaps for words as keys and frequency integers as values
        positiveFrequencies = new HashMap<>();
        negativeFrequencies = new HashMap<>();
        neutralFrequencies = new HashMap<>();
        uniqueWords = new HashMap<>();

        try (// Scan the given file
             Scanner scan = new Scanner(file)) {
            // Go through the entire file training tweets
            while (scan.hasNextLine()) {
                // Store current tweet
                String currentTweet = scan.nextLine();
                // Preprocess tweet
                Preprocessing.cleanText(currentTweet);
                // Create a SentenceTokenizer object for each sentence
                SentenceTokenizer tokenizer = new SentenceTokenizer(scan.nextLine());
                // Add the tokens to an array of strings
                String[] tokenized = tokenizer.getTokens();

                // Store the sentiment value of the given tweet
                String sentiment = tokenized[tokenized.length - 1].trim();

                System.out.println(sentiment);
                System.out.println(sentiment.contains("negative"));

                if (sentiment.contains("positive")) {
                    // If the given sentence is labeled as positive, add to positive frequencies
                    // Go through every token
                    for (String s : tokenized) {
                        // If it is a new word, add it with a base frequency one to the hash table
                        if (!positiveFrequencies.containsKey(s)) {
                            positiveFrequencies.put(s, 1);
                        } else {
                            // Otherwise, increment its frequency
                            positiveFrequencies.put(s, positiveFrequencies.get(s) + 1);
                        }
                        uniqueWords.put(s, true);
                    }
                    // If the given sentence is labeled as negative, add to negative frequencies
                } else if (sentiment.contains("negative")) {
                    for (String s : tokenized) {
                        // If it is a new word, add it with a base frequency one to the hash table
                        if (!negativeFrequencies.containsKey(s)) {
                            negativeFrequencies.put(s, 1);
                        } else {
                            // Otherwise, increment its frequency
                            negativeFrequencies.put(s, negativeFrequencies.get(s) + 1);
                        }
                        uniqueWords.put(s, true);
                    }
                }
                // If the given sentence is labeled as neutral, add to neutral frequencies
                else if (sentiment.contains("neutral")) {
                    for (String s : tokenized) {
                        // If it is a new word, add it with a base frequency one to the hash table
                        if (!neutralFrequencies.containsKey(s)) {
                            neutralFrequencies.put(s, 1);
                        } else {
                            // Otherwise, increment its frequency
                            neutralFrequencies.put(s, neutralFrequencies.get(s) + 1);
                        }
                        uniqueWords.put(s, true);
                    }
                } else throw new IllegalArgumentException("No label on tweet");
            }
        }
    }

    // Method to get the HashMap of positive frequencies
    public HashMap<String, Integer> getPositiveFrequencies() {
        return positiveFrequencies;
    }

    // Method to get the HashMap of negative frequencies
    public HashMap<String, Integer> getNegativeFrequencies() {
        return negativeFrequencies;
    }

    // Method to get the HashMap of neutral frequencies
    public HashMap<String, Integer> getNeutralFrequencies() {
        return neutralFrequencies;
    }

    // Method to get the positive words
    public Set<String> getPositiveKeys() {
        return positiveFrequencies.keySet();
    }

    // Method to get the negative words
    public Set<String> getNegativeKeys() {
        return negativeFrequencies.keySet();
    }

    // Method to get the neutral words
    public Set<String> getNeutralKeys() {
        return neutralFrequencies.keySet();
    }

    // Method to get all unique words
    public Set<String> getWords() {
        return uniqueWords.keySet();
    }

    // Method to return # number of words in positive tweets
    public int totalPos() {
        return totalPositive;
    }

    // Method to return # number of words in negative tweets
    public int totalNeg() {
        return totalNegative;
    }

    // Method to return # number of words in neutral tweets
    public int totalNeu() {
        return totalNeutral;
    }

    // Testing
    public static void main(String[] args) throws FileNotFoundException {

        File file = new File(args[0]);

        FrequencyCounter test = new FrequencyCounter(file);

        for (String s : test.getPositiveKeys()) {
            System.out.println(s + " " + test.getPositiveFrequencies().get(s));
        }

        System.out.println();

        for (String s : test.getNegativeKeys()) {
            System.out.println(s + " " + test.getNegativeFrequencies().get(s));
        }

    }
}
