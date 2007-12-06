/*
 * Player.java
 *
 * Created on December 4, 2007, 2:18 AM
 *
 * Play methods for the HierarchyObjects
 *
 */

package powerreader;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Raster;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import speech.Speech;
import util.HierarchyObject;
import util.RawTextParser;
import util.TextObject3d;
import com.sun.j3d.utils.image.TextureLoader;
import image.ImageFetcher;

/**
 *
 * @author Christopher Leung
 */
public class Player extends Thread {
    public static int WPM_FACTOR = 120000;
    
    private HierarchyObject m_root;
    private int m_focusLevel;
    
    private boolean m_growEnabled;
    private float m_highlightColorR;
    private float m_highlightColorG;
    private float m_highlightColorB;
    private float m_baseColorR;
    private float m_baseColorB;
    private float m_baseColorG;
    private Object m_ready;
    
    private boolean m_playOne = false;
    
    private static TransformGroup lastAttachedTo;
    private static BranchGroup lastAttached;
    
    // The objects to speak, and the item we are focused on
    private ArrayList m_objectsToSpeak;
    private int m_focusIndex;
    
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
        
        // Default to an empty document
        m_root = new HierarchyObject(RawTextParser.LEVEL_DOCUMENT_ID,RawTextParser.LEVEL_DOCUMENT_STR);
        
