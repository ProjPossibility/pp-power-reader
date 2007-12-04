/*
 * WordNetDictionary.java
 *
 * Created on December 2, 2007, 9:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package dictionary;
import java.io.*;
import java.net.*;
import java.util.regex.*;

/**
 *
 * @author zhanshi
 */
public class WordNetDictionary extends DictionaryLookup {
    
    /** Creates a new instance of WordNetDictionary */
    public WordNetDictionary() {
        dictionaryName = "WordNet";
    }
    
    public String getDefinition(String text) {
        String def = "";
        try {
            // Create an URL instance
            URL url = new URL("http://wordnet.princeton.edu/perl/webwn?s=" + text);
            URLConnection connection = url.openConnection();
            DataInputStream in = new DataInputStream(connection.getInputStream());
            String line;
            Pattern defPattern = Pattern.compile("<li>(.+)</li>");
            while ((line = in.readLine()) != null) {
                Matcher match = defPattern.matcher(line);
                if (match.find()) {
                    def += "\n" + match.group(1).replaceAll("\\<.*?>","");
                }
            }
            in.close();
        } catch (Exception e) {}
        return def;
    }
}
