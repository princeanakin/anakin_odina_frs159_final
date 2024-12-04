public class Preprocessing {

    public static String cleanText(String text) {
        String cleanedText = text;
        
        // Remove hashtags
        cleanedText = cleanedText.replaceAll("#", "");

        // Remove users
        cleanedText = cleanedText.replaceAll("@user", "");

        // Remove hyperlinks (https://stackoverflow.com/questions/163360/regular-expression-to-match-urls-in-java)
        cleanedText = cleanedText.replaceAll("\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", "");

        return cleanedText;
    }
    
    public static void main(String[] args) {
        
        System.out.println(Preprocessing.cleanText(args[0]));

    }

}