/*
 * Wiktionary.java
 *
 * Created on December 2, 2007, 10:06 PM
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
public class Wiktionary extends DictionaryLookup {
    
    /** Creates a new instance of Wiktionary */
    public Wiktionary() {
        dictionaryName = "Wiktionary";
    }
    
    public String getDefinition(String text) {
        String def = "";
        try {
            // Create an URL instance
            URL url = new URL("http://en.wiktionary.org/wiki/" + sanitize(text));
            URLConnection connection = url.openConnection();
            DataInputStream in = new DataInputStream(connection.getInputStream());
            String line;
            Pattern defPattern = Pattern.compile("<li>(.+)</li>");
            Boolean inDef = false;
            Boolean inDefItem = false;
            while ((line = in.readLine()) != null) {
                if (line.indexOf("<ol>") >= 0) {
                    inDef = true;
                }
                if (inDef) {
                    if (line.indexOf("<li>") >= 0) {
                        inDefItem = true;
                    }
                    if (inDefItem) {
                        def += line.replaceAll("\\<.*?>","");
                    }
                    if (line.indexOf("</li>") >= 0) {
                        if (inDefItem) {
                            def += ".\n";
                        }
                        inDefItem = false;
                    }
                }
                if (line.indexOf("</ol>") >= 0) {
                    inDef = false;
                }
            }
            in.close();
        } catch (Exception e) {}
        return def;
    }
}
