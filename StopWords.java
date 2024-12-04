import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class StopWords {

    private HashMap<String, Boolean> stopWords = new HashMap<>();

    public HashMap<String, Boolean> getStopWords() {
        return stopWords;
    }

    public void setStopWords(HashMap<String, Boolean> stopWords) {
        this.stopWords = stopWords;
    }

    public StopWords(File list) throws FileNotFoundException {
        stopWords = new HashMap<>();

        try (Scanner scan = new Scanner(list)) {
            while (scan.hasNext()) {
                stopWords.put(scan.next(), true);
            }
        }

    }

    public static void main(String[] args) {
        
    }
}