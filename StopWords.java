import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class StopWords {

    // Create a HashMap to be able to quickly access a stop word's existence
    private HashMap<String, Boolean> stopWords = new HashMap<>();

    // Return the HashMap of the stop words
    public HashMap<String, Boolean> getStopWords() {
        return stopWords;
    }
    
    // Set the stop words to a new HashMap
    public void setStopWords(HashMap<String, Boolean> stopWords) {
        this.stopWords = stopWords;
    }

    // Construct the list of stop words from a list file
    public StopWords(File list) throws FileNotFoundException {
        stopWords = new HashMap<>();

        // Go through each word in the stop word list and add them to the HashMap
        try (Scanner scan = new Scanner(list)) {
            while (scan.hasNext()) {
                stopWords.put(scan.next(), true);
            }
        }

    }

    public static void main(String[] args) {
        
    }
}