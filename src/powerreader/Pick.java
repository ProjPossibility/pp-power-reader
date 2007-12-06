/*
 * Pick.java
 *
 * Created on December 4, 2007, 2:58 AM
 *
 *Class to handle the mouse picking events
 */

package powerreader;

//for image
import java.awt.event.MouseAdapter;
import java.net.MalformedURLException;
import java.net.URL;
import javax.media.j3d.Raster;
import javax.vecmath.Point3f;

import com.sun.j3d.utils.picking.*;
import dictionary.DictionaryLookup;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.event.MouseInputAdapter;
import javax.vecmath.Vector3f;
import speech.Speech;
import util.HierarchyObject;
import util.RawTextParser;
import util.TextObject3d;
import util.WordHashMap;

/**
 *
 * @author Rubaiz Singh Virk
 */
public class Pick extends MouseInputAdapter implements MouseWheelListener {
    private PickCanvas pickCanvas;
    private BranchGroup m_sceneRoot;
    private TransformGroup m_mainTransformGroup;
    private int lastX;
    private int lastY;
    private Canvas3D c3D;
    private static TransformGroup lastPicked;
    private static BranchGroup lastAttached;
    
    private boolean mousePressed;
    
    /** Creates a new instance of Pick */
    public Pick(Canvas3D canvas3D, BranchGroup sceneRoot, TransformGroup mainTransformGroup) {
        
        pickCanvas = new PickCanvas(canvas3D, sceneRoot);
        pickCanvas.setMode(PickCanvas.BOUNDS);
        canvas3D.addMouseListener(this);
        canvas3D.addMouseWheelListener(this);
        canvas3D.addMouseMotionListener(this);
        m_sceneRoot = sceneRoot;
        m_mainTransformGroup = mainTransformGroup;
        
        mousePressed = false;
    }
    
    public void mouseClicked(MouseEvent e) {
        try {
            if (lastAttached != null){
                lastPicked.removeChild(lastAttached);
            }
            
            pickCanvas.setShapeLocation(e);
            PickResult result = pickCanvas.pickClosest();
            
            if (result != null) {
                Shape3D s3 = (Shape3D)result.getNode(PickResult.SHAPE3D);
                
                if (s3 != null) {
                    
                    // Get the textobject picked
                    TextObject3d tObj = (TextObject3d) s3.getParent().getParent().getParent();
                    
                    // Get its hierarchy object of the word
                    WordHashMap map = WordHashMap.getInstance();
                    HierarchyObject pickedObject = map.getHirarchyObject(tObj);
                    
                    // If our focus is sentence, set the pickedObject to the sentence
                    if(ConfigurationManager.getFocusLevel() == RawTextParser.LEVEL_SENTENCE_ID) {
                        pickedObject = pickedObject.getParent();
                    }
                    // Else if our focus is paragraph, set the pickedObject to the paragraph
                    else if(ConfigurationManager.getFocusLevel() == RawTextParser.LEVEL_PARAGRAPH_ID) {
                        pickedObject = pickedObject.getParent().getParent();
                    }
                    // You get it by now
                    else if(ConfigurationManager.getFocusLevel() == RawTextParser.LEVEL_DOCUMENT_ID) {
                        pickedObject = pickedObject.getParent().getParent().getParent();
                    }
                                        
                    String pickedText=pickedObject.getValue();
                    System.out.println(pickedText);
                    
                    // determine action for the button
                    int mouseAction = ConfigurationManager.ACTION_NOTHING;
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        mouseAction = ConfigurationManager.getLeftClickAction();
                    } else if (e.getButton() == MouseEvent.BUTTON2) {
                        mouseAction = ConfigurationManager.getMiddleClickAction();
                    } else if (e.getButton() == MouseEvent.BUTTON3) {
                        mouseAction = ConfigurationManager.getRightClickAction();
                    }
                    // select word
                    if(mouseAction == ConfigurationManager.ACTION_FOCUS ||
                            mouseAction == ConfigurationManager.ACTION_DEFINE) {
                        // Clear the focus highlight
                        Player.getFocusOn().color(false);
                        
                        // Kill the current player
                        Player.reset();
                        
                        // Reboot the player
                        HierarchyObject root = pickedObject.getParent(RawTextParser.LEVEL_DOCUMENT_ID);
                        Player.setHierarchyRoot(root);
                        Player.setFocusOn(pickedObject);
                        Player.playOne();
                    }
                    // dictionary meaning and images
                    if(mouseAction == ConfigurationManager.ACTION_DEFINE) {
                        DictionaryLookup w = ConfigurationManager.getDictionary();
                        String def = w.getDefinition(pickedText);
                        String toSpeak = "Definition of " + pickedText + ". " + def;
                        System.out.println(toSpeak);
                        Speech.speak(toSpeak);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void mouseWheelMoved(MouseWheelEvent e) {
        ConfigurationManager.current_z += e.getWheelRotation()/-2.5f;
        ConfigurationManager.refreshTranslate();
    }
    
    public void mouseDragged(MouseEvent e) {
        if(mousePressed) {
            int curX = e.getX();
            int curY = e.getY();
            float deltaX = (lastX - curX)/15.0f;
            float deltaY = (lastY - curY)/15.0f;
            lastX = curX;
            lastY = curY;
            
            ConfigurationManager.current_x -= deltaX;
            ConfigurationManager.current_y += deltaY;
            ConfigurationManager.refreshTranslate();
        }
    }
    
    public void mousePressed(MouseEvent e) {
        if((e.getButton() == MouseEvent.BUTTON1 &&
                ConfigurationManager.getLeftClickAction() == ConfigurationManager.ACTION_DRAG) ||
                (e.getButton() == MouseEvent.BUTTON2 &&
                ConfigurationManager.getMiddleClickAction() == ConfigurationManager.ACTION_DRAG) ||
                (e.getButton() == MouseEvent.BUTTON3 &&
                ConfigurationManager.getRightClickAction() == ConfigurationManager.ACTION_DRAG)) {
            mousePressed = true;
            lastX = e.getX();
            lastY = e.getY();
        }
    }
    
    public void mouseReleased(MouseEvent e) {
        if((e.getButton() == MouseEvent.BUTTON1 &&
                ConfigurationManager.getLeftClickAction() == ConfigurationManager.ACTION_DRAG) ||
                (e.getButton() == MouseEvent.BUTTON2 &&
                ConfigurationManager.getMiddleClickAction() == ConfigurationManager.ACTION_DRAG) ||
                (e.getButton() == MouseEvent.BUTTON3 &&
                ConfigurationManager.getRightClickAction() == ConfigurationManager.ACTION_DRAG)) {
            mousePressed = false;
        }
    }
    
}