        Object m_ready = Speech.getSync();
        
    }
    
    static public void setHierarchyRoot(HierarchyObject root) {
        m_instance.m_focusIndex = 0;
        m_instance.m_root = root;
        m_instance.m_objectsToSpeak = m_instance.m_root.getAllChildrenOfLevel(m_instance.m_focusLevel);
    }
    
    static public void setSleepDelay(int factor) {
        Speech.setSpeed(WPM_FACTOR/factor);
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
        boolean success = false;
        if(hObj.getLevel() == RawTextParser.LEVEL_DOCUMENT_ID) {
            hObj.color(true);
            m_instance.m_objectsToSpeak = new ArrayList();
            m_instance.m_objectsToSpeak.add(hObj);
            m_instance.m_focusLevel = RawTextParser.LEVEL_DOCUMENT_ID;
            m_instance.m_focusIndex = 0;
            
            success = true;
        } else {
            int findOnLevel = hObj.getLevel();
            ArrayList objectsToSearch = m_instance.m_root.getAllChildrenOfLevel(findOnLevel);
            Iterator it = objectsToSearch.iterator();
            int searchIndex = 0;
            
            while(it.hasNext()) {
                Object objToTest = it.next();
                if(objToTest.equals(hObj)) {
                    
                    m_instance.m_focusLevel = findOnLevel;
                    m_instance.m_focusIndex = searchIndex;
                    
                    // Unhighlight everything just in case
                    m_instance.m_root.color(false);
                    
                    // Highlight the focused
                    hObj.color(true);
                    
                    // Set the objects to speach os the objects that were searched
                    m_instance.m_objectsToSpeak = objectsToSearch;
                    
                    // Set the index
                    m_instance.m_focusIndex = searchIndex;
                    
                    success = true;
                }
                searchIndex++;
            }
        }
        if (success) {
            // Start the thread and play one
            m_instance.m_playOne = true;
            play();
        }
        return success;
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
            for(int i = m_focusIndex; i < m_objectsToSpeak.size(); i++) {
                // Get current obj
                currentObj = (HierarchyObject)m_objectsToSpeak.get(i);
                
                // Highlight/grow the current object
                currentObj.color(true);
                // Get image
                if(ConfigurationManager.showImages()) {
                    displayImage(currentObj.getValue(),currentObj);
                }
                
                // Disable render on everything but the current object
                disableRenderExcept(currentObj);
                
                if(ConfigurationManager.followFocus()) {
                    // Center the scene on the focused item
                    theText = (TextObject3d) currentObj.getTransformGroup();
                    //              System.out.println("To move, X: " + theText.getLocation().x + " Y: "+ theText.getLocation().y );
                    ConfigurationManager.current_x = -theText.getLocation().x;
                    ConfigurationManager.current_y = -theText.getLocation().y;
                    ConfigurationManager.refreshTranslate();
                    
                }
                
                // Speak the current object--synchronize with the speech API
                synchronized(Speech.getSync()) {
                    try {
                        Speech.speak(currentObj.getValue());
                        Speech.getSync().wait();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                
                // Hacky, but syncrhonize on the Speech object
                synchronized(Speech.getSync()) {
                    if(m_playOne) {
                        try {
                            Speech.getSync().wait();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    m_playOne = false;
                }
                
                // Now Shrink the current object
                currentObj.color(false);
                
                m_focusIndex++;
            }
            m_focusIndex = 0;
        }
    }
    
    static public void playOne() {
        // Suspend the current instance if it is active
        m_instance.suspend();
        
        HierarchyObject currentObj = null;
        
        // Get current obj
        currentObj = (HierarchyObject)m_instance.m_objectsToSpeak.get(m_instance.m_focusIndex);
        
        // Start speaking the object
        Speech.speak(currentObj.getValue());
        
        // Highlight/grow the current object
        currentObj.color(true);
        
    }
    
    static public void restart(HierarchyObject root,int focusLevel) {
        reset();
        m_instance.setHierarchyRoot(root);
        m_instance.setFocusLevel(focusLevel);
    }
    
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
            synchronized(Speech.getSync()) {
                
                Speech.getSync().notify();
                
            }
            Speech.cancel();
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
        
        // Enable depending on level of detail
        if (ConfigurationManager.getDetailLevel() == RawTextParser.LEVEL_DOCUMENT_ID && object.getLevel() != RawTextParser.LEVEL_DOCUMENT_ID) {
            enableRenderOfChildren(object.getParent(RawTextParser.LEVEL_DOCUMENT_ID));
        } else if(ConfigurationManager.getDetailLevel() == RawTextParser.LEVEL_PARAGRAPH_ID && object.getLevel() != RawTextParser.LEVEL_PARAGRAPH_ID) {
            enableRenderOfChildren(object.getParent(RawTextParser.LEVEL_PARAGRAPH_ID));
        } else if(ConfigurationManager.getDetailLevel() == RawTextParser.LEVEL_SENTENCE_ID && object.getLevel() != RawTextParser.LEVEL_SENTENCE_ID) {
            enableRenderOfChildren(object.getParent(RawTextParser.LEVEL_SENTENCE_ID));
        }
        // Deal with special cases where focused object is equal to the level of detail
        if(ConfigurationManager.getDetailLevel() == RawTextParser.LEVEL_DOCUMENT_ID && object.getLevel() == RawTextParser.LEVEL_DOCUMENT_ID) {
            enableRenderOfChildren(object);
        }
        // Deal with special cases where focused object is equal to the level of detail
        else if(ConfigurationManager.getDetailLevel() == RawTextParser.LEVEL_PARAGRAPH_ID && object.getLevel() == RawTextParser.LEVEL_PARAGRAPH_ID) {
            disableRenderOfChildren(object.getParent(),object);
        }
        // Deal with special cases where focused object is equal to the level of detail
        else if(ConfigurationManager.getDetailLevel() == RawTextParser.LEVEL_SENTENCE_ID && object.getLevel() == RawTextParser.LEVEL_SENTENCE_ID) {
            disableRenderOfChildren(object.getParent().getParent(),object.getParent());
            disableRenderOfChildren(object.getParent(),object);
        }
        
        // Disable Show depending on level of detail
        if (ConfigurationManager.getDetailLevel() > RawTextParser.LEVEL_DOCUMENT_ID && object.getLevel() != RawTextParser.LEVEL_DOCUMENT_ID&& object.getLevel() != RawTextParser.LEVEL_PARAGRAPH_ID) {
            disableRenderOfChildren(object.getParent(RawTextParser.LEVEL_DOCUMENT_ID),object.getParent(RawTextParser.LEVEL_PARAGRAPH_ID));
        }
        if(ConfigurationManager.getDetailLevel() > RawTextParser.LEVEL_PARAGRAPH_ID && object.getLevel() != RawTextParser.LEVEL_PARAGRAPH_ID && object.getLevel() != RawTextParser.LEVEL_SENTENCE_ID) {
            disableRenderOfChildren(object.getParent(RawTextParser.LEVEL_PARAGRAPH_ID),object.getParent(RawTextParser.LEVEL_SENTENCE_ID));
        }
        if(ConfigurationManager.getDetailLevel() > RawTextParser.LEVEL_SENTENCE_ID && object.getLevel() != RawTextParser.LEVEL_SENTENCE_ID) {
            disableRenderOfChildren(object.getParent(RawTextParser.LEVEL_SENTENCE_ID),object);
        }
        
        
    }
    
    static public void enableRenderOfChildren(HierarchyObject parent) {
        ArrayList children;
        Iterator it;
        HierarchyObject obj;
        for(int i = parent.getLevel()+1; i<RawTextParser.LEVEL_WORD_ID+1; i++) {
            children = parent.getAllChildrenOfLevel(i);
            it = children.iterator();
            while(it.hasNext()) {
                obj = (HierarchyObject) it.next();
                obj.enabledRender();
            }
        }
    }
    static public void disableRenderOfChildren(HierarchyObject parent, HierarchyObject except) {
        ArrayList children = parent.getAllChildrenOfLevel(except.getLevel());
        Iterator it = children.iterator();
        HierarchyObject obj;
        int i = 0;
        while(it.hasNext()) {
            obj = (HierarchyObject) it.next();
            if(!obj.equals(except)) {
                obj.disableRender();
            } else {
                obj.enabledRender();
            }
            i++;
        }
    }
    
    static public void removeImages() {
        if(lastAttachedTo != null && lastAttached != null) {
            lastAttachedTo.removeChild(lastAttached);
        }
    }
    
    
    private void displayImage(final String pickedText, final HierarchyObject tObj) {
        
        // clear out any existing images from mouse picking
        removeImages();
        
        ImageFetcher f = ConfigurationManager.getImageFetcher();
        BufferedImage bimg = f.getImage(tObj.getValue());
        if (bimg != null) {
            //scale image
            //Image img = bimg.getScaledInstance(ConfigurationManager.getImageScale()*100,-1,Image.SCALE_DEFAULT);
            
            double scale = (ConfigurationManager.getImageScale()+1)*100/bimg.getWidth();
            AffineTransform tx = new AffineTransform();
            tx.scale(scale, scale);
            BufferedImage img = bimg; // copying default image, in case scaling doesont work
            try {
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
                img = op.filter(bimg, null);
                System.out.println("Scaled [w: " + bimg.getWidth() + " , h:"+ bimg.getHeight() +" ] to scale factor: " + ConfigurationManager.getImageScale());
            } catch(Exception e) {
                System.out.println("Error scaling [w: " + bimg.getWidth() + " , h:"+ bimg.getHeight() +" ] to scale factor: " + ConfigurationManager.getImageScale());
                System.out.println(e);
            }
            
            //render image
            TextureLoader imageT = new TextureLoader(img);
            Raster imageObj = new Raster(new Point3f(0, -0.5f,1f),
                    Raster.RASTER_COLOR, 0, 0, imageT.getImage().getWidth(), imageT.getImage().getHeight(),
                    imageT.getImage(), null);
            Shape3D shape = new Shape3D(imageObj);
            imageObj.setCapability(Raster.ALLOW_IMAGE_WRITE);
            BranchGroup node = new BranchGroup();
            
            node.setCapability(BranchGroup.ALLOW_DETACH);
            
            node.addChild(shape);
            
            if(lastAttachedTo != null && lastAttached != null) {
                lastAttachedTo.removeChild(lastAttached);
            }
            lastAttachedTo = ((TextObject3d) tObj.getTransformGroup()).getTheTextTransformGroup();
            lastAttachedTo.addChild(node);
            
            lastAttached = node;
        }
        
    }
    
}
