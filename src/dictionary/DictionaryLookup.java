/*
 * DictionaryLookup.java
 *
 * Created on December 2, 2007, 9:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package dictionary;

import java.util.regex.*;

/**
 *
 * @author zhanshi
 */
public abstract class DictionaryLookup {
    
    protected static String dictionaryName;
            
    /** Creates a new instance of DictionaryLookup */
    public DictionaryLookup() {
    }
    public abstract String getDefinition(String text);
    
    public String sanitize(String text) {
        return text.replaceAll("^[^\\w]*", "").replaceAll("[^\\w]*$", "");
    }
    
    static public String getName() {
        return dictionaryName;
    }

}
