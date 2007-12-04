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
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

/**
 *
 * @author Christopher Leung
 */
public class TextObject3d extends TransformGroup {
    
    /** Creates a new instance of TextObject */
    public TextObject3d(String text) {
        super();
            // place it in the scene graph
            Transform3D offset = new Transform3D();
            this.setTransform( offset );
            
            // face it in the scene graph
            Transform3D rotation = new Transform3D();
            TransformGroup rotation_group = new TransformGroup( rotation );
            this.addChild( rotation_group );
            
            // create the 3d text
            Appearance textAppear = new Appearance();
            Material textMaterial = new Material();
            textMaterial.setEmissiveColor(1.0f,1.0f,1.0f);
            textAppear.setMaterial(textMaterial);
            FontExtrusion fontExtrusion = new FontExtrusion();
            Font3D font3D = new Font3D(
                    new Font("Helvetica", Font.PLAIN, 1),
                    new FontExtrusion());
            Text3D textGeom = new Text3D(font3D,text);
            textGeom.setAlignment(Text3D.ALIGN_FIRST);
            Shape3D textShape = new Shape3D();
            textShape.setGeometry(textGeom);
            textShape.setAppearance(textAppear);
            
            // attach it
            rotation_group.addChild( textShape );

    }

}
