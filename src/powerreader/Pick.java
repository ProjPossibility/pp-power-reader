/*
     This file is part of Power Reader.

    Power Reader is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Power Reader is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Power Reader.  If not, see <http://www.gnu.org/licenses/>. 
    
    This software was developed by members of Project:Possibility, a software 
    collaboration for the disabled.
    
    For more information, visit http://www.projectpossibility.org 
 */

/*
 * Pick.java
 *
 * Created on December 4, 2007, 2:58 AM
 *
 *Class to handle the mouse picking events
 */

package powerreader;

//for image
import com.sun.j3d.utils.image.TextureLoader;
import image.ImageFetcher;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.MouseAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import javax.media.j3d.Raster;
import javax.media.j3d.SceneGraphPath;
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
                
                // Retrieve the picked text object
                TextObject3d tObj = null;
                SceneGraphPath pickedPath = result.getSceneGraphPath();
                
                for(int i = 0; i< pickedPath.nodeCount(); i++) {
                    if (pickedPath.getNode(i) instanceof TextObject3d) {
                        tObj = (TextObject3d) pickedPath.getNode(i);
                    }
                }
                
                if (tObj != null) {
                                        
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
                        
                        // Reboot the player
                        HierarchyObject root;
                        if(pickedObject.getLevel() == RawTextParser.LEVEL_DOCUMENT_ID) {
                            root = pickedObject;
                        } else {
                            root = pickedObject.getParent(RawTextParser.LEVEL_DOCUMENT_ID);
                        }
                        Player.restart(root,pickedObject.getLevel());
                        Player.setFocusOn(pickedObject);
                    }
                    // dictionary meaning
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
