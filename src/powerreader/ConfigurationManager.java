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
import speech.Speech;

/**
 *
 * @author Christopher Leung
 */
public class ConfigurationManager {
    
    static float DEFAULT_X = 0.0f;
    static float DEFAULT_Y = 0.0f;
    static float DEFAULT_Z = -25.0f;
    static private ConfigurationManager m_instance;
    
    private ArrayList dictionaries;
    private DictionaryLookup dictionaryLookup;
    
    private ArrayList imageFetchers;
    private ImageFetcher imageFetcher;
    
    private boolean audibleSpeech = true;
    private boolean showImages = false;
    private boolean wordsGrow = false;
    
    static {
        m_instance = new ConfigurationManager();
        
    }
    
    static public void toggleAudibleSpeech() {
        if(m_instance.audibleSpeech) {
            m_instance.audibleSpeech = false;
            Speech.mute();
        }
        else {
            m_instance.audibleSpeech = true;
            Speech.unmute();
        }
    }
    static public void toggleShowImages() {
        if(m_instance.showImages) {
            m_instance.showImages = false;
        }
        else {
            m_instance.showImages = true;
        }
    }
    static public void toggleWordsGrow() {
        if(m_instance.wordsGrow) {
            m_instance.wordsGrow = false;
        }
        else {
            m_instance.wordsGrow = true;
        }
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
        return m_instance.imageFetchers;
    }
    
    static public ConfigurationManager getInstnace() {
        return m_instance;
    }
    
    static public void setDictionary(int index) {
        m_instance.dictionaryLookup = (DictionaryLookup)m_instance.dictionaries.get(index);
    }
    
    static public void setImageLookup(int index) {
        m_instance.imageFetcher = (ImageFetcher)m_instance.imageFetchers.get(index);
    }
    
    static public boolean wordsGrow() {
        return m_instance.wordsGrow;
    }
}
