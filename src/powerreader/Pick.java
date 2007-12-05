/*
 * Pick.java
 *
 * Created on December 4, 2007, 2:58 AM
 *
 *Class to handle the mouse picking events
 */

package powerreader;

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
    private float current_z;
    private float current_x;
    private float current_y;
    private int lastX;
    private int lastY;
    
    /** Creates a new instance of Pick */
    public Pick(Canvas3D canvas3D, BranchGroup sceneRoot, TransformGroup mainTransformGroup) {
        
        pickCanvas = new PickCanvas(canvas3D, sceneRoot);
        pickCanvas.setMode(PickCanvas.BOUNDS);
        canvas3D.addMouseListener(this);
        canvas3D.addMouseWheelListener(this);
        canvas3D.addMouseMotionListener(this);
        m_sceneRoot = sceneRoot;
        m_mainTransformGroup = mainTransformGroup;
        current_x = ConfigurationManager.DEFAULT_X;
        current_y = ConfigurationManager.DEFAULT_Y;
        current_z = ConfigurationManager.DEFAULT_Z;
    }
    
    public void mouseClicked(MouseEvent e) {
        
        pickCanvas.setShapeLocation(e);
        PickResult result = pickCanvas.pickClosest();
        
        if (result != null) {
            Shape3D s3 = (Shape3D)result.getNode(PickResult.SHAPE3D);
            
            if (s3 != null) {
                TextObject3d tObj = (TextObject3d) s3.getParent().getParent().getParent();
                WordHashMap map = WordHashMap.getInstance();
                
                HierarchyObject pickedObject = map.getHirarchyObject(tObj);
                String pickedText=pickedObject.getValue();
                //System.out.println(pickedText);
                
                // If left click
                if(e.getButton() == MouseEvent.BUTTON1) {
                    // Clear the focus highlight
                    Player.getFocusOn().color(false);
                    
                    // Kill the current player
                    Player.reset();
                    
                    // Reboot the player
                    HierarchyObject root = pickedObject.getParent(RawTextParser.LEVEL_DOCUMENT_ID);
                    Player.setHierarchyRoot(root);
                    Player.setFocusOn(map.getHirarchyObject(tObj));
                    Player.playOne();
                }
                // If middle click
                else if(e.getButton() == MouseEvent.BUTTON2) {
                    DictionaryLookup w = ConfigurationManager.getDictionary();
                    String def =w.getDefinition(pickedText);
                    String toSpeak = "Definition of " + pickedText + "." + def;
                    System.out.println(toSpeak);
                    Speech.speak(toSpeak);
                    
                    //FlickrImageFetcher f = new FlickrImageFetcher();
                    //String url =f.getImageURL(text);
                    //System.out.println(url);
                }
            }
        }
    }
    public void mouseWheelMoved(MouseWheelEvent e) {
        //System.out.println(e.getWheelRotation());
        current_z += e.getWheelRotation()/-2.5f;
        
        refreshTranslate();
    }
    
    public void mouseDragged(MouseEvent e) {
        //if(e.getButton() == MouseEvent.BUTTON3) {
        // Get amount moved
        int curX = e.getX();
        int curY = e.getY();
        float deltaX = (lastX - curX)/15.0f;
        float deltaY = (lastY - curY)/15.0f;
        lastX = curX;
        lastY = curY;
//        System.out.println("Change X: " + deltaX + " Y: " + deltaY);
        current_x -= deltaX;
        current_y += deltaY;
        refreshTranslate();
        //}
    }
    
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3) {
            lastX = e.getX();
            lastY = e.getY();
  //          System.out.println("Clicked: X: " + lastX + " Y: " + lastY);
        }
    }
    public void refreshTranslate() {
        Transform3D transform = new Transform3D();
        transform.setTranslation(new Vector3f(current_x,current_y,current_z));
        m_mainTransformGroup.setTransform(transform);
    }
}