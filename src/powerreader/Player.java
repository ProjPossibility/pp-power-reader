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
import javax.media.j3d.Transform3D;
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
    
    static private Player m_instance = null;
    
    static {
        m_instance = new Player();
    }
    /** Creates a new instance of Player */
    public Player() {
        
        // Default to document level focus
        m_focusLevel = RawTextParser.LEVEL_DOCUMENT_ID;
        
        // Default colors
        m_highlightColorR = 1.0f;
        m_highlightColorG = 1.0f;
        m_highlightColorB = 1.0f;
        
        m_baseColorR = 1.0f;
        m_baseColorG = 1.0f;
        m_baseColorB = 1.0f;
        
        m_focusIndex = 0;
        m_objectsToSpeak = new ArrayList();
        
        m_ready = new Object();
        
        m_sleepDelay = DEFAULT_SLEEP_DELAY;
        
        // Default to an empty document
        m_root = new HierarchyObject(RawTextParser.LEVEL_DOCUMENT_ID,RawTextParser.LEVEL_DOCUMENT_STR);
    }
    
    static public void setHierarchyRoot(HierarchyObject root) {
        m_instance.m_focusIndex = 0;
        m_instance.m_root = root;
        m_instance.m_objectsToSpeak = m_instance.m_root.getAllChildrenOfLevel(m_instance.m_focusLevel);
    }
    
    static public void setSleepDelay(int delay) {
        m_instance.m_sleepDelay = delay;
        Speech.setSpeed(WPM_FACTOR/delay);
    }
    
    static public HierarchyObject getFocusOn() {
        return (HierarchyObject)(m_instance.m_objectsToSpeak.get(m_instance.m_focusIndex));
    }
    static public void setFocusLevel(int focusLevel) {
        // Get all objects on this level
        m_instance.m_focusLevel = focusLevel;
        m_instance.m_focusIndex = 0;
        m_instance.m_objectsToSpeak = m_instance.m_root.getAllChildrenOfLevel(m_instance.m_focusLevel);
    }
    
    static public boolean setFocusOn(HierarchyObject hObj) {
        int findOnLevel = hObj.getLevel();
        ArrayList objectsToSearch = m_instance.m_root.getAllChildrenOfLevel(findOnLevel);
        Iterator it = objectsToSearch.iterator();
        int searchIndex = 0;
        
        while(it.hasNext()) {
            Object objToTest = it.next();
            if(objToTest.equals(hObj)) {
                
                m_instance.m_focusLevel = findOnLevel;
                m_instance.m_focusIndex = searchIndex;
                
                // Highlight the focused
                getFocusOn().color(true);
                
                return true;
            }
            searchIndex++;
        }
        return false;
    }
    
    static public void setGrowEnable(boolean active) {
        m_instance.m_growEnabled = active;
    }
    
    static public void setHighlightColor(float r, float g, float b) {
        m_instance.m_highlightColorR = r;
        m_instance.m_highlightColorG = g;
        m_instance.m_highlightColorB = b;
    }
    
    static public void setBaseColor(float r, float g, float b) {
        m_instance.m_baseColorR = r;
        m_instance.m_baseColorG = g;
        m_instance.m_baseColorB = b;
    }
    
    public void run() {
        
        HierarchyObject currentObj = null;
        Transform3D moveScene = new Transform3D();
        TextObject3d theText = null;
        for(;;) {
//            synchronized(m_ready) {
            for(int i = m_focusIndex; i < m_objectsToSpeak.size(); i++) {
                // Get current obj
                currentObj = (HierarchyObject)m_objectsToSpeak.get(i);
                
                // Highlight/grow the current object
                currentObj.color(true);

                // Start speaking the object
                Speech.speak(currentObj.getValue());
                
                // Disable render on everything but the current object
                if (ConfigurationManager.getDetailLevel() == RawTextParser.LEVEL_WORD_ID) {
                    disableRenderExcept(currentObj);
                }
            
                if(ConfigurationManager.followFocus()) {
                    // Center the scene on the focused item
                    theText = (TextObject3d) currentObj.getTransformGroup();
                    //              System.out.println("To move, X: " + theText.getLocation().x + " Y: "+ theText.getLocation().y );
                    ConfigurationManager.current_x = -theText.getLocation().x;
                    ConfigurationManager.current_y = -theText.getLocation().y;
                    ConfigurationManager.refreshTranslate();
                    
                }
//                m_instance.m_root.getTransformGroup().setTransform(moveScene);
                
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
            m_focusIndex = 0;
            //}
        }
    }
    
    static public void playOne() {
        HierarchyObject currentObj = null;
        
        // Get current obj
        currentObj = (HierarchyObject)m_instance.m_objectsToSpeak.get(m_instance.m_focusIndex);
        
        // Start speaking the object
        Speech.speak(currentObj.getValue());
        
        // Highlight/grow the current object
        currentObj.color(true);
        
    }
    // TODO : Stop
    static public void reset() {
        Speech.cancel();
        if(m_instance.isAlive()) {
            m_instance.stop();
            m_instance = new Player();
        }
    }
    
    static public Player getInstance() {
        return m_instance;
    }
    
    static public void play() {
        if(m_instance.isAlive()) {
            m_instance.resume();
        } else {
            m_instance.start();
        }
    }
    
    static public void pause() {
        m_instance.suspend();
        Speech.cancel();
    }
    
    static public void disableRenderExcept(HierarchyObject object) {
        
        // Start with the document level
        HierarchyObject documentParent = object.getParent(RawTextParser.LEVEL_DOCUMENT_ID);
        HierarchyObject paragraphParent = object.getParent(RawTextParser.LEVEL_PARAGRAPH_ID);
        HierarchyObject sentenceParent = object.getParent(RawTextParser.LEVEL_SENTENCE_ID);
        
        // Disable all of its children starting with the highest parent
        disableRenderOfChildren(documentParent,paragraphParent);
        disableRenderOfChildren(paragraphParent,sentenceParent);
        disableRenderOfChildren(paragraphParent,object);
    }
    
    static public void disableRenderOfChildren(HierarchyObject parent, HierarchyObject except) {
        ArrayList children = parent.getAllChildrenOfLevel(except.getLevel());
        Iterator it = children.iterator();
        HierarchyObject obj;
        int i = 0;
        while(it.hasNext()) {
            obj = (HierarchyObject) it.next();
            System.out.println(i + ". Disabling Item: " + obj.getLevel() + " Val: "+ obj.getValue());
            if(!obj.equals(except)) {
                obj.disableRender();
                System.out.println("Disabled Success");
            } else {
                obj.enabledRender();
                System.out.println("Enabled Success");
            }
            i++;
        }
    }
}
