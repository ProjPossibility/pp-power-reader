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
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JSlider;
import javax.vecmath.Vector3f;
import speech.Speech;

/**
 *
 * @author Christopher Leung
 */
public class ConfigurationManager {
    
    static float DEFAULT_X = 0.0f;
    static float DEFAULT_Y = 0.0f;
    static float DEFAULT_Z = -25.0f;
    static float MIN_Z = -46.0f;
    static float MAX_Z = 1.0f;
    
    static float current_x = DEFAULT_X;
    static float current_y = DEFAULT_Y;
    static float current_z = DEFAULT_Z;
    
    static private ConfigurationManager m_instance;
    
    private ArrayList dictionaries;
    private DictionaryLookup dictionaryLookup;
    
    private ArrayList imageFetchers;
    private ImageFetcher imageFetcher;
    
    private boolean audibleSpeech = true;
    private boolean showImages = false;
    private boolean wordsGrow = false;
    private boolean followFocus = false;
    
    private TransformGroup m_mainTransformGroup = null;
    
    private String fileName = null;
    
    private JSlider m_zoomSlider = null;
    static {
        m_instance = new ConfigurationManager();
        
    }
    
    static public void setMainTransformGroup(TransformGroup root) {
        m_instance.m_mainTransformGroup = root;
    }
    
    static public TransformGroup getMainTransformGroup() {
        return m_instance.m_mainTransformGroup;
    }
    static public void setZoomSlider(JSlider slider) {
        m_instance.m_zoomSlider = slider;
    }
    
    static public void toggleAudibleSpeech() {
        if(m_instance.audibleSpeech) {
            m_instance.audibleSpeech = false;
            Speech.mute();
        } else {
            m_instance.audibleSpeech = true;
            Speech.unmute();
        }
    }
    
    static public void toggleFollowFocus() {
        if(m_instance.followFocus) {
            m_instance.followFocus = false;
        } else {
            m_instance.followFocus = true;
        }
    }
    static public void toggleShowImages() {
        if(m_instance.showImages) {
            m_instance.showImages = false;
        } else {
            m_instance.showImages = true;
        }
    }
    static public void toggleWordsGrow() {
        if(m_instance.wordsGrow) {
            m_instance.wordsGrow = false;
        } else {
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
        imageFetcher = (ImageFetcher) imageFetchers.get(1);
        
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
    static public boolean showImages() {
        return m_instance.showImages;
    }
    static public boolean followFocus() {
        return m_instance.followFocus;
    }
    
    static public boolean showImages() {
        return m_instance.showImages;
    }
    
    static public void refreshTranslate() {
        if(m_instance.m_mainTransformGroup != null) {
            // Cap current_z if we need to
            if(current_z > MAX_Z) {
                current_z = MAX_Z;
            } else if (current_z < MIN_Z) {
                current_z = MIN_Z;
            }
            Transform3D transform = new Transform3D();
            transform.setTranslation(new Vector3f(current_x,current_y,current_z));
            m_instance.m_mainTransformGroup.setTransform(transform);
            
            if(m_instance.m_zoomSlider != null) {
                m_instance.m_zoomSlider.setValue((int)m_instance.current_z);
            }
        }
    }
    
    static public void setCurrentFileName(String filename) {
        m_instance.fileName = filename;
    }
    
    static public String getCurrentFileName(String filename) {
        return m_instance.fileName;
    }
}
