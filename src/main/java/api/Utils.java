package api;

public class Utils {
    private Utils() {
        throw new IllegalStateException("Utility class!");
    }
    
    /**
     * @param src String to be encoded
     * @return Encoded String
     */
    public static String encodeHtml(String src) {
        /*
         * Tak https://www.tutorialspoint.com/how-to-remove-the-html-tags-from-a-given-string-in-java
         */
        return src.replaceAll("\"[^\"]*+\"", "");
    }
    
}
