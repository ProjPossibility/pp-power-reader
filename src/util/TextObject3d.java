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
 * TextObject.java
 *
 * Created on December 3, 2007, 5:19 PM
 *
 * We assume that the default colors have already been set by another class using
 * setHighlightColor, etc.
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
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import powerreader.ConfigurationManager;

/**
 *
 * @author Christopher Leung
 */

public class TextObject3d extends TransformGroup {
    
    private static Vector3f nextLocation = new Vector3f(-5.0f,0,0);//coordinates for the welcome/start string
    private static Vector3f nextLocationStart = new Vector3f(-10f,10f,0);
    private static Vector3f nextLocationIncrementWordSentence = new Vector3f(0.3f,-1.5f,0);
    private static float nextLocationIncrementParagraph = -2.5f;
    private static Appearance m_textAppearanceHighlight;
    private static Appearance m_textAppearanceBaseColor;

    private static float m_lineWidth = 20.0f;
    
    private TransformGroup theText;
    private Material m_textMaterial;
    private Shape3D m_textShape;
    
    private Vector3f currentLocation;
    
    /** Creates a new instance of TextObject */
    public TextObject3d(String text) {
        super();
        
        // face it in the scene graph
        Transform3D rotation = new Transform3D();
        TransformGroup rotation_group = new TransformGroup( rotation );
        this.addChild( rotation_group );
        
        // Set up 3d geometry
        FontExtrusion fontExtrusion = new FontExtrusion();
        Font3D font3D = new Font3D(
                new Font("Helvetica", Font.PLAIN, 1),
                new FontExtrusion());
        Text3D textGeom = new Text3D(font3D,text);
        
        // Set alignment
        textGeom.setAlignment(Text3D.ALIGN_FIRST);
        
        // Set up bounding box
        BoundingBox bb = new BoundingBox();
        textGeom.getBoundingBox(bb);
        Point3d up= new Point3d();
        bb.getUpper(up);
        
        //check if the word fits on screen at default zoom level else warp the text to newline
        if (nextLocation.x + up.x > m_lineWidth/2.0f) {
            warpTextToNewLine();
        }
        
        // place it in the scene graph
        Transform3D offset = new Transform3D();
        this.currentLocation = new Vector3f(nextLocation.x,nextLocation.y,nextLocation.z);
        offset.setTranslation(nextLocation);
        this.setTransform( offset );
        
        nextLocation.x += up.x + nextLocationIncrementWordSentence.x;
        
        m_textShape = new Shape3D();
        m_textShape.setGeometry(textGeom);
        m_textShape.setAppearance(m_textAppearanceBaseColor);
        m_textShape.setCapability(Shape3D.ALLOW_APPEARANCE_OVERRIDE_WRITE);
        m_textShape.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
        
        // attach it
        theText = new TransformGroup();
        theText.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        theText.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        theText.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        theText.addChild(m_textShape);
        rotation_group.addChild( theText );
        
        // squeeze the text
        Transform3D squeeze = new Transform3D();
        squeeze.setScale(new Vector3d(1,1,ConfigurationManager.TEXT_DEPTH));
        theText.setTransform(squeeze);
        
    }
    
    public static Appearance createAppearance(Color3f color) {
        // create the 3d text
        Appearance textAppear = new Appearance();
        Material textMaterial = new Material();
        textMaterial.setCapability(Material.ALLOW_COMPONENT_WRITE);
        textMaterial.setCapability(Material.ALLOW_COMPONENT_READ);
        textMaterial.setCapability(Material.EMISSIVE);
        textMaterial.setEmissiveColor(color);
        textAppear.setMaterial(textMaterial);
        return textAppear;
    }
    
    public static void setLineWidth(float width) {
        m_lineWidth = width;
        nextLocationStart.x = -1.0f * width/2.0f;
        nextLocationStart.y = width/2.0f;
    }
    public static float getLineWidth() {
        return m_lineWidth;
    }
    
    // reset co-ordinates of the text to be rendered to the top-left corner
    public static void resetLocation() {
        nextLocation.x= nextLocationStart.x;
        nextLocation.y= nextLocationStart.y;
    }
    
    // Warp text and spovide spacing for next paragraph
    public static void startNewParagraph() {
        nextLocation.x = nextLocationStart.x;
        nextLocation.y += nextLocationIncrementParagraph;
    }
    
    //warp to new line below the current line
    private void warpTextToNewLine() {
        nextLocation.x = nextLocationStart.x;
        nextLocation.y += nextLocationIncrementWordSentence.y;
    }
    
    public static void setHighlightColor(Color3f color) {
        m_textAppearanceHighlight = createAppearance(color);
    }
    
    public static void setBaseColor(Color3f color) {
        m_textAppearanceBaseColor = createAppearance(color);
    }
    
    public void highLight() {
        m_textShape.setAppearance(m_textAppearanceHighlight);
        if(ConfigurationManager.wordsGrow()) {
            Transform3D scale = new Transform3D();
            scale.setScale(new Vector3d(1.0f,1.6f,ConfigurationManager.TEXT_DEPTH));
            // Alternative method -- have words come out at you
            // scale.setTranslation(new Vector3f(0,0,10f));
            theText.setTransform(scale);
        }
    }
    public void unHighLight() {
        m_textShape.setAppearance(m_textAppearanceBaseColor);
        
        Transform3D scale = new Transform3D();
        scale.setScale(new Vector3d(1.0f,1.0f,ConfigurationManager.TEXT_DEPTH));
        theText.setTransform(scale);
    }
    
    public TransformGroup getTheTextTransformGroup() {
        return theText;
    }
    
    public Vector3f getLocation() {
        return currentLocation;
    }
}
