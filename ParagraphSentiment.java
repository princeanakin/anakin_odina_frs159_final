import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class ParagraphSentiment {

    // Store doubles to hold the levels of positivity, negativity, and neutrality of the paragraph
    double positiveLevel;
    double negativeLevel;
    double neutralLevel;
    // Stores a double to hold the total level
    double totalLevel;

    // Construct a sentiment analysis using the original paragraph, a file to save the sentence version, a list of stop words to remove during tokenization, and a frequency counter based on training data
    public ParagraphSentiment(File paragraph, String splitParagraphFile, File stopWords, FrequencyCounter freqCount, WeightedWords weights) throws IOException {

        // Split the paragraph into sentences
        ParagraphSplitter.splitParagraph(paragraph, splitParagraphFile);

        // Create a stop words object with the given list of stop words
        StopWords igboStop = new StopWords(stopWords);

        // Take in the file with the split up paragraph
        File splitParagraph = new File(splitParagraphFile);

        // Scan through these split up sentences
        try (Scanner scan2 = new Scanner(splitParagraph)) {
            // For sake of easier readability, reference the HashMaps from the frequency counter
            HashMap<String,Integer> posFreq = freqCount.getPositiveFrequencies();
            HashMap<String,Integer> negFreq = freqCount.getNegativeFrequencies();
            HashMap<String,Integer> neuFreq = freqCount.getNeutralFrequencies();
            Set<String> uniqueFreq = freqCount.getWords();

            // Initialize the levels for the paragraph at zero
            positiveLevel = 0;
            negativeLevel = 0;
            neutralLevel = 0;

            while (scan2.hasNextLine()) {
                // Tokenize the sentence
                SentenceTokenizer currentTokens = new SentenceTokenizer(scan2.nextLine(), igboStop);

                for (String token : currentTokens.getTokens()) {

                    // Initialize the frequencies for the word
                    double posFrequency = 0;
                    double negFrequency = 0;
                    double neuFrequency = 0;

                    // Sets intensity to 1 if not already specified
                    if (!weights.getIntensities().containsKey(token)) {
                        weights.getIntensities().put(token, 1.0);
                    }

                    // Get the intensity of the word
                    double wordIntensity = weights.getIntensities().get(token);

                    // Set the frequencies to their respective values only if they exist in the training data
                    if (posFreq.containsKey(token)) {
                        posFrequency = posFreq.get(token);
                    }

                    if (negFreq.containsKey(token)) {
                        negFrequency = negFreq.get(token);
                    }

                    if (neuFreq.containsKey(token)) {
                        neuFrequency = neuFreq.get(token);
                    }

                    // Using the formula from chapter 4 (version with Laplace smoothing), calculate and add to each level for each frequency
                    positiveLevel += ((posFrequency + 1) / (freqCount.totalPos() + uniqueFreq.size()) * wordIntensity);

                    negativeLevel += ((negFrequency + 1) / (freqCount.totalNeg() + uniqueFreq.size()) * wordIntensity);

                    neutralLevel += ((neuFrequency + 1) / (freqCount.totalNeu() + uniqueFreq.size()) * wordIntensity);

                    // Total level will be the three levels combined
                    totalLevel = positiveLevel + negativeLevel + neutralLevel;

                }

            }
        }

    }

    // The model's confidence that it will be this sentiment is the sentiment's level divided by the total sentiment level, outputting a decimal from 0 to 1
    public double positiveChance() {
        return positiveLevel / totalLevel;
    }

    public double negativeChance() {
        return negativeLevel / totalLevel;
    }

    public double neutralChance() {
        return neutralLevel / totalLevel;
    }

    // This is for testing
    public static void main(String[] args) throws IOException {

        File paragraph = new File(args[0]);

        String splitParagraphFile = args[1];

        File stopWords = new File(args[2]);

        String trainFileName = args[3];

        FrequencyCounter freqCount = new FrequencyCounter(new File(trainFileName));

        WeightedWords weightedWords = new WeightedWords();

        ParagraphSentiment test = new ParagraphSentiment(paragraph, splitParagraphFile, stopWords, freqCount, weightedWords);

        System.out.println(test.positiveChance() * 100 + "% Positive");

        System.out.println(test.negativeChance() * 100 + "% Negative");

        System.out.println(test.neutralChance() * 100 + "% Neutral");

    }
}
