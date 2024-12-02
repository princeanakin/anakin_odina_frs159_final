import java.io.File;
import java.io.FileNotFoundException;
import java.text.BreakIterator;
import java.util.Locale;
import java.util.Scanner;

public class ParagraphSplitter {
    public static void main(String[] args) throws FileNotFoundException {

        // Import a text file containing the given paragraph
        File file = new File(args[0]);

        try (// Make a scanner class to read the given file with the paragraph
        Scanner scan = new Scanner(file)) {
            // Initialize a string variable for the paragraph
            String paragraph = "";

            // Go through the file and append the text data to one big string variable
            while (scan.hasNextLine()) {
                // Add each line
                paragraph += scan.nextLine();
            }

            // Initialize a BreakIterator class with English punctuation as the parameter
            BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.ENGLISH);

            // Set the text for the iterator to work through as the paragraph
            iterator.setText(paragraph);

            // Create integer variables for the iterator indices
            int now = iterator.first();
            int next = iterator.next();

            // Go through the text data and break up each sentence into a substring
            while (next != BreakIterator.DONE) {
                // Create a sentence substring for each sentence
                String sentence = paragraph.substring(now, next).trim();

                // This is to print out each sentence
                System.out.println(sentence);

                // Increment the iterator indices
                now = next;
                next = iterator.next();
            }
        }
    }
}
