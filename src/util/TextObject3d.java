/*
 * TextObject.java
 *
 * Created on December 3, 2007, 5:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package util;

import java.awt.Font;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

/**
 *
 * @author Christopher Leung
 */

public class TextObject3d extends TransformGroup {

    private static Vector3f nextLocation = new Vector3f(-11.0f,11.0f,0);
    private static Color3f highlightColor = new Color3f(1.0f, 0, 0);
    private static Color3f baseColor = new Color3f(0, 1.0f, 1.0f);

    private Material m_textMaterial;
    
    /** Creates a new instance of TextObject */
    public TextObject3d(String text) {
        super();
        
        // face it in the scene graph
        Transform3D rotation = new Transform3D();
        TransformGroup rotation_group = new TransformGroup( rotation );
        this.addChild( rotation_group );

        // create the 3d text
        Appearance textAppear = new Appearance();
        m_textMaterial = new Material();
        m_textMaterial.setCapability(Material.ALLOW_COMPONENT_WRITE);
        m_textMaterial.setEmissiveColor(baseColor);
        textAppear.setMaterial(m_textMaterial);
        FontExtrusion fontExtrusion = new FontExtrusion();
        Font3D font3D = new Font3D(
                new Font("Helvetica", Font.PLAIN, 1),
                new FontExtrusion());
        Text3D textGeom = new Text3D(font3D,text);
        textGeom.setAlignment(Text3D.ALIGN_FIRST);
         BoundingBox bb = new BoundingBox();
        textGeom.getBoundingBox(bb);
        Point3d up= new Point3d();
        bb.getUpper(up);

        //check if the word fits on screen at default zoom level
        if (nextLocation.x + up.x > 10f) {
            warpTextToNewLine();
        }
        
        // place it in the scene graph
        Transform3D offset = new Transform3D();
        offset.setTranslation(nextLocation);
        this.setTransform( offset );

        nextLocation.x += up.x + 0.3;
        
        Shape3D textShape = new Shape3D();
        textShape.setGeometry(textGeom);
        textShape.setAppearance(textAppear);

        // attach it
        rotation_group.addChild( textShape );

    }

    public static void resetLocation() {
        nextLocation.x=-11f;
        nextLocation.y=11f;
    }
    
    public static void startNewParagraph() {
        nextLocation.x = -11.0f;
        nextLocation.y -= 2.0f;
    }
    
    private void warpTextToNewLine() {
        nextLocation.x = -11.0f;
        nextLocation.y -= 1.0f;
    }
    
    public void setHighlightColor(Color3f color) {
        highlightColor = color;
    }
    
    public void setBaseColor (Color3f color) {
        baseColor = color;
    }

    public void highLight() {
        m_textMaterial.setEmissiveColor(new Color3f(highlightColor));
    }
    public void unHighLight() {
        m_textMaterial.setEmissiveColor(new Color3f(baseColor));
    }
}
