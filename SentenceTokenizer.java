import java.util.ArrayList;
import java.util.StringTokenizer;

public class SentenceTokenizer {

    // Hold an array variable of all of the tokens
    private final String[] tokens;

    // Constructor for the sentence tokenizer
    public SentenceTokenizer(String sentence) {

        // Initialize a StringTokenizer class to handle basic tokenization (adding hypen and apostrophes as delimeters for Igbo)
        StringTokenizer tokenizer = new StringTokenizer(sentence, " -'!?");

        // Initialize the array for the tokens length amount of tokens
        tokens = new String[tokenizer.countTokens()];

        // Add each token to the array
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokenizer.nextToken();
        }

    }

    // Constructor for the sentence tokenizer with stop words
    public SentenceTokenizer(String sentence, StopWords stopWords) {

        // Initialize a StringTokenizer class to handle basic tokenization (adding hypen and apostrophes as delimeters for Igbo)
        StringTokenizer tokenizer = new StringTokenizer(sentence, " -'!?");

        // Initialize the array for the tokens length amount of tokens
        ArrayList<String> tokenList = new ArrayList<>();

        // Add each token to the array
        for (int i = 0; i < tokenizer.countTokens(); i++) {
            tokenList.add(tokenizer.nextToken());
        }

        // Convert the tokens to an array
        tokens = (String[])(tokenList.toArray());

    }

    // Return the tokens array once requested
    public String[] getTokens() {
        return tokens;
    }

    public static void main(String[] args) {

    }
}