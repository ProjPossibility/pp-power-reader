/*
 * Pick.java
 *
 * Created on December 4, 2007, 2:58 AM
 *
 *Class to handle the mouse picking events
 */

package powerreader;

import com.sun.j3d.utils.picking.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Shape3D;
import util.HierarchyObject;
import util.RawTextParser;
import util.TextObject3d;
import util.WordHashMap;

/**
 *
 * @author Rubaiz Singh Virk
 */
public class Pick extends MouseAdapter{
    private PickCanvas pickCanvas;
    
    
    /** Creates a new instance of Pick */
    public Pick(Canvas3D canvas3D, BranchGroup m_sceneRoot) {
        
        pickCanvas = new PickCanvas(canvas3D, m_sceneRoot);
        pickCanvas.setMode(PickCanvas.BOUNDS);
        canvas3D.addMouseListener(this);
    }
    
    public void mouseClicked(MouseEvent e) {
        
        // If left click
        if(e.getButton() == MouseEvent.BUTTON1) {
            pickCanvas.setShapeLocation(e);
            PickResult result = pickCanvas.pickClosest();
            
            if (result != null) {
                Shape3D s3 = (Shape3D)result.getNode(PickResult.SHAPE3D);
                
                if (s3 != null) {
                    TextObject3d tObj = (TextObject3d) s3.getParent().getParent();
                    WordHashMap map = WordHashMap.getInstance();
                    
                    HierarchyObject pickedObject = map.getHirarchyObject(tObj);
                    System.out.println(pickedObject.getValue());
                    
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
            }
        }
        
    }
}
