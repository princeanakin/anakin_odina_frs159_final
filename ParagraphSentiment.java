import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class ParagraphSentiment {

    double chancePositive;
    double chanceNegative;
    double chanceNeutral;

    public ParagraphSentiment(File paragraph, String splitParagraphFile, File stopWords, FrequencyCounter freqCount) throws IOException {

        ParagraphSplitter.splitParagraph(paragraph, splitParagraphFile);

        StopWords igboStop = new StopWords(stopWords);

        File splitParagraph = new File(splitParagraphFile);

        try (Scanner scan2 = new Scanner(splitParagraph)) {
            HashMap<String,Integer> posFreq = freqCount.getPositiveFrequencies();
            HashMap<String,Integer> negFreq = freqCount.getNegativeFrequencies();
            HashMap<String,Integer> neuFreq = freqCount.getNeutralFrequencies();
            Set<String> uniqueFreq = freqCount.getWords();

            chancePositive = 1;
            chanceNegative = 1;
            chanceNeutral = 1;

            while (scan2.hasNextLine()) {
                SentenceTokenizer currentTokens = new SentenceTokenizer(scan2.nextLine(), igboStop);

                for (String token : currentTokens.getTokens()) {

                    double posFrequency = 0;
                    double negFrequency = 0;
                    double neuFrequency = 0;

                    if (posFreq.containsKey(token)) {
                        posFrequency += posFreq.get(token);
                    }

                    if (negFreq.containsKey(token)) {
                        negFrequency += negFreq.get(token);
                    }

                    if (neuFreq.containsKey(token)) {
                        neuFrequency += neuFreq.get(token);
                    }

                    chancePositive *= (posFrequency + 1) / (freqCount.totalPos() + uniqueFreq.size());

                    chanceNegative *= (negFrequency + 1) / (freqCount.totalNeg() + uniqueFreq.size());

                    chanceNeutral *= (neuFrequency + 1) / (freqCount.totalNeu() + uniqueFreq.size());

                }

            }
        }

    }

    public double positiveChance() {
        return chancePositive;
    }

    public double negativeChance() {
        return chanceNegative;
    }

    public double neutralChance() {
        return chanceNeutral;
    }

    public static void main(String[] args) throws IOException {

        File paragraph = new File(args[0]);

        String splitParagraphFile = args[1];

        File stopWords = new File(args[2]);

        String trainFileName = args[3];

        FrequencyCounter freqCount = new FrequencyCounter(new File(trainFileName));

        ParagraphSentiment test = new ParagraphSentiment(paragraph, splitParagraphFile, stopWords, freqCount);

        System.out.println(test.chanceNegative);

        System.out.println(test.chancePositive);

        System.out.println(test.chanceNeutral);

    }
}
