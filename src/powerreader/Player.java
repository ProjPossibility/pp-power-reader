/*
 * Player.java
 *
 * Created on December 4, 2007, 2:18 AM
 *
 * Play methods for the HierarchyObjects
 *
 */

package powerreader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import speech.Speech;
import util.HierarchyObject;
import util.RawTextParser;
import util.TextObject3d;

/**
 *
 * @author Christopher Leung
 */
public class Player extends Thread {
    public static int WPM_FACTOR = 120000;
    public static int DEFAULT_SLEEP_DELAY = 800;
    
    private HierarchyObject m_root;
    private int m_focusLevel;
    
    private int m_sleepDelay;
    
    private boolean m_growEnabled;
    private float m_highlightColorR;
    private float m_highlightColorG;
    private float m_highlightColorB;
    private float m_baseColorR;
    private float m_baseColorB;
    private float m_baseColorG;
    
    // The objects to speak, and the item we are focused on
    private ArrayList m_objectsToSpeak;
    private int m_focusIndex;
    
    private Object m_ready;
    
    /** Creates a new instance of Player */
    public Player(HierarchyObject root, int focusLevel) {
        m_root = root;
        
        // Default to word level focus
        m_focusLevel = RawTextParser.LEVEL_WORD_ID;
        
        // Default colors
        m_highlightColorR = 1.0f;
        m_highlightColorG = 1.0f;
        m_highlightColorB = 1.0f;
        
        m_baseColorR = 1.0f;
        m_baseColorG = 1.0f;
        m_baseColorB = 1.0f;
        
        m_focusLevel = focusLevel;
        m_focusIndex = 0;
        m_objectsToSpeak = m_root.getAllChildrenOfLevel(m_focusLevel);
        
        m_ready = new Object();
        
        m_sleepDelay = DEFAULT_SLEEP_DELAY;
    }
    
    public void setSleepDelay(int delay) {
        m_sleepDelay = delay;
        Speech.setSpeed(WPM_FACTOR/delay);
    }
    
    public HierarchyObject getFocusOn() {
        return (HierarchyObject)m_objectsToSpeak.get(m_focusIndex);
    }
    public void setFocusLevel(int focusLevel) {
        // Get all objects on this level
        m_focusLevel = focusLevel;
        m_focusIndex = 0;
        m_objectsToSpeak = m_root.getAllChildrenOfLevel(m_focusLevel);
    }
    
    public boolean setFocusOn(HierarchyObject hObj) {
        int findOnLevel = hObj.getLevel();
        ArrayList objectsToSearch = m_root.getAllChildrenOfLevel(findOnLevel);
        Iterator it = objectsToSearch.iterator();
        int searchIndex = 0;
        while(it.hasNext()) {
            Object objToTest = it.next();
            if(objToTest.equals(hObj)) {
                m_focusLevel = findOnLevel;
                m_focusIndex = searchIndex;
                return true;
            }
            searchIndex++;
        }
        return false;
    }
    
    public void setGrowEnable(boolean active) {
        m_growEnabled = active;
    }
    
    public void setHighlightColor(float r, float g, float b) {
        m_highlightColorR = r;
        m_highlightColorG = g;
        m_highlightColorB = b;
    }
    
    public void setBaseColor(float r, float g, float b) {
        m_baseColorR = r;
        m_baseColorG = g;
        m_baseColorB = b;
    }
    
    public void run() {
        
        HierarchyObject currentObj = null;
        
        for(;;) {
//            synchronized(m_ready) {
                for(int i = m_focusIndex; i < m_objectsToSpeak.size(); i++) {
                    // Get current obj
                    currentObj = (HierarchyObject)m_objectsToSpeak.get(i);

                    // Start speaking the object
                    Speech.speak(currentObj.getValue());

                    // Highlight/grow the current object
                    currentObj.color(true);

                    try {

                        // Until speaking is done
                        Thread.sleep(m_sleepDelay);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    // Shrink the current object
                    currentObj.color(false);
                    
                    m_focusIndex++;
                }
            //}
        }        
    }
    
    public void play() {
        m_ready.notify();
    }
    // TODO : Stop
    public void cancel() {
        Speech.cancel();
    }    
}
