/*
 * DictionaryLookup.java
 *
 * Created on December 2, 2007, 9:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package dictionary;

/**
 *
 * @author zhanshi
 */
public abstract class DictionaryLookup {
    
    /** Creates a new instance of DictionaryLookup */
    public DictionaryLookup() {
    }
    public abstract String getDefinition(String text);
}
