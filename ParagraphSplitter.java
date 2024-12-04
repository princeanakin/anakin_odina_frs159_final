import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.BreakIterator;
import java.util.Locale;
import java.util.Scanner;

public class ParagraphSplitter {

    // static method that takes an input and output file and converts the paragraph/text into a list of sentences
    public static void splitParagraph(File input, String output) throws IOException {

        Scanner scan = new Scanner(input);

        // Initialize a StringBuilder to hold all the input text in one string
        StringBuilder paragraph = new StringBuilder();

        // Initialize a FileWriter to convert the split strings into a list of sentences
        FileWriter fileWriter = new FileWriter(output, true);

        // Initialize a PrintWriter to be able to output the input
        PrintWriter out = new PrintWriter(fileWriter);

        // Go through the input and append the text data to one big string variable
        while (scan.hasNextLine()) {
            // Add each line
            paragraph.append(scan.nextLine());
        }

        // Initialize a BreakIterator class with English punctuation as the parameter
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.ENGLISH);

        // Set the text for the iterator to work through as the paragraph
        iterator.setText(paragraph.toString());

        // Create integer variables for the iterator indices
        int now = iterator.first();
        int next = iterator.next();

        // Go through the text data and break up each sentence into a substring
        while (next != BreakIterator.DONE) {
            // Create a sentence substring for each sentence
            String sentence = paragraph.substring(now, next).trim();

            // This is to add each sentence to the end input
            out.println(sentence);

            // Increment the iterator indices
            now = next;
            next = iterator.next();
        }

        // Finishes writing to the file
        out.close();

    }

    public static void main(String[] args) throws IOException {

        // Import a text file containing the given paragraph
        File file = new File(args[0]);

        // Split the paragraph
        ParagraphSplitter.splitParagraph(file, args[1]);

    }
}
