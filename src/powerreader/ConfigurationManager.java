/*
 * ConfigurationManager.java
 *
 * Created on December 4, 2007, 2:09 PM
 *
 * Contains all settings
 *
 */

package powerreader;

import dictionary.DictionaryLookup;
import dictionary.Wiktionary;
import dictionary.WordNetDictionary;
import image.FlickrImageFetcher;
import image.GoogleImageFetcher;
import image.ImageFetcher;
import image.YahooImageFetcher;
import java.util.ArrayList;

/**
 *
 * @author Christopher Leung
 */
public class ConfigurationManager {
   
    static private ConfigurationManager m_instance;
    
    private ArrayList dictionaries;
    private DictionaryLookup dictionaryLookup;
    
    private ArrayList imageFetchers;
    private ImageFetcher imageFetcher;
    
    static {
        m_instance = new ConfigurationManager();
        
    }
    
    public ConfigurationManager() {
        // Dictionaries 
        
        dictionaries = new ArrayList();
        dictionaries.add(new Wiktionary());
        dictionaries.add(new WordNetDictionary());
        
        // Set default dictionary
        dictionaryLookup = (DictionaryLookup) dictionaries.get(0);
        
        // Images
        imageFetchers = new ArrayList();
        imageFetchers.add(new FlickrImageFetcher());
        imageFetchers.add(new GoogleImageFetcher());
        imageFetchers.add(new YahooImageFetcher());
       
        // Set default image lookups
        imageFetcher = (ImageFetcher) imageFetchers.get(0);

        // Fonts
        
        // Voices
        
    }
    
    static public DictionaryLookup getDictionary() {
        return m_instance.dictionaryLookup;
    }
    
    static public ArrayList getDictionaryList() {
        return m_instance.dictionaries;
    }
    
    static public ImageFetcher getImageFetcher() {
        return m_instance.imageFetcher;
    }
    
    static public ArrayList getImageFetcherList() {
        return m_instance.m_imageFetchers;
    }
}
