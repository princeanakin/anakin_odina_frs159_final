import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class FrequencyCounter {

    // Create HashMaps to hold the frequency counts
    private final HashMap<String, Integer> positiveFrequencies;
    private final HashMap<String, Integer> negativeFrequencies;

    // Create the constructor for the FrequencyCounter
    public FrequencyCounter(File file) throws FileNotFoundException {

        // Initialize the frequency HashMaps for words as keys and frequency integers as values
        positiveFrequencies = new HashMap<String, Integer>();
        negativeFrequencies = new HashMap<String, Integer>();

        try (// Scan the given file
        Scanner scan = new Scanner(file)) {
            // Go through the entire file of split paragraphs into sentences
            while (scan.hasNextLine()) {
                // Create a SentenceTokenizer object for each sentence
                SentenceTokenizer tokenizer = new SentenceTokenizer(scan.nextLine());
                // Add the tokens to an array of strings
                String[] tokenized = tokenizer.getTokens();

                // If the given sentence is labeled as positive, add to positive frequencies
                if (tokenized[tokenized.length - 1].equals("positive")) {
                    // Go through every token
                    for (String s : tokenized) {
                        // If it is a new word, add it with a base frequency one to the hash table
                        if (!positiveFrequencies.containsKey(s)) {
                            positiveFrequencies.put(s, 1);
                        } else {
                            // Otherwise, increment its frequency
                            positiveFrequencies.put(s, positiveFrequencies.get(s) + 1);
                        }
                    }
                }
                // If the given sentence is labeled as negative, add to negative frequencies
                else if (tokenized[tokenized.length - 1].equals("negative")) {
                    for (String s : tokenized) {
                        // If it is a new word, add it with a base frequency one to the hash table
                        if (!negativeFrequencies.containsKey(s)) {
                            negativeFrequencies.put(s, 1);
                        } else {
                            // Otherwise, increment its frequency
                            negativeFrequencies.put(s, positiveFrequencies.get(s) + 1);
                        }
                    }
                }

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

    // Method to get the positive words
    public Set<String> getPositiveKeys() {
        return positiveFrequencies.keySet();
    }

    // Method to get the negative words
    public Set<String> getNegativeKeys() {
        return negativeFrequencies.keySet();
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
