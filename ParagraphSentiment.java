import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class ParagraphSentiment {


    public ParagraphSentiment(File paragraph, String splitParagraphFile, File stopWords, FrequencyCounter freqCount, WeightedWords weights) throws IOException {

        ParagraphSplitter.splitParagraph(paragraph, splitParagraphFile);

        StopWords igboStop = new StopWords(stopWords);

        File splitParagraph = new File(splitParagraphFile);

        Scanner scan2 = new Scanner(splitParagraph);
    
        HashMap<String,Integer> posFreq = freqCount.getPositiveFrequencies();
        HashMap<String,Integer> negFreq = freqCount.getNegativeFrequencies();
        HashMap<String,Integer> neuFreq = freqCount.getNeutralFrequencies();
        Set<String> uniqueFreq = freqCount.getWords();

        double chancePositive = 1;
        double chanceNegative = 1;
        double chanceNeutral = 1;

        while (scan2.hasNextLine()) {
            SentenceTokenizer currentTokens = new SentenceTokenizer(scan2.nextLine(), igboStop);

            for (String token : currentTokens.getTokens()) {

                int posFrequency = 0;
                int negFrequency = 0;
                int neuFrequency = 0;

                if (posFreq.containsKey(token)) {
                    posFrequency += posFreq.get(token);
                }

                if (negFreq.containsKey(token)) {
                    negFrequency += negFreq.get(token);
                }

                if (neuFreq.containsKey(token)) {
                    neuFrequency += neuFreq.get(token);
                }

                chancePositive *= (posFrequency + 1) / (posFreq.size() + uniqueFreq.size());

                chanceNegative *= (negFrequency + 1) / (negFreq.size() + uniqueFreq.size());

                chanceNeutral *= (neuFrequency + 1) / (neuFreq.size() + uniqueFreq.size());

            }

        }

    }

    public static void main(String[] args) {

    }
}